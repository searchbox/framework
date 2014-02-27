package com.searchbox.framework.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.searchbox.framework.domain.PresetDefinition;
import com.searchbox.framework.domain.Searchbox;
import com.searchbox.framework.repository.SearchboxRepository;

//@Controller
//@RequestMapping("/about")
public class AboutController {
	
	@Autowired
	SearchboxRepository searchboxRepository;

	@RequestMapping
	public ModelAndView about(HttpServletRequest request) {
		// That should come from the searchbox param/filter
		Searchbox searchbox = searchboxRepository.findAll().iterator().next();

		List<PresetDefinition> presets = new ArrayList<PresetDefinition>();
		for(PresetDefinition pdef:searchbox.getPresets()){
			presets.add(pdef);
		}
		
		ModelAndView model = new ModelAndView("about/index");
		model.addObject("presets", presets);
		model.addObject("searchbox", searchbox);
		return model;
	}
}
