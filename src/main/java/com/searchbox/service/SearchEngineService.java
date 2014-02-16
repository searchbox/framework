package com.searchbox.service;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrResponse;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.common.util.ContentStreamBase;
import org.apache.solr.core.CoreContainer;
import org.apache.solr.core.CoreDescriptor;
import org.apache.solr.core.SolrConfig;
import org.apache.solr.core.SolrCore;
import org.apache.solr.schema.IndexSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@Service
public class SearchEngineService {

	private static Logger logger = LoggerFactory.getLogger(SearchEngineService.class);

	@Autowired
	ApplicationContext context;
	
	SolrServer server;
	
	private static boolean loaded = false;
	
	public SearchEngineService() {}
	
	@PostConstruct
	public synchronized void init() throws ParserConfigurationException, IOException, SAXException{ 
		
		Resource solrHomeResource = context.getResource("classpath:META-INF/solr/");
		System.setProperty("solr.solr.home", solrHomeResource.getURL().getPath());
		logger.info("Embedded solr.solr.home is: " + solrHomeResource.getURL().toString());
		//SolrResourceLoader loader = new SolrResourceLoader(solrHomeResource.getURL().getPath(), context.getClassLoader());
		//CoreContainer coreContainer = new CoreContainer(loader);
		CoreContainer coreContainer = new CoreContainer();
		coreContainer.load();

		String coreInstanceDir = context.getResource("classpath:META-INF/solr/").getURL().getPath();
		Resource solrConfigResource = context.getResource("classpath:META-INF/solr/conf/solrconfig.xml");
		SolrConfig config = new SolrConfig(solrConfigResource.getFilename(), new InputSource(solrConfigResource.getInputStream()));

		Resource schemaResource = context.getResource("classpath:META-INF/solr/conf/schema.xml");
		IndexSchema schema = new IndexSchema(config, schemaResource.getFilename(), new InputSource(schemaResource.getInputStream()));

		File dataDir = new File("target/data/pubmed/");
		if(dataDir.exists()){			
			FileUtils.deleteDirectory(dataDir);
		}
		
		String dataDirName = dataDir.getPath();
		
		CoreDescriptor cd = new CoreDescriptor(coreContainer, "pubmed", coreInstanceDir);
		SolrCore core = new SolrCore("pubmed",dataDirName,config, schema, cd);
		logger.info("Solr Core config: " + core.getConfigResource());
		logger.info("Solr Instance dir: " + core.getIndexDir());
		logger.info("Solr Data dir: " + core.getDataDir());
		coreContainer.register(core, false);
		
		this.server = new EmbeddedSolrServer(coreContainer, "pubmed");
		
		
		//Now we might be able to load some PUBMED data.
		//TODO remove that has nothing to do here!!!
		
		

		try {
			Resource rfile = context.getResource("classpath:META-INF/data/pubmedIndex.xml");
			logger.error("FILE: " + rfile.getFile().getAbsolutePath());
			ContentStreamBase contentstream = new ContentStreamBase.FileStream(rfile.getFile());
			contentstream.setContentType("text/xml");
			ContentStreamUpdateRequest request = new ContentStreamUpdateRequest("/update");
			request.addContentStream(contentstream);
			request.process(this.server);
			this.server.commit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public SolrServer getServer(String collection){
		return this.server;
	}
	
	public SolrResponse getResponse(SolrQuery query) throws Exception{
		return this.server.query(query);
	}

//	public List<Collection> getCollections();
//
//	public List<Field> getFields(Collection collection);
//
//	public boolean setField(Collection collection, Field field);
//
//	public boolean addCollection(Collection collection);
//
//	public SearchResult search();
//
//	public List<String> spell();
}
