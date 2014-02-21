package com.searchbox.core.engine;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;

import com.searchbox.core.search.SearchElement;

@Configurable
public abstract class AbstractSearchEngine<K> implements SearchEngine {
	
	@Autowired
	private ApplicationContext context;
	
	protected String name;
	
	protected String description;
	
	protected Class<K> queryClass;
	
	private Boolean isLoaded = false;
	
	protected AbstractSearchEngine(Class<K> queryClass){
		this.queryClass = queryClass;
	}
	
	protected AbstractSearchEngine(String name, Class<K> queryClass){
		this.name = name;
		this.queryClass = queryClass;
	}
	
	@Override
	public Class getQueryClass() {
		return this.queryClass;
	}
	
	@Override
	public Boolean isLoaded(){
		return this.isLoaded;
	}
	
	protected void lodaed(){
		this.isLoaded = true;
	}
	
	public K getQueryObject(){
		try {
			return queryClass.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<SearchElement> getSupportedElements() {
		return null;
	}
	
	@Override
	public Boolean supportsElement(SearchElement element){
		//FIXME check if searchegine can actually use Element
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setQueryClass(Class<K> queryClass) {
		this.queryClass = queryClass;
	}
}
