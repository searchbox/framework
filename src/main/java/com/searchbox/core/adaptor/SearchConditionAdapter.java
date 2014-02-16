package com.searchbox.core.adaptor;

import org.springframework.stereotype.Component;

import com.searchbox.core.dm.Preset;
import com.searchbox.core.search.SearchCondition;

@Component
public interface SearchConditionAdapter<K extends SearchCondition, T> {
	public T doAdapt(Preset preset, K condition, T query);
}
