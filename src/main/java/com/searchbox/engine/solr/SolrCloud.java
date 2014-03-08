package com.searchbox.engine.solr;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.request.CollectionAdminRequest;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.client.solrj.response.CollectionAdminResponse;
import org.apache.solr.common.cloud.Slice;
import org.apache.solr.common.cloud.SolrZkClient;
import org.apache.solr.common.cloud.ZkStateReader;
import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.searchbox.core.SearchAttribute;
import com.searchbox.core.dm.Collection;
import com.searchbox.core.dm.Field;

public class SolrCloud extends SolrSearchEngine implements InitializingBean, DisposableBean {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SolrCloud.class);

	@Autowired
	ApplicationContext context;

	@SearchAttribute
	String zkHost = "localhost:9983";

	static CloudSolrServer solrServer;

	private static final String CORE_CONFIG_PATH = "/configs";
	private static final String COLLECTION_PATH = "/collections";

	@Override
	public void afterPropertiesSet() throws Exception {
		if (solrServer == null && zkHost != null) {
			solrServer = new CloudSolrServer(zkHost, true);
			solrServer.setParallelUpdates(true);
			solrServer.connect();
		}
	}
	
	@Override
	public void destroy() throws Exception {
		solrServer.shutdown();
	}

	@Override
	protected SolrServer getSolrServer() {
		return solrServer;
	}
	
	@Override
	public void register() {
		try {
			SolrZkClient zkServer = solrServer.getZkStateReader().getZkClient();

			LOGGER.info("Creating configuration for: " + collection.getName());
			LOGGER.info("Path: "
					+ context.getResource("classpath:solr/conf").getFile());
			uploadToZK(zkServer, context.getResource("classpath:solr/conf")
					.getFile(), CORE_CONFIG_PATH + "/" + collection.getName());
	
			this.reloadEngine();
			
		} catch (Exception e) {
			LOGGER.error("Could not register colleciton: "
					+ collection.getName());
		}
		
	}
	
	@Override
	public void reloadEngine() {
		try {
			SolrZkClient zkServer = solrServer.getZkStateReader().getZkClient();

			if (coreExists(zkServer, collection.getName())) {
				CollectionAdminResponse response = CollectionAdminRequest
						.reloadCollection(collection.getName(), solrServer);
				LOGGER.info("Reloaded collection: " + response);
			} else {
				CollectionAdminResponse response = CollectionAdminRequest
						.createCollection(collection.getName(), 1,
								collection.getName(), solrServer);
				LOGGER.info("Created collection: " + response);
			}
			
			while(!coreExists(zkServer,collection.getName())){
				LOGGER.info("Waiting for core to come online...");
				Thread.sleep(500);
			}
		} catch (Exception e) {
			LOGGER.error("Could not reload engine for: " + collection.getName(),e);
		}
	}

	@Override
	protected boolean addCopyFields(Field field, Set<String> copyFields) {
		
		HttpClient client = HttpClientBuilder.create().build();
		ZkStateReader  zkSateReader = solrServer.getZkStateReader();
		
		try {
			
			java.util.Collection<Slice> slices = zkSateReader
					.getClusterState().getActiveSlices(collection.getName());
			
			String baseUrl = "";
			for(Slice slice:slices){
				String nodeName = slice.getLeader().getNodeName();
				baseUrl = solrServer.getZkStateReader().getBaseUrlForNodeName(nodeName);
				LOGGER.info("Slice state: " + slice.getState());
				LOGGER.info("Leader's node name: " + nodeName);
				LOGGER.info("Base URL for node: " + baseUrl);
			}
			
			String url = baseUrl+"/"+collection.getName()+"/schema/copyfields";
			LOGGER.info("Schema copyField url: " + url);
			
			HttpPost post = new HttpPost(url);
			
			/** 
			 * [{"source":"sourceField","dest":["target1",...]}, ...] 
			 */
			Set<String> realCopyFields = new HashSet<String>();
			for(String copyField:copyFields){
				realCopyFields.add("\""+copyField+"\"");
			}
			String content = "[{\"source\":\""+
					field.getKey()+"\",\"dest\":["+
					StringUtils.join(realCopyFields, ',')+"]}]";
			LOGGER.info("Payload is: " + content);
			
			HttpEntity entity = EntityBuilder.create()
					.setContentType(ContentType.APPLICATION_JSON)
					.setText(content).build();
			post.setEntity(entity);
			
			HttpResponse response = client.execute(post);
			entity = response.getEntity();
			post.completed();
			
			//FIXME this is ugly... Should NOT have to reload
			// core for a simple copyField...
			CollectionAdminResponse synch = CollectionAdminRequest
					.reloadCollection(collection.getName(), solrServer);
//			this.reloadEngine();
			
									
			LOGGER.info("response is: " + response.getStatusLine());				
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		return false;
	}

	private static final boolean coreExists(SolrZkClient server, String coreName) {
		try {
			return server.exists(COLLECTION_PATH + "/" + coreName, true);
		} catch (Exception e) {
			LOGGER.error("Could not check if core:" + coreName + " exists", e);
		}

		return false;
	}

	private static final boolean configExists(SolrZkClient server,
			String coreName) {
		try {
			return server.exists(CORE_CONFIG_PATH + "/" + coreName, true);
		} catch (Exception e) {
			LOGGER.error("Could not check if congif:" + coreName + " exists", e);
		}
		return false;
	}

	public static void uploadToZK(SolrZkClient zkClient, File dir, String zkPath)
			throws IOException, KeeperException, InterruptedException {
		File[] files = dir.listFiles();
		if (files == null) {
			throw new IllegalArgumentException("Illegal directory: " + dir);
		}
		for (File file : files) {
			if (!file.getName().startsWith(".")) {
				if (!file.isDirectory()) {
					zkClient.makePath(zkPath + "/" + file.getName(), file,
							false, true);
				} else {
					uploadToZK(zkClient, file, zkPath + "/" + file.getName());
				}
			}
		}
	}

	/**
	 * @return the zkHost
	 */
	public String getZkHost() {
		return zkHost;
	}

	/**
	 * @param zkHost the zkHost to set
	 */
	public void setZkHost(String zkHost) {
		this.zkHost = zkHost;
	}

	public static void main(String... args) throws KeeperException,
			InterruptedException, SolrServerException, IOException {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				SolrCloud.class);
		
		SolrCloud server = context.getBean(SolrCloud.class);
		server.setCollection(new Collection("oppfin"));
		
		Set<String> copyFields = new HashSet<String>();
		copyFields.add("title_en");
		copyFields.add("title_ss");
		server.addCopyFields(Field.stringField("title"), copyFields);
		
		CollectionAdminResponse response = CollectionAdminRequest
				.reloadCollection("oppfin", solrServer);
		LOGGER.info("Reloaded collection: " + response);
		
		//solrServer.getZkStateReader().getLeaderUrl("oppfin", shard, timeout).getZkClient();
		context.close();
	}
}
