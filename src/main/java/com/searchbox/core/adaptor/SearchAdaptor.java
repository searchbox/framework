package com.searchbox.core.adaptor;

import com.searchbox.core.engine.SearchQuery;
import com.searchbox.core.search.SearchCondition;
import com.searchbox.domain.Collection;

public abstract class SearchAdaptor<K extends SearchCondition, T extends SearchQuery> {
	public abstract T doAdapt(Collection collection, K condition, T query);
}
