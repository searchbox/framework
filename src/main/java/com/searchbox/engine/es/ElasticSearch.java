package com.searchbox.engine.es;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;

import com.searchbox.core.dm.Collection;
import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.core.dm.FieldAttribute.USE;
import com.searchbox.core.engine.AbstractSearchEngine;
import com.searchbox.core.engine.ManagedSearchEngine;
import com.searchbox.engine.FieldMappingSearchEngine;

public class ElasticSearch extends
    AbstractSearchEngine<SearchRequestBuilder, MultiSearchResponse> implements
    ManagedSearchEngine, FieldMappingSearchEngine {

  protected ElasticSearch() {
    super(SearchRequestBuilder.class, MultiSearchResponse.class);
  }

  protected ElasticSearch(String name) {
    super(name, SearchRequestBuilder.class, MultiSearchResponse.class);
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

}
