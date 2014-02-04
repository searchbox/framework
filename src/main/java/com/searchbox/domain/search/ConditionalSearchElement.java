package com.searchbox.domain.search;


public abstract class ConditionalSearchElement extends SearchElement implements GenerateSearchCondition {

	public ConditionalSearchElement() {
		super(null);
	}
	
	public ConditionalSearchElement(String label) {
		super(label);
	}
	
	public ConditionalSearchElement(String label, Integer position) {
		super(label, position);
	}

	@Override
	public abstract SearchCondition getSearchCondition();
}
