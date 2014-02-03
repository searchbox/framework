package com.searchbox.domain.search;


public abstract class ConditionalSearchElementWithValues<K extends ValueElement<?>> 
		extends SearchElementWithValues<K>
		implements GenerateSearchCondition {

	public ConditionalSearchElementWithValues(String label) {
		super(label);
	}

	@Override
	public abstract SearchCondition getSearchCondition();

}
