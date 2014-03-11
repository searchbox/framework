/*******************************************************************************
 * Copyright Searchbox - http://www.searchbox.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.searchbox.framework.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.searchbox.core.SearchAdapter;
import com.searchbox.core.dm.Collection;
import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.core.engine.SearchEngine;
import com.searchbox.core.search.AbstractSearchCondition;
import com.searchbox.core.search.GenerateSearchCondition;
import com.searchbox.core.search.SearchConditionToElementMerger;
import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.debug.SearchError;

@Service
public class SearchService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(SearchService.class);

    @Autowired
    private SearchAdapterService adapterService;

    public SearchService() {
    }

    @SuppressWarnings("rawtypes")
    public Set<SearchElement> execute(SearchEngine searchEngine,
            Set<SearchElement> searchElements,
            Set<FieldAttribute> fieldAttributes,
            Set<AbstractSearchCondition> conditions) {

        Object query = searchEngine.newQuery();

        Set<AbstractSearchCondition> presetConditions = new TreeSet<AbstractSearchCondition>();

        // Weave in all SearchElement in Query
        adapterService.doAdapt(SearchAdapter.Time.PRE, null, searchEngine,
                query, fieldAttributes, searchElements);

        for (SearchElement element : searchElements) {
            if (element.getClass().isAssignableFrom(
                    GenerateSearchCondition.class)) {
                LOGGER.debug("This is a filter right here.");
                presetConditions.add(((GenerateSearchCondition<?>) element)
                        .getSearchCondition());
            }
        }

        // Weave in all UI Conditions in query
        LOGGER.debug("Adapting condition from UI: " + conditions);
        adapterService.doAdapt(SearchAdapter.Time.PRE,
                AbstractSearchCondition.class, searchEngine, query,
                fieldAttributes, conditions, searchElements);

        // Weave in all presetConditions in query
        LOGGER.debug("Adapting condition from Preset: " + presetConditions);
        adapterService.doAdapt(SearchAdapter.Time.PRE,
                AbstractSearchCondition.class, searchEngine, query,
                fieldAttributes, presetConditions, searchElements);

        // Executing the query on the search engine!!!
        Object result = null;
        try {
            LOGGER.debug("Using: " + searchEngine);
            result = reflectionExecute(searchEngine, query);
        } catch (Exception e) {
            SearchElement error = new SearchError(e.getMessage(), e);
            error.setPosition(100000);
            LOGGER.debug("Adding search element: " + error);
            searchElements.add(error);
            LOGGER.error("Could not use searchEngine!!!", e);
        }

        // Weave in SearchResponse to element
        Class<?> resultClass = result.getClass();
        adapterService.doAdapt(SearchAdapter.Time.POST, resultClass,
                searchEngine, query, fieldAttributes, conditions,
                presetConditions, result, searchElements);

        // Executing a merge on all SearchConditions
        for (SearchElement element : searchElements) {
            if (SearchConditionToElementMerger.class.isAssignableFrom(element
                    .getClass())) {
                for (AbstractSearchCondition condition : conditions) {
                    if (condition != null) {
                        ((SearchConditionToElementMerger) element)
                                .mergeSearchCondition(condition);
                    }
                }
            }
        }

        LOGGER.debug("we got: " + searchElements.size() + " elements");

        return searchElements;
    }

    private Object reflectionExecute(final SearchEngine<?, ?> engine,
            final Object query) throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {
        Method execute = engine.getClass().getMethod("execute",
                engine.getQueryClass());
        return execute.invoke(engine, query);
    }
}
