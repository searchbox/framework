package com.searchbox.core.engine;

import java.util.List;

import com.searchbox.core.search.SearchElement;

public interface SearchEngine<Q,R> {
	
	public String getName();

	public Class<Q> getQueryClass();
	
	public Class<R> getResponseClass();
	
	public Boolean isLoaded();
	
	public Q newQuery();
	
	public R execute(Q query);
		
	public List<SearchElement> getSupportedElements();

	public Boolean supportsElement(SearchElement element);
	
	public boolean load();
	//TODO Shoudl be able to get a list of "Operations" that is 
	// supported by the engine.
}
