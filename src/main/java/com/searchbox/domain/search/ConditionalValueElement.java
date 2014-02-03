package com.searchbox.domain.search;

import com.searchbox.domain.search.facet.FieldFacet.Value;


public abstract class ConditionalValueElement<K extends Comparable<K>> extends ValueElement<K> implements
		GenerateSearchCondition {

	public ConditionalValueElement(String label) {
		super(label);
	}
	
	public ConditionalValueElement(String label, K value) {
		super(label, value);
	}
	
	@Override
	public abstract SearchCondition getSearchCondition();
}
