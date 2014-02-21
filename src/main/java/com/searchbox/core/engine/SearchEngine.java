package com.searchbox.core.engine;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

import com.searchbox.core.dm.Collection;
import com.searchbox.core.dm.Preset;
import com.searchbox.core.search.SearchCondition;
import com.searchbox.core.search.SearchElement;

public interface SearchEngine {

	public Class<?> getQueryClass();
	
	public Boolean isLoaded();
	
	public List<SearchElement> executeSearch(Preset preset, List<SearchCondition> conditions);
	
	public List<SearchElement> getSupportedElements();

	public Boolean supportsElement(SearchElement element);
	
	//TODO Shoudl be able to get a list of "Operations" that is 
	// supported by the engine.
}
