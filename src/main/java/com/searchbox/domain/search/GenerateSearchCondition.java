package com.searchbox.domain.search;


public interface GenerateSearchCondition<K extends SearchCondition> {
	
	public K getSearchCondition();
	
	//TODO remove that goes to the conversion package...
	public String geParamValue();
}
