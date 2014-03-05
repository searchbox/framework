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
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.searchbox.framework.domain.CollectionDefinition;
import com.searchbox.framework.domain.SearchEngineDefinition;
import com.searchbox.framework.domain.Searchbox;
import com.searchbox.framework.domain.User;
import com.searchbox.framework.repository.CollectionRepository;
import com.searchbox.framework.repository.SearchEngineRepository;
import com.searchbox.framework.repository.SearchboxRepository;

@Controller
@RequestMapping("/")
//@SessionAttributes("user")
public class HomeController {
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	SearchboxRepository searchboxRepository;
	
	@Autowired
	CollectionRepository collectionRepository;
	
	@Autowired
	SearchEngineRepository searchEngineRepository;
	
	@ModelAttribute("collections")
	public List<CollectionDefinition> getAllCollections() {
		ArrayList<CollectionDefinition> list = new ArrayList<CollectionDefinition>();
		Iterator<CollectionDefinition> it = collectionRepository.findAll().iterator();
		while (it.hasNext()) {
			list.add(it.next());
		}
		return list;
	}	
	
	@ModelAttribute("searchengines")
	public List<SearchEngineDefinition> getAllSearchEngines() {
		ArrayList<SearchEngineDefinition> list = new ArrayList<SearchEngineDefinition>();
		Iterator<SearchEngineDefinition> it = searchEngineRepository.findAll().iterator();
		while (it.hasNext()) {
			list.add(it.next());
		}
		return list;
	}	
	
	@ModelAttribute("searchboxes")
	public List<Searchbox> getAllSearchboxes() {
		ArrayList<Searchbox> searchboxes = new ArrayList<Searchbox>();
		Iterator<Searchbox> sbx = searchboxRepository.findAll().iterator();
		while (sbx.hasNext()) {
			searchboxes.add(sbx.next());
		}
		return searchboxes;
	}	
	
//	@ModelAttribute("user")
//	public User getCurrentUser(){
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		LOGGER.info("Got auth {}", auth);
//		if(auth.getClass().isAssignableFrom(User.class)){
//			LOGGER.info("Got user with roles {}", auth.getAuthorities());
//			return (User) auth;
//		} else {
//			LOGGER.info("Anonymous User...");
//			return new User();
//		}
//	}
	
	@RequestMapping()
	public ModelAndView home(@AuthenticationPrincipal User user,
			HttpServletRequest request, ModelAndView model,
			RedirectAttributes redirectAttributes) {

		ModelAndView mav = new ModelAndView("index");
		mav.addObject("user", user);
		return mav;
	}

}
