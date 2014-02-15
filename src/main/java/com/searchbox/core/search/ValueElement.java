package com.searchbox.core.search;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.searchbox.core.search.facet.FieldFacet.Value;


public abstract class ValueElement implements Serializable, Comparable<ValueElement>{
	
	/**
	 * 
	 */
	protected static final long serialVersionUID = 1L;

	protected String label;
	
	public ValueElement(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	@Override
	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@Override
	public abstract int compareTo(ValueElement other);
}
