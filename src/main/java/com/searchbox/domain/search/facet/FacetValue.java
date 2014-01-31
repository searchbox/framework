package com.searchbox.domain.search.facet;

import java.io.Serializable;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
public class FacetValue<K extends Serializable> {

	
	K value;
	
	Integer count;
	
	public FacetValue(K value, Integer count){
		this.value = value;
		this.count = count;
	}

	public K getValue() {
		return value;
	}

	public void setValue(K value) {
		this.value = value;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
}
