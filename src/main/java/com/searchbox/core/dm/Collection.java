package com.searchbox.core.dm;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Collection {

	/**
     */
	private String name;

	private List<Field> fields = new ArrayList<Field>();

	public Collection(String name) {
		this.name = name;
	}

	public void addField(Field field) {
		this.fields.add(field);
	}

	public String getName() {
        return this.name;
    }

	public void setName(String name) {
        this.name = name;
    }

	public List<Field> getFields() {
        return this.fields;
    }

	public void setFields(List<Field> fields) {
        this.fields = fields;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
