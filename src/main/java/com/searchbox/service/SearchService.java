package com.searchbox.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.searchbox.domain.app.Preset;
import com.searchbox.domain.app.SearchElementDefinition;
import com.searchbox.domain.search.GenerateSearchCondition;
import com.searchbox.domain.search.HitList;
import com.searchbox.domain.search.SearchCondition;
import com.searchbox.domain.search.SearchConditionToElementMerger;
//import com.searchbox.domain.app.SearchElementDefinition;
import com.searchbox.domain.search.SearchElement;
import com.searchbox.domain.search.SearchResult;
import com.searchbox.domain.search.facet.FieldFacet;
import com.searchbox.domain.search.query.SimpleQuery;
import com.searchbox.ref.Order;
import com.searchbox.ref.Sort;

@Service
public class SearchService {
	
	private static Logger logger = LoggerFactory.getLogger(SearchService.class);
	
//	@Autowired
//	private SearchEngineService searchEngineService;
//	
//	@Autowired
//	private SearchComponentService searchComponentService;

	public SearchService() {
		// TODO Auto-generated constructor stub
	}

	public SearchResult execute(Preset preset, List<SearchCondition> conditions) {
		
		List<SearchCondition> presetConditions = new ArrayList<SearchCondition>();
		for(SearchElementDefinition element:preset.getSearchElements()){
			if(element.getSearchElement().getClass().isAssignableFrom(GenerateSearchCondition.class)){
				logger.debug("This is a filter right here.");
				presetConditions.add(((GenerateSearchCondition<?>)element).getSearchCondition());
			}
		}
		
		SearchResult result = this.executeSearch(preset, presetConditions, conditions);
		
		for(SearchElement element:result.getElements()){
			if(SearchConditionToElementMerger.class.isAssignableFrom(element.getClass())){
				for(SearchCondition condition:conditions){
					if(condition != null){
						((SearchConditionToElementMerger)element).mergeSearchCondition(condition);
					}
				}
			}
		}
		//Executing a merge on all SearchConditions
		
		
		return result;
	}
	
	private SearchResult executeSearch(Preset preset,
			List<SearchCondition> presetConditions,
			List<SearchCondition> conditions) {
		
		SearchResult result = new SearchResult();
		
		HitList hitList = new HitList();
		String[] fields = { "title", "description" };
		hitList.setFields(Arrays.asList(fields));
		for (int i = 0; i < 10; i++) {
			HitList.Hit hit = new HitList.Hit();
			hit.getValue().put("title", "here's my " + i + "th Title");
			String desc = "";
			for (int t = 0; t < Math.random() * 20; t++) {
				desc += "lorem Ipsum dolores invictus amenentum centri. ";
			}
			hit.getValue().put("description", desc);
			hit.setScore(new Float(Math.random() + (10 - i)));
			hitList.addHit(hit);
		}
		result.addElement(hitList);
		
		FieldFacet facet = new FieldFacet("Keyword", "keyword");
		facet.setOrder(Order.VALUE);
		facet.setSort(Sort.DESC);
		facet.addValueElement(facet.new Value("Population", "Population", 29862));
		facet.addValueElement("Demographic Factors", 40833);
		facet.addValueElement("Developing Countries",27923);
		facet.addValueElement("Research Methodology",25246);
		facet.addValueElement("Family Planning", 23287);
		facet.addValueElement("Population Dynamics", 20919);
		
		FieldFacet facet2 = new FieldFacet("Institution", "institution");
		facet2.setOrder(Order.KEY);
		facet2.setSort(Sort.ASC);
		facet2.addValueElement("Department of Biology, MIT", 647);
		facet2.addValueElement("Department of Molecular and Cell Biology, University of California, Berkeley.",609);
		facet2.addValueElement("Division of "
				+ "Biology, California Institute of Technology, Pasadena.",558);
		facet2.addValueElement("European Molecular Biology Laboratory, Heidelberg, Germany.",543);
		facet2.addValueElement("ARC", 525);
		
		SimpleQuery query = new SimpleQuery();
		
		result.addElement(query);
		result.addElement(facet);
		result.addElement(facet2);
		
		return result;
	}
}