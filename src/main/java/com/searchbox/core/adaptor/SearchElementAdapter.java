package com.searchbox.core.adaptor;

import org.springframework.stereotype.Component;

import com.searchbox.core.dm.Collection;
import com.searchbox.core.dm.Preset;
import com.searchbox.core.search.SearchElement;

@Component
public interface SearchElementAdapter<K extends SearchElement, T, U> {	
	
	public T doAdapt(Collection collection, K searchElement, T query);
	
	public K doAdapt(Preset preset, K searchElement, T query,  U response);
}
