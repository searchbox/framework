package com.searchbox.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.searchbox.core.dm.Preset;
import com.searchbox.domain.PresetDefinition;
import com.searchbox.domain.Searchbox;

@Controller
@RequestMapping("/about")
@Layout("search")
public class AboutController {

	@RequestMapping
	public ModelAndView about(HttpServletRequest request) {
		// That should come from the searchbox param/filter
		Searchbox searchbox = Searchbox.findAllSearchboxes().get(0);

		List<Preset> presets = new ArrayList<Preset>();
		Preset currentPreset = null;
		for(PresetDefinition pdef:searchbox.getPresets()){
			Preset pset = pdef.getElement();
			presets.add(pset);
		}
		
		ModelAndView model = new ModelAndView("about/index");
		model.addObject("presets", presets);
		model.addObject("searchbox", searchbox);
		return model;
	}
}
