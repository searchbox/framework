package com.searchbox.core.adaptor;

import org.springframework.stereotype.Component;

import com.searchbox.core.dm.Collection;
import com.searchbox.core.engine.SearchQuery;
import com.searchbox.core.search.SearchCondition;

@Component
public interface SearchConditionAdapter<K extends SearchCondition, T> {
	public T doAdapt(Collection collection, K condition, T query);
}
