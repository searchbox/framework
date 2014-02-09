package com.searchbox.core.adaptor;

import org.springframework.stereotype.Component;

import com.searchbox.core.engine.SearchQuery;
import com.searchbox.core.engine.SolrResponse;
import com.searchbox.core.search.SearchElement;
import com.searchbox.domain.Collection;
import com.searchbox.domain.Preset;

@Component
public interface SearchElementAdaptor<K extends SearchElement, T extends SearchQuery, U extends SolrResponse> {	
	
	public T doAdapt(Collection collection, K searchElement, T query);
	
	public K doAdapt(Preset preset, K searchElement, T query,  U response);
}
