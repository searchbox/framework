package com.searchbox.domain.search.query;

import org.apache.lucene.search.Query;
import org.springframework.core.convert.converter.Converter;

import com.searchbox.ann.search.SearchComponent;
import com.searchbox.domain.search.ConditionalSearchElement;
import com.searchbox.domain.search.SearchCondition;
import com.searchbox.domain.search.SearchElementType;

@SearchComponent(prefix = "q", condition = SimpleQuery.Condition.class)
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
		return new SimpleQuery.Condition(q);
	}
	
	@Override
	public Converter<String, SimpleQuery.Condition> getConverter() {
		// TODO Auto-generated method stub
		return null;
	}

	public class Condition extends SearchCondition {

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
}
