package com.searchbox.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.searchbox.ann.search.SearchComponent;
import com.searchbox.domain.search.GenerateSearchCondition;
import com.searchbox.domain.search.Hit;
import com.searchbox.domain.search.SearchCondition;
import com.searchbox.domain.search.SearchResult;
import com.searchbox.domain.search.facet.FieldFacet;
import com.searchbox.domain.search.facet.FieldFacet.Value;
import com.searchbox.ref.Order;
import com.searchbox.ref.Sort;

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
