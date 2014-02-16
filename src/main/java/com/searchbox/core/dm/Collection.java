package com.searchbox.core.dm;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.searchbox.core.engine.SearchEngine;

@RooJavaBean
@RooToString
public class Collection {

	/**
     */
	private String name;

	private SearchEngine engine;

	private List<Field> fields = new ArrayList<Field>();

	public Collection(String name) {
		this.name = name;
	}

	public Collection(String name, SearchEngine engine) {
		this.name = name;
		this.engine = engine;
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

	public SearchEngine getEngine() {
        return this.engine;
    }

	public void setEngine(SearchEngine engine) {
        this.engine = engine;
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
