package com.searchbox.app.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.searchbox.ref.ReflectionUtils;

@Entity
public class SearchEngineDefinition extends DefinitionClass{
	
	protected String description;
	
	protected Boolean lazyLoad = true;
	
	public SearchEngineDefinition(){
		super();
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
