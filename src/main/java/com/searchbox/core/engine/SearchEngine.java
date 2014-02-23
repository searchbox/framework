package com.searchbox.core.engine;

import java.util.List;

import javax.persistence.MappedSuperclass;

import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.SearchCondition;

@MappedSuperclass
public interface SearchEngine<Q,R> {
	
	public String getName();
	
	public String getDescription();

	public Class<Q> getQueryClass();
	
	public Class<R> getResponseClass();
	
	public Boolean isLoaded();
	
	public Q newQuery();
	
	public R execute(Q query);
		
	public List<SearchElement> getSupportedElements();

	public Boolean supportsElement(SearchElement element);
	
	public Boolean supportsCondition(SearchCondition condition);

	public boolean load();
}
