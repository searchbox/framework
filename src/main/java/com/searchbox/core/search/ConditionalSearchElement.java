package com.searchbox.core.search;



public abstract class ConditionalSearchElement<K extends SearchCondition> 
	extends SearchElement implements GenerateSearchCondition<K>, SearchConditionToElementMerger {

	public ConditionalSearchElement(String label,SearchElement.Type type) {
		super(label,type);
	}
	
	@Override
	public abstract K getSearchCondition();
	
	public abstract void mergeSearchCondition(SearchCondition condition);
}
