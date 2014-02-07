package com.searchbox.domain.search.sort;

import org.apache.lucene.search.Query;

import com.searchbox.ann.search.SearchComponent;
import com.searchbox.domain.search.ConditionalSearchElement;
import com.searchbox.domain.search.SearchCondition;
import com.searchbox.domain.search.SearchElementType;
import com.searchbox.ref.Sort;

@Deprecated
@SearchComponent(prefix = "fs", condition = FieldSort.Condition.class)
public class FieldSort extends ConditionalSearchElement {

	private String fieldName;
	private Sort sort;

	public FieldSort(String FieldName) {
		super("Sort Component");
		this.setType(SearchElementType.SORT);
	}

	@Override
	public String geParamValue() {
		return fieldName + "##" + sort;
	}

	@Override
	public SearchCondition getSearchCondition() {
		return new FieldSort.Condition(fieldName, sort);
	}

	public class Condition extends SearchCondition {

		String fieldName;
		Sort sort;

		Condition(String fieldName, Sort sort) {
			this.fieldName = fieldName;
			this.sort = sort;
		}

		@Override
		protected Query getConditionalQuery() {
			// TODO use DM service to generate the required edismax Query.
			return null;
		}

	}

	@Override
	public void mergeSearchCondition(SearchCondition condition) {
		// TODO Auto-generated method stub
		
	}
}