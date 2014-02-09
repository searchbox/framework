package com.searchbox.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.searchbox.core.search.SearchResult;
import com.searchbox.domain.Collection;
import com.searchbox.domain.Field;

@Service
public interface SearchEngineService {

	public List<Collection> getCollections();

	public List<Field> getFields(Collection collection);

	public boolean setField(Collection collection, Field field);

	public boolean addCollection(Collection collection);

	public SearchResult search();

	public List<String> spell();
}
