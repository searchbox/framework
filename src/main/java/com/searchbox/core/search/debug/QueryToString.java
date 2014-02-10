package com.searchbox.core.search.debug;


import com.searchbox.anno.SearchAdaptor;
import com.searchbox.anno.SearchComponent;
import com.searchbox.core.adaptor.SearchElementAdapter;
import com.searchbox.core.engine.SolrQuery;
import com.searchbox.core.engine.SolrResponse;
import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.SearchElementType;
import com.searchbox.domain.Collection;
import com.searchbox.domain.Preset;

@SearchComponent
public class QueryToString extends SearchElement  {

	private String query;
	
	public QueryToString(){
		this.type = SearchElementType.DEBUG;
	}
	
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	@SearchAdaptor
	public static class SolrAdaptor implements SearchElementAdapter<QueryToString, SolrQuery, SolrResponse>{

		@Override
		public SolrQuery doAdapt(Collection collection,
				QueryToString SearchElement, SolrQuery query) {
			query.set("debug","true");
			return query;
		}

		@Override
		public QueryToString doAdapt(Preset preset,
				QueryToString searchElement, SolrQuery query,
				SolrResponse response) {
			searchElement.setQuery(query.toString());
			return searchElement;
		}
	}
}
