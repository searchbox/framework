package com.searchbox.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
}
