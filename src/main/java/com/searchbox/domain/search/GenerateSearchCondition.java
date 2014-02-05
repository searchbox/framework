package com.searchbox.domain.search;

import org.springframework.core.convert.converter.Converter;


public interface GenerateSearchCondition<K extends SearchCondition> {
	
	public SearchCondition getSearchCondition();
	
	public String geParamValue();
	
	public Converter<String, K> getConverter();
}
