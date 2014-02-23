package com.searchbox.core.search;


public abstract class ConditionalSearchElementWithValues<K extends ValueElement, T extends SearchCondition> 
		extends SearchElementWithValues<K>
		implements GenerateSearchCondition<T>, SearchConditionToElementMerger {

	public ConditionalSearchElementWithValues(){ 
		super (null, SearchElement.Type.UNKNOWN);
	}

	public ConditionalSearchElementWithValues(String label,SearchElement.Type type) {
		super(label, type);
	}

	@Override
	public abstract T getSearchCondition();

	public abstract void mergeSearchCondition(SearchCondition condition);
}
