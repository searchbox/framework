package com.searchbox.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.searchbox.domain.search.Hit;
import com.searchbox.domain.search.SearchResult;
import com.searchbox.domain.search.facet.FieldFacet;

@Controller
@RequestMapping("/")
public class HomeController {

	private static Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	@RequestMapping
	public ModelAndView index(@RequestParam(defaultValue = "unknown") String h) {
		ModelAndView model = new ModelAndView("index");

		logger.debug("Here is our param2 " + h);
		model.addObject("h", h);
		return model;
	}

	@RequestMapping("L1")
	@Layout("default")
	public ModelAndView L1index(@RequestParam(defaultValue = "unknown") String h) {
		ModelAndView model = new ModelAndView("index");

		logger.debug("Here is our param2 " + h);
		model.addObject("h", h);
		return model;
	}

	@RequestMapping("L2")
	@Layout("big")
	public ModelAndView L2index(@RequestParam(defaultValue = "unknown") String h) {
		ModelAndView model = new ModelAndView("index");

		logger.debug("Here is our param2 " + h);
		model.addObject("h", h);
		return model;
	}

	@RequestMapping("L3")
	@Layout("big")
	public ModelAndView L3index(@RequestParam(defaultValue = "unknown") String h) {
		ModelAndView model = new ModelAndView("index");

		logger.debug("Here is our param2 " + h);
		model.addObject("h", h);
		throw new RuntimeException("wassup");
	}
	
	@RequestMapping("search")
	public ModelAndView search() {
		ModelAndView model = new ModelAndView("search/index");

		model.addObject("title", "Search results");
		logger.debug("We're in the search controller");

		SearchResult result = new SearchResult();
		String[] fields = { "title", "description" };
		result.setFields(Arrays.asList(fields));
		for (int i = 0; i < 10; i++) {
			Hit hit = new Hit();
			hit.getFields().put("title", "here's my " + i + "th Title");
			String desc = "";
			for (int t = 0; t < Math.random() * 20; t++) {
				desc += "lorem Ipsum dolores invictus amenentum centri. ";
			}
			hit.getFields().put("description", desc);
			hit.setScore(new Float(Math.random() + (10 - i)));
			result.addHit(hit);
		}

		FieldFacet facet = new FieldFacet("Keyword", "keyword");
		facet.addValueElement("Population", 29862);
		facet.addValueElement("Demographic Factors", 28833);
		facet.addValueElement("Developing Countries",27923);
		facet.addValueElement("Research Methodology",25246);
		facet.addValueElement("Family Planning", 23287);
		facet.addValueElement("Population Dynamics", 20919);

		FieldFacet facet2 = new FieldFacet("Institution", "institution");
		facet2.addValueElement("Department of Biology, MIT", 647);
		facet2.addValueElement("Department of Molecular and Cell Biology, University of California, Berkeley.",609);
		facet2.addValueElement("Division of Biology, California Institute of Technology, Pasadena.",558);
		facet2.addValueElement("European Molecular Biology Laboratory, Heidelberg, Germany.",543);
		facet2.addValueElement("ARC", 525);
		result.addElement(facet);
		result.addElement(facet2);

		model.addObject("result", result);
		return model;
	}
}
