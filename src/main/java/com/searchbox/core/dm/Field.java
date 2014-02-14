package com.searchbox.core.dm;

import javax.persistence.ManyToOne;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
public class Field<K> {

	/**
     */
	private String key;

	/**
     */
	private String label;
	
	private K value;
	
	private Boolean multivalue = false;

	public Field(String key) {
		this.key = key;
	}

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String getKey() {
        return this.key;
    }

	public void setKey(String key) {
        this.key = key;
    }

	public String getLabel() {
        return this.label;
    }

	public void setLabel(String label) {
        this.label = label;
    }

	public K getValue() {
        return this.value;
    }

	public void setValue(K value) {
        this.value = value;
    }

	public Boolean getMultivalue() {
        return this.multivalue;
    }

	public void setMultivalue(Boolean multivalue) {
        this.multivalue = multivalue;
    }
}
