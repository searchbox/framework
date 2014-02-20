package com.searchbox.app.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import com.searchbox.core.engine.SearchEngine;

@Entity
public class CollectionDefinition {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
	
	@Version
	@Column(name="OPTLOCK")
	private long version;
	
	/**
     */
	private String name;

	@ManyToOne
	private SearchEngineDefinition engine;

	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
	private List<FieldDefinition> fieldDefinitions = new ArrayList<FieldDefinition>();
	
	public CollectionDefinition() {
	}
	
	public CollectionDefinition(String name) {
		this.name = name;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SearchEngineDefinition getEngine() {
		return engine;
	}

	public void setEngine(SearchEngineDefinition engine) {
		this.engine = engine;
	}

	public List<FieldDefinition> getFieldDefinitions() {
		return fieldDefinitions;
	}

	public void setFieldDefinitions(List<FieldDefinition> fieldDefinitions) {
		this.fieldDefinitions = fieldDefinitions;
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
