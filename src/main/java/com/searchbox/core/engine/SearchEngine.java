package com.searchbox.core.engine;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

import com.searchbox.core.dm.Collection;
import com.searchbox.core.dm.Preset;
import com.searchbox.core.search.SearchCondition;
import com.searchbox.core.search.SearchElement;

public interface SearchEngine<Q,R> {

	public Class<Q> getQueryClass();
	
	public Class<R> getResponseClass();
	
	public Boolean isLoaded();
	
	public Q newQuery();
	
	public R execute(Q query);
		
	public List<SearchElement> getSupportedElements();

	public Boolean supportsElement(SearchElement element);
	
	//TODO Shoudl be able to get a list of "Operations" that is 
	// supported by the engine.
}
