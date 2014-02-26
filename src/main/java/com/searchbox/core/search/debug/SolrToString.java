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
