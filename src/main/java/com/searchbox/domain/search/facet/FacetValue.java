package com.searchbox.domain.search.facet;

import java.io.Serializable;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
public class FacetValue<K> {

	K value;

	Integer count;
	
	Boolean selected = false;

	public FacetValue(K value, Integer count) {
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
	

	public Boolean getSelected() {
		return selected;
	}

	public FacetValue<K> setSelected(Boolean selected) {
		this.selected = selected;
		return this;
	}
}
