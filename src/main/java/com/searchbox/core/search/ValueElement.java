package com.searchbox.core.search;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public class ValueElement<K> {
	
	protected String label;
	
	protected K value;

	public ValueElement(String label) {
		this.label = label;
	}
	
	public ValueElement(String label, K value){
		this.label = label;
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public K getValue() {
		return value;
	}

	public void setValue(K value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
