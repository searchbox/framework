package com.searchbox.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import com.searchbox.core.engine.SearchEngine;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class CollectionDefinition {
	
	/**
     */
	private String name;

	@ManyToOne
	private SearchEngineDefinition engine;

	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
	private List<FieldDefinition> fieldDefinitions = new ArrayList<FieldDefinition>();
	
	public CollectionDefinition(String name) {
		this.name = name;
	}
	
	
	public FieldDefinition getFieldDefinition(String key){
		for(FieldDefinition def:this.fieldDefinitions){
			if(def.getKey().equals(key)){
				return def;
			}
		}
		return null;
	}
}
