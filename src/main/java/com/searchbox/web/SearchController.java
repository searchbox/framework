package com.searchbox.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.searchbox.core.dm.Preset;
import com.searchbox.core.search.SearchCondition;
import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.SearchResult;
import com.searchbox.domain.PresetDefinition;
import com.searchbox.domain.SearchElementDefinition;
import com.searchbox.domain.Searchbox;
import com.searchbox.service.ApplicationConversionService;
import com.searchbox.service.SearchService;

@Controller
@RequestMapping("/*")
@Layout("search")
public class SearchController {

	private static Logger logger = LoggerFactory.getLogger(SearchController.class);

	@Autowired
	ConversionService conversionService;
	
	@Autowired
	ApplicationConversionService searchComponentService;
	
	@Autowired
	SearchService searchService;
	
	public SearchController() {
	}
	
	@RequestMapping
	public ModelAndView search(@RequestParam(value="searchbox", required=false) String searchboxSlug, 
			HttpServletRequest request) {
		logger.info("No Slug given, select default Preset");
		return search(searchboxSlug, "", request);
	}
	
	@RequestMapping("{presetSlug}")
//	public ModelAndView search(@RequestParam("ff") FieldFacet.ValueCondition condition) {
	public ModelAndView search(@RequestParam(value="searchbox", required=false) String searchboxSlug, 
			@PathVariable String presetSlug, HttpServletRequest request) {
		
		//That should come from the searchbox param/filter
		List<Searchbox> searchboxes = Searchbox.findAllSearchboxes();
		Searchbox searchbox = null;
		for(Searchbox sb:searchboxes){
			if(sb.getSlug().equals(searchboxSlug)){
				searchbox = sb;
			}
		}
		
		if(searchbox == null){
			ModelAndView model = new ModelAndView("search/searchbox");
			model.addObject("searchboxes",searchboxes);
			return model;
		}
		
		
		List<Preset> presets = new ArrayList<Preset>();
		Preset currentPreset = null;
		for(PresetDefinition pdef:searchbox.getPresets()){
			Preset pset = pdef.getElement();
			if(pset.getSlug().equals(presetSlug)){
				currentPreset = pset;
			}
			presets.add(pset);
		}
		
		if(currentPreset == null && presets.size() > 0){
			currentPreset = presets.get(0);
		}
					
		List<SearchCondition> conditions = new ArrayList<SearchCondition>();
		
		for(String param:searchComponentService.getSearchConditionParams()){
			if(request.getParameterValues(param) != null){
				for(String value:request.getParameterValues(param)){
					if(value != null && !value.isEmpty()){
						SearchCondition cond = (SearchCondition) conversionService.convert(value, searchComponentService.getSearchConditionClass(param));
						conditions.add(cond);
					}
				}
			}
		}
		
		SearchResult result = new SearchResult();
		
		if(currentPreset != null){
			for(SearchElement element:searchService.execute(currentPreset, conditions)){
				logger.debug("Adding to result view element: " + element);
				result.addElement(element);
			}
		}
		
		ModelAndView model = new ModelAndView("search/index");
		model.addObject("result", result);
		model.addObject("presets",presets);
		model.addObject("searchboxes",searchboxes);
		model.addObject("currentSearchbox",searchbox);
		model.addObject("currentPreset", currentPreset);
		
		return model;
	}
}
