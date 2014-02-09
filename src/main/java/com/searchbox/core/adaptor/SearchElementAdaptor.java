package com.searchbox.core.adaptor;

import org.springframework.stereotype.Component;

import com.searchbox.core.engine.SearchQuery;
import com.searchbox.core.search.SearchElement;
import com.searchbox.domain.Collection;

@Component
public interface SearchElementAdaptor<K extends SearchElement, T extends SearchQuery> {	
	public T doAdapt(Collection collection, K SearchElement, T query);
}
