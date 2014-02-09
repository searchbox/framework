package com.searchbox.core.search.query;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

import com.searchbox.anno.SearchComponent;
import com.searchbox.core.search.ConditionalSearchElement;
import com.searchbox.core.search.SearchCondition;
import com.searchbox.core.search.SearchElementType;

@SearchComponent(prefix = "q", condition = SimpleQuery.Condition.class, converter=SimpleQuery.Converter.class)
public class SimpleQuery extends ConditionalSearchElement<SimpleQuery.Condition> {

	private String query;

	public SimpleQuery() {
		super("query component");
		this.setType(SearchElementType.QUERY);
	}
	
	public SimpleQuery(String query) {
		super("query component");
		this.setType(SearchElementType.QUERY);
		this.query = query;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	@Override
	public String geParamValue() {
		return query;
	}

	@Override
	public SimpleQuery.Condition getSearchCondition() {
		return new SimpleQuery.Condition(query);
	}
	
	public static class Condition extends SearchCondition {

		String query;

		Condition(String query) {
			this.query = query;
		}

		public String getQuery() {
			return query;
		}
	}
	

	public static class Converter implements 
	org.springframework.core.convert.converter.Converter<String, SimpleQuery.Condition> {
	
		@Override
		public SimpleQuery.Condition convert(String source) {
			return new SimpleQuery.Condition(source);
		}
	}


	@Override
	public void mergeSearchCondition(SearchCondition condition) {
		if(SimpleQuery.Condition.class.equals(condition.getClass())){
			this.query = ((SimpleQuery.Condition)condition).getQuery();
		}
	}
}
