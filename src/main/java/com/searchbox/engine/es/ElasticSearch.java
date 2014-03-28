package com.searchbox.engine.es;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.node.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.searchbox.core.dm.Collection;
import com.searchbox.core.dm.Field;
import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.core.dm.FieldAttribute.USE;
import com.searchbox.core.engine.AbstractSearchEngine;
import com.searchbox.core.engine.ManagedSearchEngine;
import com.searchbox.engine.FieldMappingSearchEngine;

public class ElasticSearch extends
    AbstractSearchEngine<SearchRequestBuilder, MultiSearchResponse> implements
    ManagedSearchEngine, FieldMappingSearchEngine, InitializingBean{
  
  private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearch.class);
  
  Node node;
  
  String clusterName;
  
  protected ElasticSearch() {
    super(SearchRequestBuilder.class, MultiSearchResponse.class);
  }
  

  public ElasticSearch(String name) {
    super(name, SearchRequestBuilder.class, MultiSearchResponse.class);
  }

  public String getClusterName() {
    return clusterName;
  }


  public void setClusterName(String clusterName) {
    this.clusterName = clusterName;
  }


  @Override
  public MultiSearchResponse execute(Collection collection, SearchRequestBuilder query) {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public String getKeyForField(FieldAttribute fieldAttribute) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getKeyForField(FieldAttribute fieldAttribute, USE operation) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Set<String> getAllKeysForField(FieldAttribute fieldAttribute) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean indexFile(Collection collection, File file) {
    // TODO Auto-generated method stub
    return false;
  }
  
  @Override
  public boolean indexMap(Collection collection, Map<String, Object> fields) {
    IndexResponse response = client.prepareIndex(collection.getName().toLowerCase(),
        "issue",fields.get("id").toString())
      .setSource(fields)
      .execute()
      .actionGet();
      
    return response.isCreated();
  }
  
  @Override
  public boolean indexMap(Collection collection,
      java.util.Collection<Map<String, Object>> indexables) {
    
    BulkRequestBuilder bulk = client.prepareBulk();
    
    for(Map<String, Object> indexable:indexables){
      bulk.add(client.prepareIndex(collection.getName().toLowerCase(),
          "issue",indexable.get("id").toString())
          .setSource(indexable));
    }
    
    return !bulk.execute().actionGet().hasFailures();
  }

  @Override
  public boolean updateDataModel(Collection collection,
      List<FieldAttribute> fieldAttributes) {
    // TODO Auto-generated method stub
    return false;
  }
  
  private String getIndex(Collection collection){
    return collection.getName().toLowerCase();
  }

  @Override
  public void reloadEngine(Collection collection) {
    String index = getIndex(collection);
    
    //Close the index, we're updating.
    client.admin().indices().prepareClose(index).execute().actionGet();
    
    //Open the index we're done.
    client.admin().indices().prepareOpen(index).execute().actionGet();
  }

  @Override
  public void register(Collection collection) {
    String index = getIndex(collection);

    IndicesExistsResponse indexExistsResponse = client.admin().indices()
        .prepareExists(index)
        .execute()
        .actionGet();
    
    if(!indexExistsResponse.isExists()){
      LOGGER.info("Index {} does not exists. Creating it",index);
      CreateIndexResponse response = client.admin().indices()
          .prepareCreate(collection.getName().toLowerCase())
          .execute()
          .actionGet();
      LOGGER.debug("result: {}", response);
    } 
    
//    //Close the index, we're updating.
//    client.admin().indices().prepareClose(index).execute().actionGet();
//    
//    for(Field field:collection.getFields()){
//      
//    }
//    
//    //Open the index we're done.
//    client.admin().indices().prepareOpen(index).execute().actionGet();
//
//    client.admin().indices().preparePutMapping(collection.getName().toLowerCase())
//      .setSource(jsonBuilder()
//        .startObject()
//            .startObject("analysis")
//                .startObject("analyzer")
//                    .startObject("path")
//                        .field("type", "string")
//                        .field("tokenizer", "path_hierarchy")
//                    .endObject()
//                .endObject()
//            .endObject()
//        .endObject())
//        
//    .setSettings(ImmutableSettings.settingsBuilder().loadFromSource(jsonBuilder()
//        .startObject()
//            .startObject("analysis")
//                .startObject("analyzer")
//                    .startObject("path")
//                        .field("type", "string")
//                        .field("tokenizer", "path_hierarchy")
//                    .endObject()
//                .endObject()
//            .endObject()
//        .endObject().string()))
//        
//    client.admin().indices()
//      .prepareGetFieldMappings(collection.getName().toLowerCase())
//      .prepareUpdateSettings(collection.getName())
//      .
  }

  @Override
  public void reloadPlugins(Collection collection) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public SearchRequestBuilder newQuery(Collection collection) {
    // TODO Auto-generated method stub
    return null;
  }
  
  private static Client client;


  @Override
  public void afterPropertiesSet() throws Exception {
    this.node = nodeBuilder()
        .clusterName(this.getClusterName())
        .data(false)
        .node();
    client = node.client();   
  }

  public Client getClient() {
    return client;
   }
  
  public static void main(String... args) throws Exception{
    
    ElasticSearch searchEngine = new ElasticSearch("ElasticSearch Engine");
    searchEngine.setClusterName("elasticsearch");
    searchEngine.afterPropertiesSet();
    
    
    
    while(true){
      Thread.sleep(1000);
    }
    
  }
}
