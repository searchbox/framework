package com.searchbox.app.domain;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.searchbox.core.engine.AbstractSearchEngine;
import com.searchbox.core.engine.SearchEngine;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class SearchEngineDefinition extends UnknownClassDefinition 
	implements ElementFactory<SearchEngine<?,?>>{
	
	protected String name;
		
	public SearchEngineDefinition(){
		super();
	}
	public SearchEngineDefinition(Class<?> searchEngineClass, String name){
		super(searchEngineClass);
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public SearchEngine<?, ?> getInstance() {
		AbstractSearchEngine<?, ?> engine = (AbstractSearchEngine<?, ?>) super.toObject();
		engine.setName(this.getName());
		return engine;
	}
}
