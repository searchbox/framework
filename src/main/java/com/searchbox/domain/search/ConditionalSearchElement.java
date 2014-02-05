package com.searchbox.domain.search;


public abstract class ConditionalSearchElement<K extends SearchCondition> 
	extends SearchElement implements GenerateSearchCondition<K> {

	public ConditionalSearchElement(String label) {
		super(label);
	}
	
	public ConditionalSearchElement(String label, Integer position) {
		super(label, position);
	}

	@Override
	public abstract K getSearchCondition();
}
