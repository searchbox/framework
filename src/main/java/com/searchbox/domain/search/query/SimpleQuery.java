package com.searchbox.domain.search.query;

import org.apache.lucene.search.Query;

import com.searchbox.ann.search.SearchComponent;
import com.searchbox.domain.search.ConditionalSearchElement;
import com.searchbox.domain.search.SearchCondition;
import com.searchbox.domain.search.SearchElementType;

@SearchComponent(prefix="q", condition=SimpleQueryCondition.class)
public class SimpleQuery extends ConditionalSearchElement {

	private String q;
	
	public SimpleQuery() {
		this.setType(SearchElementType.QUERY);
	}

	@Override
	public String geParamValue() {
		return q;
	}

	@Override
	public SearchCondition getSearchCondition() {
		return new SimpleQueryCondition(q);
	}

}

class SimpleQueryCondition extends SearchCondition {

	String q;
	
	SimpleQueryCondition(String query){
		this.q = query;
	}
	
	@Override
	protected Query getConditionalQuery() {
		//TODO use DM service to generate the required edismax Query.
		return null;
	}
	
}
