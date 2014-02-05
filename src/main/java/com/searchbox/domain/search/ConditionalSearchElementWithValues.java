package com.searchbox.domain.search;

public abstract class ConditionalSearchElementWithValues<K extends ValueElement<?>, T extends SearchCondition> 
		extends SearchElementWithValues<K>
		implements GenerateSearchCondition<T> {

	public ConditionalSearchElementWithValues(){ super ("");}

	public ConditionalSearchElementWithValues(String label) {
		super(label);
	}

	@Override
	public abstract T getSearchCondition();

}
