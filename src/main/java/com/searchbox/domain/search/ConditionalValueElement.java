package com.searchbox.domain.search;


public abstract class ConditionalValueElement<K extends Comparable<K>, T extends SearchCondition> 
	extends ValueElement<K> implements
		GenerateSearchCondition<T> {
	
	public ConditionalValueElement(String label) {
		super(label);
	}
	
	public ConditionalValueElement(String label, K value) {
		super(label, value);
	}
	
	@Override
	public abstract T getSearchCondition();
}
