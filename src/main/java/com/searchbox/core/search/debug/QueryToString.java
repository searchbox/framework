package com.searchbox.core.search.debug;


import com.searchbox.anno.SearchAdaptor;
import com.searchbox.anno.SearchComponent;
import com.searchbox.core.adaptor.SearchElementAdaptor;
import com.searchbox.core.engine.SolrQuery;
import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.SearchElementType;
import com.searchbox.domain.Collection;

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
	public static class SolrAdaptor implements SearchElementAdaptor<QueryToString, SolrQuery>{
		@Override
		public SolrQuery doAdapt(Collection collection, QueryToString searchElement,
				SolrQuery query) {
			searchElement.setQuery(query.toString());
			return query;
		}
	}
}
