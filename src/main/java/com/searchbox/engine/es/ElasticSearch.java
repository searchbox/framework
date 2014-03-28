package com.searchbox.engine.es;


import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.searchbox.core.dm.Collection;
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
  

  protected ElasticSearch(String name) {
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
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean updateDataModel(Collection collection,
      List<FieldAttribute> fieldAttributes) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void reloadEngine(Collection collection) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void register(Collection collection) {
    // TODO Auto-generated method stub
    
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
    
    CreateIndexResponse response = new CreateIndexRequestBuilder(client.admin().indices())
          .setIndex("test_index")
          .execute()
          .actionGet();
    LOGGER.info("result: {}", response);
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
