package com.searchbox.core.adaptor;

import org.springframework.stereotype.Component;

import com.searchbox.core.engine.SearchQuery;
import com.searchbox.core.search.SearchCondition;
import com.searchbox.domain.Collection;

@Component
public interface SearchConditionAdaptor<K extends SearchCondition, T extends SearchQuery> {
	public T doAdapt(Collection collection, K condition, T query);
}
