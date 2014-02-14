package com.searchbox.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/about")
@Layout("search")
public class AboutController {
	
	@RequestMapping
	public ModelAndView about(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("search/index");
		return model;
	}
}
