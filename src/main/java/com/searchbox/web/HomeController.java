package com.searchbox.web;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.searchbox.domain.search.Facet;
import com.searchbox.domain.search.Hit;
import com.searchbox.domain.search.SearchResult;
import com.searchbox.domain.search.facet.FacetValue;
import com.searchbox.domain.search.facet.FieldFacet;

@Controller
@RequestMapping("/")
public class HomeController {
	
	private static Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping
	public ModelAndView index(@RequestParam(defaultValue="unknown") String h) {
		ModelAndView model = new ModelAndView("index");
		
		logger.debug("Here is our param2 "+h);
		model.addObject("h", h);
		return model;
	}
	
	
	@RequestMapping("L1")
	@Layout("default")
	public ModelAndView L1index(@RequestParam(defaultValue="unknown") String h) {
		ModelAndView model = new ModelAndView("index");
		
		logger.debug("Here is our param2 "+h);
		model.addObject("h", h);
		return model;
	}
	
	@RequestMapping("L2")
	@Layout("big")
	public ModelAndView L2index(@RequestParam(defaultValue="unknown") String h) {
		ModelAndView model = new ModelAndView("index");
		
		logger.debug("Here is our param2 "+h);
		model.addObject("h", h);
		return model;
	}
	
	@RequestMapping("L3")
	@Layout("big")
	public ModelAndView L3index(@RequestParam(defaultValue="unknown") String h) {
		ModelAndView model = new ModelAndView("index");
		
		logger.debug("Here is our param2 "+h);
		model.addObject("h", h);
			throw new RuntimeException("wassup");
	}
	
	
	
	@RequestMapping("search")
	public ModelAndView search() {
		ModelAndView model = new ModelAndView("search");
		
		model.addObject("title", "Search results");
		logger.debug("We're in the search controller");
		
		SearchResult result = new SearchResult();
		String[] fields = {"title","description"};
		result.setFields(Arrays.asList(fields));
		for(int i=0; i< 10; i++){
			Hit hit = new Hit();
			hit.getFields().put("title", "here's my " + i +"th Title");
			String desc ="";
			for(int t=0; t<Math.random()*20;t++){
				desc += "lorem Ipsum dolores invictus amenentum centri. ";
			}
			hit.getFields().put("description", desc);
			hit.setScore(new Float(Math.random()+(10-i)));
			result.addHit(hit);
		}
		
		Facet facet = new FieldFacet("Keyword", "keyword");
		facet.addFacetValue(new FacetValue<String>("Population","value1",29862));
		facet.addFacetValue(new FacetValue<String>("Demographic Factors","value2",28833));
		facet.addFacetValue(new FacetValue<String>("Developing Countries","value3",27923));
		facet.addFacetValue(new FacetValue<String>("Research Methodology","value3",25246));
		facet.addFacetValue(new FacetValue<String>("Family Planning","value3",23287));
		facet.addFacetValue(new FacetValue<String>("Population Dynamics","value3",20919));
		
		Facet facet2 = new FieldFacet("Institution", "institution");
		facet2.addFacetValue(new FacetValue<String>("Department of Biology, MIT","value1",647));
		facet2.addFacetValue(new FacetValue<String>("Department of Molecular and Cell Biology, University of California, Berkeley.","value3",609));
		facet2.addFacetValue(new FacetValue<String>("Division of Biology, California Institute of Technology, Pasadena.","value3",558));
		facet2.addFacetValue(new FacetValue<String>("European Molecular Biology Laboratory, Heidelberg, Germany.","value3",543));
		facet2.addFacetValue(new FacetValue<String>("ARC","value3",525));
		
		result.addFacet(facet);
		result.addFacet(facet2);
		
		model.addObject("result", result);
		return model;
	}
}
