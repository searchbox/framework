package com.searchbox.core.search.filter;

import com.searchbox.core.SearchComponent;
import com.searchbox.core.search.ConditionalSearchElement;
import com.searchbox.core.search.SearchCondition;

@SearchComponent(urlParam="f")
public class FieldFilter extends ConditionalSearchElement<FieldValueCondition> {

	public FieldFilter(String label, Type type) {
		super(label, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String geParamValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FieldValueCondition getSearchCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void mergeSearchCondition(SearchCondition condition) {
		// TODO Auto-generated method stub
		
	}

}
