package com.searchbox.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.core.engine.SearchEngine;
import com.searchbox.core.search.GenerateSearchCondition;
import com.searchbox.core.search.SearchCondition;
import com.searchbox.core.search.SearchConditionToElementMerger;
import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.debug.SearchError;

@Service
public class SearchService {

	private static Logger logger = LoggerFactory.getLogger(SearchService.class);

	@Autowired
	private SearchAdapterService adapterService;
	
	public SearchService() {
	}

	

	@SuppressWarnings("rawtypes")
	public Set<SearchElement> execute(SearchEngine searchEngine, 
			Set<SearchElement> searchElements,
			Set<FieldAttribute> fieldAttributes,
			Set<SearchCondition> conditions) {

		Object query = searchEngine.newQuery();

		Set<SearchCondition> presetConditions = new TreeSet<SearchCondition>();

		
		for (SearchElement element : searchElements) {

			adapterService.doPreSearchAdapt(element, searchEngine, query, fieldAttributes);

			if (element.getClass().isAssignableFrom(
					GenerateSearchCondition.class)) {
				logger.debug("This is a filter right here.");
				presetConditions.add(((GenerateSearchCondition<?>) element)
						.getSearchCondition());
			}
		}

		// Weave in all UI Conditions in query
		for (SearchCondition condition : conditions) {
			logger.debug("Adapting condition from UI: " + condition);
			adapterService.doPreSearchAdapt(searchElements, searchEngine, query, 
					fieldAttributes, condition);
		}

		// Weave in all presetConditions in query
		for (SearchCondition condition : presetConditions) {
			logger.debug("Adapting condition from Preset: " + condition);
			adapterService.doPreSearchAdapt(searchElements, searchEngine, query, 
					fieldAttributes, condition);
		}

		// Executing the query on the search engine!!!
		Object result = null;
		try {
			logger.debug("Using: " + searchEngine);
			result = reflectionExecute(searchEngine, query);
		} catch (Exception e) {
			SearchElement error = new SearchError(e.getMessage(), e);
			error.setPosition(100000);
			logger.debug("Adding search element: " + error);
			searchElements.add(error);
			logger.error("Could not use searchEngine!!!", e);
		}

		// Executing a merge on all SearchConditions
		for (SearchElement element : searchElements) {

			// Weave in SearchResponse to element
			adapterService.doPostSearchAdapt(element, searchEngine, query, 
					fieldAttributes, conditions, presetConditions, result);

			if (SearchConditionToElementMerger.class.isAssignableFrom(element
					.getClass())) {
				for (SearchCondition condition : conditions) {
					if (condition != null) {
						((SearchConditionToElementMerger) element)
								.mergeSearchCondition(condition);
					}
				}
			}
		}

		logger.debug("we got: " + searchElements.size() + " elements");

		return searchElements;
	}

	private Object reflectionExecute(final SearchEngine<?, ?> engine,
			final Object query) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method execute = engine.getClass().getMethod("execute",
				engine.getQueryClass());
		Object result = execute.invoke(engine, query);
		return result;
	}
}