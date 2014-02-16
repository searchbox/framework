package com.searchbox.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.searchbox.core.adaptor.SearchConditionAdapter;
import com.searchbox.core.adaptor.SearchElementAdapter;
import com.searchbox.core.dm.Preset;
import com.searchbox.core.search.GenerateSearchCondition;
import com.searchbox.core.search.SearchCondition;
import com.searchbox.core.search.SearchConditionToElementMerger;
import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.debug.SearchError;
//import com.searchbox.domain.app.SearchElementDefinition;

@Service
public class SearchService {
	
	private static Logger logger = LoggerFactory.getLogger(SearchService.class);
	
	@Autowired
	private SearchAdapterService adapterService;
	
	@Autowired
	private SearchEngineService searchEngineService;
	
	public SearchService() {
		// TODO Auto-generated constructor stub
	}

	public List<SearchElement> execute(Preset preset, List<SearchCondition> conditions) {
		
		List<SearchCondition> presetConditions = new ArrayList<SearchCondition>();
		List<SearchElement> elements = new ArrayList<SearchElement>();
		
		//TODO we have to get this from the preset's collection
		SolrQuery query = new SolrQuery();
		
		for(SearchElement element:preset.getSearchElements()){
			
			elements.add(element);
			
			//Weave in all element conditions in query
			SearchElementAdapter elementAdapter = adapterService.getAdapter(element);
			if(elementAdapter != null){
				logger.debug("Adapting condition from Element: " + element);
				elementAdapter.doAdapt(preset, element, query);
			}
			
			if(element.getClass().isAssignableFrom(GenerateSearchCondition.class)){
				logger.debug("This is a filter right here.");
				presetConditions.add(((GenerateSearchCondition<?>)element).getSearchCondition());
			}
		}
		
		//Weave in all UI Conditions in query
		for(SearchCondition condition:conditions){
			logger.debug("Adapting condition from UI: " + condition);
			SearchConditionAdapter conditionAdaptor = adapterService.getAdapter(condition);
			if(conditionAdaptor != null){
				conditionAdaptor.doAdapt(preset, condition, query);
			}
		}
		
		//Weave in all presetConditions in query
		for(SearchCondition condition:presetConditions){
			logger.debug("Adapting condition from Preset: " + condition);
			SearchConditionAdapter conditionAdaptor = adapterService.getAdapter(condition);
			if(conditionAdaptor != null){
				conditionAdaptor.doAdapt(preset, condition, query);
			}
		}
				
		//Executing the query on the search engine!!! 
		SolrResponse result = null;
		try {
			logger.debug("Using: " + this.searchEngineService.server);
			result = this.searchEngineService.getResponse(query);
		} catch (Exception e) {
			SearchElement error = new SearchError(e.getMessage(), e);
			logger.debug("Adding search element: " + error);
			elements.add(error);
			logger.error("Could not use searchEngine!!!", e);
		}
		
		
		//Executing a merge on all SearchConditions
		for(SearchElement element:elements){
			
			//Weave in SearchResponse to element
			SearchElementAdapter elementAdaptor = adapterService.getAdapter(element);
			if(elementAdaptor != null){
				logger.debug("Adapting element from Preset: " + element + " for response");
				//TODO the casting here is because we only suport Solr now.
				elementAdaptor.doAdapt(preset, element, query, (SolrResponse) result);
			}
			
			if(SearchConditionToElementMerger.class.isAssignableFrom(element.getClass())){
				for(SearchCondition condition:conditions){
					if(condition != null){
						((SearchConditionToElementMerger)element).mergeSearchCondition(condition);
					}
				}
			}
		}
		
		logger.debug("we got: " + elements.size() + " elements");

		return elements;
	}
}