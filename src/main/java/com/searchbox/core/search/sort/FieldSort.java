package com.searchbox.core.search.sort;

import org.apache.lucene.search.Query;

import com.searchbox.anno.SearchComponent;
import com.searchbox.core.search.ConditionalSearchElement;
import com.searchbox.core.search.SearchCondition;
import com.searchbox.core.search.SearchElementType;
import com.searchbox.ref.Sort;

@Deprecated
@SearchComponent(prefix = "fs", condition = FieldSort.Condition.class)
public class FieldSort extends ConditionalSearchElement<FieldSort.Condition> {

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
	public Condition getSearchCondition() {
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