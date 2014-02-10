package com.searchbox.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.searchbox.core.adaptor.SearchConditionAdapter;
import com.searchbox.core.adaptor.SearchElementAdapter;
import com.searchbox.core.engine.SearchResponse;
import com.searchbox.core.engine.SolrQuery;
import com.searchbox.core.engine.SolrResponse;
import com.searchbox.core.search.GenerateSearchCondition;
import com.searchbox.core.search.SearchCondition;
import com.searchbox.core.search.SearchConditionToElementMerger;
import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.SearchResult;
import com.searchbox.core.search.debug.QueryToString;
import com.searchbox.core.search.facet.FieldFacet;
import com.searchbox.core.search.query.SimpleQuery;
import com.searchbox.core.search.result.HitList;
import com.searchbox.domain.Preset;
import com.searchbox.domain.SearchElementDefinition;
//import com.searchbox.domain.app.SearchElementDefinition;
import com.searchbox.ref.Order;
import com.searchbox.ref.Sort;

@Service
public class SearchService {
	
	private static Logger logger = LoggerFactory.getLogger(SearchService.class);
	
	@Autowired
	private SearchAdapterService adapterService;
	
	public SearchService() {
		// TODO Auto-generated constructor stub
	}

	public List<SearchElement> execute(Preset preset, List<SearchCondition> conditions) {
		
		List<SearchCondition> presetConditions = new ArrayList<SearchCondition>();
		List<SearchElement> elements = new ArrayList<SearchElement>();
		
		//TODO we have to get this from the preset's collection
		SolrQuery query = new SolrQuery();
		
		for(SearchElementDefinition element:preset.getSearchElements()){
			
			SearchElement selement = element.getSearchElement();
			elements.add(selement);
			
			//Weave in all element conditions in query
			SearchElementAdapter elementAdapter = adapterService.getAdapter(selement);
			if(elementAdapter != null){
				logger.info("Adapting condition from Element: " + selement);
				elementAdapter.doAdapt(preset.getCollection(), selement, query);
			}
			
			if(element.getSearchElement().getClass().isAssignableFrom(GenerateSearchCondition.class)){
				logger.debug("This is a filter right here.");
				presetConditions.add(((GenerateSearchCondition<?>)element).getSearchCondition());
			}
		}
		
		//Weave in all UI Conditions in query
		for(SearchCondition condition:conditions){
			logger.info("Adapting condition from UI: " + condition);
			SearchConditionAdapter conditionAdaptor = adapterService.getAdapter(condition);
			if(conditionAdaptor != null){
				conditionAdaptor.doAdapt(preset.getCollection(), condition, query);
			}
		}
		
		//Weave in all presetConditions in query
		for(SearchCondition condition:presetConditions){
			logger.info("Adapting condition from Preset: " + condition);
			SearchConditionAdapter conditionAdaptor = adapterService.getAdapter(condition);
			if(conditionAdaptor != null){
				conditionAdaptor.doAdapt(preset.getCollection(), condition, query);
			}
		}
				
		SearchResponse result = this.executeSearch(preset, presetConditions, conditions);
		
		//Executing a merge on all SearchConditions
		for(SearchElement element:elements){
			
			//Weave in SearchResponse to element
			SearchElementAdapter elementAdaptor = adapterService.getAdapter(element);
			if(elementAdaptor != null){
				logger.info("Adapting element from Preset: " + element + " for response");
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
		
		logger.info("we got: " + elements.size() + " elements");

		return elements;
	}
	
	private SearchResponse executeSearch(Preset preset,
			List<SearchCondition> presetConditions,
			List<SearchCondition> conditions) {
		
		SolrResponse response = new SolrResponse();
		
//		//response.setResponse(res);
//		
//		SearchResult result = new SearchResult();
//		
//		HitList hitList = new HitList();
//		String[] fields = { "title", "description" };
//		hitList.setFields(Arrays.asList(fields));
//		for (int i = 0; i < 10; i++) {
//			HitList.Hit hit = new HitList.Hit();
//			hit.getValue().put("title", "here's my " + i + "th Title");
//			String desc = "";
//			for (int t = 0; t < Math.random() * 20; t++) {
//				desc += "lorem Ipsum dolores invictus amenentum centri. ";
//			}
//			hit.getValue().put("description", desc);
//			hit.setScore(new Float(Math.random() + (10 - i)));
//			hitList.addHit(hit);
//		}
//		result.addElement(hitList);
//		
//		FieldFacet facet = new FieldFacet("Keyword", "keyword");
//		facet.setOrder(Order.VALUE);
//		facet.setSort(Sort.DESC);
//		facet.addValueElement(facet.new Value("Population", "Population", 29862));
//		facet.addValueElement("Demographic Factors", 40833);
//		facet.addValueElement("Developing Countries",27923);
//		facet.addValueElement("Research Methodology",25246);
//		facet.addValueElement("Family Planning", 23287);
//		facet.addValueElement("Population Dynamics", 20919);
//		
//		FieldFacet facet2 = new FieldFacet("Institution", "institution");
//		facet2.setOrder(Order.KEY);
//		facet2.setSort(Sort.ASC);
//		facet2.addValueElement("Department of Biology, MIT", 647);
//		facet2.addValueElement("Department of Molecular and Cell Biology, University of California, Berkeley.",609);
//		facet2.addValueElement("Division of "
//				+ "Biology, California Institute of Technology, Pasadena.",558);
//		facet2.addValueElement("European Molecular Biology Laboratory, Heidelberg, Germany.",543);
//		facet2.addValueElement("ARC", 525);
//		
//		SimpleQuery query = new SimpleQuery();
//		
//		QueryToString queryDebug = new QueryToString();
//		
//		result.addElement(queryDebug);
//		result.addElement(query);
//		result.addElement(facet);
//		result.addElement(facet2);
//		
		return response;
	}
}