package com.searchbox.web;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.searchbox.domain.search.Hit;
import com.searchbox.domain.search.SearchResult;

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
	
	@RequestMapping("search")
	public ModelAndView search() {
		ModelAndView model = new ModelAndView("search");
		
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
		
		model.addObject("result", result);
		return model;
	}
}
