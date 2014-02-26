package com.searchbox.framework.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.searchbox.core.dm.Collection;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class CollectionDefinition extends UnknownClassDefinition implements ElementFactory<Collection> {

	@ManyToOne
	private SearchEngineDefinition searchEngine;

	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
	private Set<FieldDefinition> fieldDefinitions = new HashSet<FieldDefinition>();
	
	public CollectionDefinition() {
		super();
	}
	
	public CollectionDefinition(String name, SearchEngineDefinition definition) {
		super();
		this.searchEngine = definition;
	}
	
	public SearchEngineDefinition getSearchEngine() {
		return searchEngine;
	}

	public void setSearchEngine(SearchEngineDefinition searchEngine) {
		this.searchEngine = searchEngine;
	}

	public SearchEngineDefinition getSearchEngineDefinition() {
		return searchEngine;
	}

	public void setSearchEngineDefinition(SearchEngineDefinition engine) {
		this.searchEngine = engine;
	}

	public Set<FieldDefinition> getFieldDefinitions() {
		return fieldDefinitions;
	}

	public void setFieldDefinitions(Set<FieldDefinition> fieldDefinitions) {
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
	
	@Override
	public Collection getInstance() {
		// TODO Auto-generated method stub
		return null;
	}
}
