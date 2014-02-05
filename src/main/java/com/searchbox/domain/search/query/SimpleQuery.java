package com.searchbox.domain.search.query;

import org.apache.lucene.search.Query;

import com.searchbox.ann.search.SearchComponent;
import com.searchbox.domain.search.ConditionalSearchElement;
import com.searchbox.domain.search.SearchCondition;
import com.searchbox.domain.search.SearchElementType;

@SearchComponent(prefix = "q", condition = SimpleQuery.Condition.class, converter=SimpleQuery.Converter.class)
public class SimpleQuery extends ConditionalSearchElement<SimpleQuery.Condition> {

	private String q;

	public SimpleQuery() {
		super("query component");
		this.setType(SearchElementType.QUERY);
	}

	@Override
	public String geParamValue() {
		return q;
	}

	@Override
	public SimpleQuery.Condition getSearchCondition() {
		return new SimpleQuery.Condition(q);
	}
	
	public static class Condition extends SearchCondition {

		String q;

		Condition(String query) {
			this.q = query;
		}

		@Override
		protected Query getConditionalQuery() {
			// TODO use DM service to generate the required edismax Query.
			return null;
		}
	}
	

	public static class Converter implements 
	org.springframework.core.convert.converter.Converter<String, SimpleQuery.Condition> {
	
		@Override
		public SimpleQuery.Condition convert(String source) {
			System.out.println(" ~+~+~+~+~+ this is my converter for Q with value: " + source);
			return null;
		}
	}
}
