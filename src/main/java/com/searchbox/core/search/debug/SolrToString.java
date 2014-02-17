package com.searchbox.core.search.debug;


import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;

import com.searchbox.anno.SearchAdaptor;
import com.searchbox.anno.SearchComponent;
import com.searchbox.core.adaptor.SolrElementAdapter;
import com.searchbox.core.dm.Preset;
import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.SearchElementType;

@SearchComponent
public class SolrToString extends SearchElement  {

	private String query;
	private QueryResponse response;
	
	public SolrToString(){
		super("Solr Debug");
		this.type = SearchElementType.VIEW;
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

	@SearchAdaptor
	public static class SolrAdaptor implements SolrElementAdapter<SolrToString>{

		@Override
		public SolrQuery doAdapt(Preset preset,
				SolrToString SearchElement, SolrQuery query) {
			query.set("debug","true");
			return query;
		}

		@Override
		public SolrToString doAdapt(Preset preset,
				SolrToString searchElement, SolrQuery query,
				QueryResponse response) {
			searchElement.setQuery(query.toString());
			searchElement.setResponse(response);
			return searchElement;
		}
	}
}
