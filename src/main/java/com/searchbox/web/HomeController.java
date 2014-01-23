package com.searchbox.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class HomeController {

	@RequestMapping
	public ModelAndView index(@RequestParam(defaultValue="unknown") String h) {
		ModelAndView model = new ModelAndView("index");
		model.addObject("h", h);
		return model;
	}
}
