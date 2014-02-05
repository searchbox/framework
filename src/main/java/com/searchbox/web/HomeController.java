package com.searchbox.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class HomeController {
	
	
	private static Logger logger = LoggerFactory.getLogger(HomeController.class);

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
}
