package com.searchbox.core.search.debug;


import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;

import com.searchbox.anno.SearchAdapter;
import com.searchbox.anno.SearchAdapterMethod;
import com.searchbox.anno.SearchAdapterMethod.Target;
import com.searchbox.anno.SearchComponent;
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

	@SearchAdapter(target=SolrToString.class)
	public static class SolrAdaptor {

		@SearchAdapterMethod(target=Target.PRE)
		public SolrQuery addDebug(SolrQuery query) {
			query.set("debug","true");
			return query;
		}

		@SearchAdapterMethod(target=Target.POST)
		public SolrToString getDebugInfo(SolrToString searchElement, SolrQuery query,
				QueryResponse response) {
			searchElement.setQuery(query.toString());
			searchElement.setResponse(response);
			return searchElement;
		}
	}
}
