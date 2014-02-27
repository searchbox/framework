/*******************************************************************************
 * Copyright Searchbox - http://www.searchbox.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
