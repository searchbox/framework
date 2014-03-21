package com.searchbox.engine.solr;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.request.CollectionAdminRequest;
import org.apache.solr.client.solrj.response.CollectionAdminResponse;
import org.apache.solr.common.cloud.Slice;
import org.apache.solr.common.cloud.SolrZkClient;
import org.apache.solr.common.cloud.ZkStateReader;
import org.apache.zookeeper.KeeperException;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;

import com.searchbox.core.SearchAttribute;
import com.searchbox.core.dm.Collection;
import com.searchbox.core.dm.Field;
import com.searchbox.core.dm.MultiCollection;
import com.searchbox.core.engine.AccessibleSearchEngine;

public class SolrCloud extends SolrSearchEngine implements InitializingBean,
    AccessibleSearchEngine, DisposableBean {

  private static final Logger LOGGER = LoggerFactory.getLogger(SolrCloud.class);
  
  private static final String DEFAULT_SOLR_URL = "http://localhost:8983/solr";

  @SearchAttribute
  // FIXME this is null when not set like this when using afterProperties
  private String zkHost = "localhost:9983";

  private static final String ZK_CORE_CONFIG_PATH = "/configs";
  private static final String ZK_COLLECTION_PATH = "/collections";

  @Override
  public void afterPropertiesSet() throws Exception {
    initServer();
  }

  @Override
  public void destroy() throws Exception {
    getSolrServer(null).shutdown();
  }

  private static CloudSolrServer solrServer;

  private void initServer() {
    if (solrServer == null && zkHost != null) {
      LOGGER.info("Initializing SolrCloud server with zkHost: {}", zkHost);
      try {
        CloudSolrServer server = new CloudSolrServer(zkHost, true);
        server.setParallelUpdates(true);
        server.connect();

        solrServer = server;
      } catch (IOException e) {
        LOGGER.error("Could not connect to server!!!", e);
        throw new RuntimeException("Could not connect to server "
            + "with zkHost: " + zkHost);
      }
    }
  }

  @Override
  protected SolrServer getSolrServer(Collection collection) {
    LOGGER.trace("Getting Solr Server for collection {}", collection);
    if (solrServer == null) {
      LOGGER.debug("Solr Server does not exists, creating it for collection {}", collection);
      initServer();
    }
    LOGGER.debug("Solr Server is {}", solrServer);
    return solrServer;
  }

  @Override
  public void register(Collection collection) {
    if(MultiCollection.class.isAssignableFrom(collection.getClass())){
      registerAlias((MultiCollection) collection);
    } else {
      registerCollection(collection);
    }
  }
  
  private void registerAlias(MultiCollection collection){
    LOGGER.info("Registering multiCollection as alias {}",collection.getName());
    String adminUrl = getAdminURL();
    LOGGER.info("Using Solr Base URL: {}", adminUrl);
    Set<String> collectionList = new TreeSet<String>();
    for(Collection coll:collection.getCollections()){
      collectionList.add(coll.getName());
    }
    String createAlias = adminUrl+"/collections"
        +"?action=CREATEALIAS&name="+collection.getName()
        +"&collections="+StringUtils.join(collectionList, ",");
    
    LOGGER.info("CreateAlias REST url: {}",createAlias);
    this.httpGET(createAlias);
    
  }
  
  private CloudSolrServer getCloudServer(){
    return (CloudSolrServer) this.getSolrServer(null);
  }
  
  private File getConfiguration(){
    String dir = this.getClass().getResource("/solr/conf").getFile();
    LOGGER.info("Path: {}", dir);
    return new File(dir);
  }
    
  private void registerCollection(Collection collection){

    try {
      SolrZkClient zkServer = getCloudServer().getZkStateReader().getZkClient();

      LOGGER.info("Creating configuration for: " + collection.getName());
      uploadToZK(zkServer, getConfiguration(),
          ZK_CORE_CONFIG_PATH + "/" + collection.getName());

      if (coreExists(zkServer, collection.getName())) {
        CollectionAdminResponse response = CollectionAdminRequest
            .reloadCollection(collection.getName(), getSolrServer(collection));
        LOGGER.info("Reloaded Existing collection: " + response);
      } else {
        CollectionAdminResponse response = CollectionAdminRequest
            .createCollection(collection.getName(), 1, collection.getName(),
                getSolrServer(collection));
        LOGGER.info("Created New collection: " + response);
      }

      int wait = 0;
      while (!coreExists(zkServer, collection.getName())) {
        if ((wait++) > 5) {
          LOGGER.error("Giving up waiting for engine...");
          break;
        }
        LOGGER.info("Waiting for core to come online...");
        Thread.sleep(500);
      }

    } catch (Exception e) {
      LOGGER.error("Could not register collection: " + collection.getName());
      e.printStackTrace();
    }

  }

  private ZkStateReader getZkStateReader() {
    return ((CloudSolrServer) getSolrServer(null)).getZkStateReader();
  }

  @Override
  public void reloadEngine(Collection collection) {
    try {

      SolrZkClient zkServer = getZkStateReader().getZkClient();

      int wait = 0;
      while (!coreExists(zkServer, collection.getName())) {
        if ((wait++) > 5) {
          LOGGER.error("Giving up waiting for engine...");
        }
        Thread.sleep(500);
        LOGGER.info("Waiting for core to come online...");
      }

      CollectionAdminResponse response = CollectionAdminRequest
          .reloadCollection(collection.getName(), getSolrServer(collection));
      LOGGER.info("Reloaded collection: " + response);

      wait = 0;
      while (!coreExists(zkServer, collection.getName())) {
        if ((wait++) > 5) {
          LOGGER.error("Giving up waiting for engine...");
        }
        Thread.sleep(500);
        LOGGER.info("Waiting for core to come online...");
      }
    } catch (Exception e) {
      LOGGER.error("Could not reload engine for: " + collection.getName(), e);
    }
  }

  protected boolean updateDataModel(Collection collection, Map<Field, Set<String>> copyFields) {

    try {

      String baseUrl = this.getUrlBase(collection);

      String schemaFieldURL = baseUrl + "/schema/fields";
      LOGGER.debug("Schema field url: {}", schemaFieldURL);

      String copyFieldUrl = baseUrl + "/schema/copyfields";
      LOGGER.debug("Schema copyField url: {}", copyFieldUrl);

      // Load current CopyFields not to double add them
      JSONObject solrCurrentCopyFields = httpGET(copyFieldUrl);
      LOGGER
          .debug("Current Copy fields in Solr are: {}", solrCurrentCopyFields);

      JSONArray solrFieldUpdates = new JSONArray();
      JSONArray solrCopyFields = new JSONArray();
      for (Entry<Field, Set<String>> copyField : copyFields.entrySet()) {
        String collectionField = copyField.getKey().getKey();
        JSONObject fieldInfo = this.httpGET(schemaFieldURL + "/"
            + collectionField);
        JSONObject currentFieldCopys = new JSONObject();

        if (fieldInfo.getJSONObject("field").has("dynamicBase")) {
          LOGGER.debug("Materializing dynamic field: {}",
              fieldInfo.getJSONObject("field"));
          solrFieldUpdates.put(fieldInfo.getJSONObject("field"));
        }
        currentFieldCopys.put("source", collectionField);
        currentFieldCopys.put("dest", new JSONArray());
        solrCopyFields.put(currentFieldCopys);

        for (String solrField : copyField.getValue()) {
          JSONObject solrFieldInfo = this.httpGET(schemaFieldURL + "/"
              + solrField);

          // Check if the field exists or not
          if (solrFieldInfo.getJSONObject("field").has("dynamicBase")) {
            LOGGER.debug("Materializing dynamic field: {}",
                solrFieldInfo.getJSONObject("field"));
            solrFieldUpdates.put(solrFieldInfo.getJSONObject("field"));
          }

          boolean hasCopyPair = false;
          JSONArray copyFieldPairs = solrCurrentCopyFields
              .getJSONArray("copyFields");
          for (int i = 0; i < copyFieldPairs.length(); i++) {
            JSONObject copyFieldPair = copyFieldPairs.getJSONObject(i);
            if (copyFieldPair.getString("source").replace("\"", "")
                .equals(collectionField)
                && copyFieldPair.getString("dest").replace("\"", "")
                    .equals(solrField)) {
              hasCopyPair = true;
            }
          }
          // Check if its the copy pair exists
          if (!hasCopyPair) {
            currentFieldCopys.getJSONArray("dest").put(solrField);
          }
        }
      }

      /**
       * No need, we have dynamic Schema
       * 
       * JSONObject fieldResponse = this.httpPOST(schemaFieldURL,
       * solrFieldUpdates); LOGGER.info("Update Field Response: {}",
       * fieldResponse);
       */

      // Now we update the copyFields...
      JSONObject copyResponse = this.httpPOST(copyFieldUrl, solrCopyFields);
      LOGGER.info("Update CopyField Response: {}", copyResponse);

    } catch (Exception e) {
      LOGGER.error("Could not update DataModel", e);
    } finally {

    }
    return false;
  }

  private static final boolean coreExists(SolrZkClient server, String coreName) {
    try {
      return server.exists(ZK_COLLECTION_PATH + "/" + coreName, true);
    } catch (Exception e) {
      LOGGER.error("Could not check if core:" + coreName + " exists", e);
    }

    return false;
  }

  private static final boolean configExists(SolrZkClient server, String coreName) {
    try {
      return server.exists(ZK_CORE_CONFIG_PATH + "/" + coreName, true);
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
          zkClient.makePath(zkPath + "/" + file.getName(), file, false, true);
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
   * @param zkHost
   *          the zkHost to set
   */
  public void setZkHost(String zkHost) {
    this.zkHost = zkHost;
  }

  private JSONObject httpGET(String url) {
    return this.doHttpRequest(RequestBuilder.get(), url, null);
  }

  private JSONObject httpPUT(String url, Object jsonObject) {
    return this.doHttpRequest(RequestBuilder.put(), url, jsonObject);
  }

  private JSONObject httpPOST(String url, Object jsonObject) {
    return this.doHttpRequest(RequestBuilder.post(), url, jsonObject);
  }

  /**
   * Adds new fields in the Collection's Schema with the Schema Rest API
   * (http://localhost:8983/solr/schema/fields)
   * 
   * [{"name":"newfield1","type":"text","copyFields":["target1",...]},
   * {"name":"newfield2","type":"text","stored":"false"}]
   * 
   * @param jsonObject
   */
  private JSONObject doHttpRequest(RequestBuilder builder, String url,
      Object jsonObject) {
    HttpClient client = HttpClientBuilder.create().build();
    RequestBuilder thisBuilder = builder.setUri(url)
        .addParameter("includeDynamic", "true").addParameter("wt", "json");
    if (jsonObject != null) {
      thisBuilder = thisBuilder.setEntity(EntityBuilder.create()
          .setContentType(ContentType.APPLICATION_JSON)
          .setText(jsonObject.toString()).build());
    }

    HttpUriRequest request = thisBuilder.build();

    JSONObject response = new JSONObject();

    try {
      HttpResponse httpResponse = client.execute(request);
      InputStream ips = httpResponse.getEntity().getContent();
      BufferedReader buf = new BufferedReader(new InputStreamReader(ips,
          "UTF-8"));
      if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
        LOGGER.error("could read response ({}) for url: {}", httpResponse
            .getStatusLine().getReasonPhrase(), url);
      }
      StringBuilder sb = new StringBuilder();
      String s;
      while (true) {
        s = buf.readLine();
        if (s == null || s.length() == 0)
          break;
        sb.append(s);

      }
      buf.close();
      ips.close();
      return new JSONObject(sb.toString());
    } catch (Exception e) {
      LOGGER.error("Could not post JSON to {}", url, e);
    }
    return response;
  }
  
  private String getAdminURL() {
    //TODO Actually resolve to an URL instead of default
    return DEFAULT_SOLR_URL+"/admin";
  }

  @Override
  public String getUrlBase(Collection collection) {
    String urlBase = null;
    
    LOGGER.info("Getting URL base for {}",collection);
    
    try {
      ZkStateReader zkSateReader = ((CloudSolrServer) getSolrServer(null))
          .getZkStateReader();
      java.util.Collection<Slice> slices = zkSateReader.getClusterState()
          .getActiveSlices(collection.getName());

      String baseUrl = "";
      for (Slice slice : slices) {
        String nodeName = slice.getLeader().getNodeName();
        urlBase = zkSateReader.getBaseUrlForNodeName(nodeName);
        LOGGER.debug("Slice state: {}", slice.getState());
        LOGGER.debug("Leader's node name: {}", nodeName);
        LOGGER.debug("Base URL for node: {}", baseUrl);
      }
      urlBase += "/" + collection.getName();
    } catch (Exception e) {
      LOGGER.error("Could not read from ZK to get urlBase", e);
    }
    return urlBase;
  }
}
