package com.searchbox.core.engine;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;

import com.searchbox.core.search.SearchElement;

@Configurable
public abstract class AbstractSearchEngine<Q,R> implements SearchEngine<Q,R>  {
	
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(AbstractSearchEngine.class);
	
	@Autowired
	private ApplicationContext context;
	
	protected String name;
	
	protected String description;
	
	protected Class<Q> queryClass;
	protected Class<R> responseClass;
	
	private Boolean isLoaded = false;
	
	protected AbstractSearchEngine(Class<Q> queryClass, Class<R> responseClass){
		this.queryClass = queryClass;
		this.responseClass = responseClass;
	}
	
	protected AbstractSearchEngine(String name, Class<Q> queryClass, Class<R> responseClass){
		this.name = name;
		this.queryClass = queryClass;
		this.responseClass = responseClass;
	}
	
	protected abstract boolean _load();
	
	public boolean load(){
		return _load();
	}
	
	@Override
	public Class<Q> getQueryClass() {
		return this.queryClass;
	}
	
	@Override
	public Class<R> getResponseClass() {
		return this.responseClass;
	}
	
	@Override
	public Boolean isLoaded(){
		return this.isLoaded;
	}
	
	protected void lodaed(){
		this.isLoaded = true;
	}
	
	@Override
	public Q newQuery(){
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

	@Override
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

	public void setQueryClass(Class<Q> queryClass) {
		this.queryClass = queryClass;
	}

	protected void setResponseClass(Class<R> responseClass) {
		this.responseClass = responseClass;
	}
}
