package com.searchbox.core.search;

public abstract class ConditionalValueElement<T extends SearchCondition> 
	extends ValueElement implements
		GenerateSearchCondition<T> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConditionalValueElement(String label) {
		super(label);
	}
	
	@Override
	public abstract T getSearchCondition();
}
