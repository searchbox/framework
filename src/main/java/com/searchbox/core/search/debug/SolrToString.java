/*******************************************************************************
 * Copyright Searchbox - http://www.searchbox.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.searchbox.core.search.debug;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.core.SearchAdapter;
import com.searchbox.core.SearchAdapter.Time;
import com.searchbox.core.SearchAdapterMethod;
import com.searchbox.core.SearchComponent;
import com.searchbox.core.SearchElement;
import com.searchbox.core.SearchElementBean;
import com.searchbox.core.dm.Collection;
import com.searchbox.core.engine.AccessibleSearchEngine;
import com.searchbox.engine.solr.SolrSearchEngine;

@SearchComponent
public class SolrToString extends SearchElementBean {
  
  private static final Logger LOGGER = LoggerFactory
      .getLogger(SolrToString.class);

  private QueryResponse response;
  private SolrQuery request;
  private SolrSearchEngine engine;
  private Collection collection;

  public SolrToString() {
    this.setLabel("Solr Debug");
    this.setType(SearchElement.Type.DEBUG);
  }

  public Collection getCollection() {
    return collection;
  }

  public void setCollection(Collection collection) {
    this.collection = collection;
  }

  public QueryResponse getResponse() {
    return response;
  }

  public void setResponse(QueryResponse response) {
    this.response = response;
  }

  public SolrQuery getRequest() {
    return request;
  }

  public void setRequest(SolrQuery request) {
    this.request = request;
  }

  public SolrSearchEngine getEngine() {
    return engine;
  }

  public void setEngine(SolrSearchEngine engine) {
    this.engine = engine;
  }

  public String getExternalQueryURL() {
    if (AccessibleSearchEngine.class.isAssignableFrom(this.engine.getClass())) {
      return ((AccessibleSearchEngine) engine).getUrlBase(collection) + "/select?"
          + request.toString();
    } else {
      return null;
    }
  }

  @SearchAdapter
  public static class SolrAdaptor {

    @SearchAdapterMethod(execute = Time.PRE)
    public void addDebug(SolrQuery query) {
      query.set("debug", "true");
    }

    @SearchAdapterMethod(execute = Time.POST)
    public void getDebugInfo(SolrToString searchElement, Collection collection,
        SolrQuery query, QueryResponse response, SolrSearchEngine engine) {
      LOGGER.info("Post SolrToString for collection {}",collection);
      searchElement.setRequest(query);
      searchElement.setResponse(response);
      searchElement.setEngine(engine);
      searchElement.setCollection(collection);
    }
  }
}
