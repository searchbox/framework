package com.searchbox.app.domain;

import javax.persistence.Entity;

import com.searchbox.core.engine.AbstractSearchEngine;

@Entity
public class SearchEngineDefinition extends DefinitionClass{
	
	protected String description;
	
	protected Boolean lazyLoad = true;
	
	public SearchEngineDefinition(){
		super();
	}
	
	public AbstractSearchEngine<?, ?> toEngine(){
		AbstractSearchEngine<?, ?> engine = (AbstractSearchEngine<?, ?>) super.toObject();
		return engine;
	}
	
	public SearchEngineDefinition(String name, Class<?> searchEngineClass){
		super(name, searchEngineClass);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getLazyLoad() {
		return lazyLoad;
	}

	public void setLazyLoad(Boolean lazyLoad) {
		this.lazyLoad = lazyLoad;
	}
}
