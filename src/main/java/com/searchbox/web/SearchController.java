package com.searchbox.web;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.searchbox.domain.search.Hit;
import com.searchbox.domain.search.SearchCondition;
import com.searchbox.domain.search.SearchElementType;
import com.searchbox.domain.search.SearchResult;
import com.searchbox.domain.search.facet.FieldFacet;
import com.searchbox.domain.search.facet.FieldFacet.Value;
import com.searchbox.domain.search.query.SimpleQuery;
import com.searchbox.ref.Order;
import com.searchbox.ref.Sort;

@Controller
@RequestMapping("/search")
public class SearchController {

	private static Logger logger = LoggerFactory.getLogger(HomeController.class);

	public SearchController() {
	}
	
	@RequestMapping
	public ModelAndView search(HttpServletRequest request) {
		
		for(Object key:request.getParameterMap().keySet()){
			for(String value:request.getParameterValues((String)key)){
				logger.info("#####: " + key + " : " + value);
			}
		}
		
		//@RequestParam(value="conditions") List<SearchCondition> searchConditions
//		for(SearchCondition searchCondition:searchConditions){
//			logger.info("GOT CONDITION: " + searchCondition);
//		}
		
		ModelAndView model = new ModelAndView("search/index");

		model.addObject("title", "Search results");

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
		facet.setOrder(Order.VALUE);
		facet.setSort(Sort.DESC);
		facet.addValueElement(facet.new Value("Population", "Population", 29862).setSelected(true));
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
		facet2.addValueElement("Division of Biology, California Institute of Technology, Pasadena.",558);
		facet2.addValueElement("European Molecular Biology Laboratory, Heidelberg, Germany.",543);
		facet2.addValueElement("ARC", 525);
		
		SimpleQuery query = new SimpleQuery();
		
		result.addElement(query);
		result.addElement(facet);
		result.addElement(facet2);
		
		model.addObject("result", result);
		return model;
	}

}
