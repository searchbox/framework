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

import com.searchbox.core.PostSearchAdapter;
import com.searchbox.core.PreSearchAdapter;
import com.searchbox.core.SearchAdapter;
import com.searchbox.core.SearchComponent;
import com.searchbox.core.search.SearchElement;

@SearchComponent
public class SolrToString extends SearchElement  {

	private String query;
	private QueryResponse response;
	
	public SolrToString(){
		super("Solr Debug", SearchElement.Type.DEBUG);
	}
	
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public QueryResponse getResponse() {
		return response;
	}

	public void setResponse(QueryResponse response) {
		this.response = response;
	}

	@SearchAdapter
	public static class SolrAdaptor {

		@PreSearchAdapter
		public SolrQuery addDebug(SolrQuery query) {
			query.set("debug","true");
			return query;
		}

		@PostSearchAdapter
		public SolrToString getDebugInfo(SolrToString searchElement, SolrQuery query,
				QueryResponse response) {
			searchElement.setQuery(query.toString());
			searchElement.setResponse(response);
			return searchElement;
		}
	}
}
