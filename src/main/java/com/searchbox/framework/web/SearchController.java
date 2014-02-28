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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.core.engine.SearchEngine;
import com.searchbox.core.search.CachedContent;
import com.searchbox.core.search.SearchCondition;
import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.SearchResult;
import com.searchbox.framework.domain.FieldAttributeDefinition;
import com.searchbox.framework.domain.FieldDefinition;
import com.searchbox.framework.domain.PresetDefinition;
import com.searchbox.framework.domain.SearchElementDefinition;
import com.searchbox.framework.domain.Searchbox;
import com.searchbox.framework.repository.FieldAttributeRepository;
import com.searchbox.framework.repository.PresetRepository;
import com.searchbox.framework.repository.SearchEngineRepository;
import com.searchbox.framework.repository.SearchboxRepository;
import com.searchbox.framework.service.DirectoryService;
import com.searchbox.framework.service.SearchEngineService;
import com.searchbox.framework.service.SearchService;

@Controller
@RequestMapping("/{searchbox}")
public class SearchController {

	private static Logger logger = LoggerFactory
			.getLogger(SearchController.class);

	@Autowired
	ApplicationConversionService applicationConversionService;

	@Autowired
	ConversionService conversionService;

	@Autowired
	SearchService searchService;

	@Autowired
	SearchEngineService searchEngineService;

	@Autowired
	DirectoryService directoryService;

	@Autowired
	SearchboxRepository searchboxRepository;

	@Autowired
	FieldAttributeRepository fieldAttributeRepository;

	@Autowired
	protected PresetRepository presetRepository;

	@Autowired
	protected SearchEngineRepository searchEngineRepository;

	public SearchController() {
	}

	protected String getSearchView() {
		return "search/index";
	}

	protected String getHitView() {
		return "search/view";
	}

	protected String getSearchUrl(Searchbox searchbox, PresetDefinition preset) {
		return "/" + searchbox.getSlug() + "/search/" + preset.getSlug();
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

	@ModelAttribute("presets")
	public List<PresetDefinition> getAllPresets(
			@PathVariable Searchbox searchbox) {
		return presetRepository.findAllBySearchbox(searchbox);
	}

	@RequestMapping(value = { "/search", "/search/" })
	public ModelAndView getDefaultPreset(@PathVariable Searchbox searchbox,
			HttpServletRequest request, ModelAndView model,
			RedirectAttributes redirectAttributes) {

		logger.info("Missing preset. Redirecting to first preset of searchbox: "
				+ searchbox.getName());
		PresetDefinition preset = searchbox.getPresets().get(0);
		ModelAndView redirect = new ModelAndView(new RedirectView(getSearchUrl(
				searchbox, preset), true));
		return redirect;

	}

	@RequestMapping("/view/{preset}")
	public ModelAndView executeView(
			@ModelAttribute("searchboxes") List<Searchbox> searchboxes,
			@PathVariable Searchbox searchbox,
			@PathVariable PresetDefinition preset, HttpServletRequest request,
			ModelAndView model, RedirectAttributes redirectAttributes) {

		logger.info("search page for: " + searchbox + " with preset:" + preset);
		model.setViewName(this.getSearchView());

		SearchResult result = new SearchResult();
		Set<SearchElement> resultElements = executeRequest(searchbox, preset,
				request, SearchElement.Type.QUERY, SearchElement.Type.FILTER, 
				SearchElement.Type.DEBUG, SearchElement.Type.INSPECT);
		for (SearchElement element : resultElements) {
			logger.debug("Adding to result view element["
					+ element.getPosition() + "] = " + element.getLabel());
			result.addElement(element);
		}

		model.addObject("result", result);
		model.addObject("preset", preset);

		return model;
	}

	@RequestMapping("/search/{preset}")
	public ModelAndView executeSearch(
			@ModelAttribute("searchboxes") List<Searchbox> searchboxes,
			@PathVariable Searchbox searchbox,
			@PathVariable PresetDefinition preset, HttpServletRequest request,
			ModelAndView model, RedirectAttributes redirectAttributes) {

		logger.info("search page for: " + searchbox + " with preset:" + preset);
		model.setViewName(this.getSearchView());

		SearchResult result = new SearchResult();
		Set<SearchElement> resultElements = executeRequest(searchbox, preset,
				request, SearchElement.Type.QUERY, SearchElement.Type.FACET,
				SearchElement.Type.FILTER, SearchElement.Type.SORT,
				SearchElement.Type.DEBUG, SearchElement.Type.VIEW);

		for (SearchElement element : resultElements) {
			logger.debug("Adding to result view element["
					+ element.getPosition() + "] = " + element.getLabel());
			result.addElement(element);
		}

		model.addObject("result", result);
		model.addObject("preset", preset);

		return model;
	}

	private Set<SearchElement> executeRequest(Searchbox searchbox,
			PresetDefinition preset, HttpServletRequest request,
			SearchElement.Type... types) {

		// Fetch all search Conditions within HTTP params
		Set<SearchCondition> conditions = new HashSet<SearchCondition>();
		for (String param : applicationConversionService
				.getSearchConditionParams()) {
			if (request.getParameterValues(param) != null) {
				for (String value : request.getParameterValues(param)) {
					if (value != null && !value.isEmpty()) {
						try {
							SearchCondition cond = (SearchCondition) conversionService
									.convert(
											value,
											applicationConversionService
													.getSearchConditionClass(param));
							conditions.add(cond);
						} catch (Exception e) {
							logger.error("Could not convert " + value, e);
						}
					}
				}
			}
		}

		// Build the Preset DTO with dependancies
		Set<SearchElement> searchElements = new TreeSet<SearchElement>();

		for (SearchElementDefinition elementdefinition : preset
				.getSearchElements()) {
			try {
				SearchElement searchElement = elementdefinition.getInstance();
				if (Arrays.asList(types).contains(
						searchElement.getElementType())) {

					if (CachedContent.class.isAssignableFrom(searchElement
							.getClass())) {
						Integer hash = ((CachedContent) searchElement)
								.getContentHash();

						String tempFile = searchElement.getClass()
								.getSimpleName()
								+ "_"
								+ searchElement.getPosition()
								+ "_"
								+ hash
								+ ".jspx";
						if (!directoryService.fileExists(tempFile)) {
							directoryService.createFile(tempFile,
									((CachedContent) searchElement)
											.getContent());
						}
						((CachedContent) searchElement)
								.setPath(directoryService
										.getApplicationRelativePath(tempFile));
					}
					searchElements.add(searchElement);
				}
			} catch (Exception e) {
				logger.error("Could not get SearchElement for: "
						+ elementdefinition, e);
			}
		}

		Set<FieldAttribute> fieldAttributes = new HashSet<FieldAttribute>();
		for (FieldAttributeDefinition def : preset.getFieldAttributes()) {
			fieldAttributes.add(def.getInstance());
		}

		SearchEngine<?, ?> searchEngine = searchEngineService
				.getSearchEngine(preset.getCollection().getSearchEngine()
						.getName());

		Set<SearchElement> resultElements = searchService.execute(searchEngine,
				searchElements, fieldAttributes, conditions);

		return resultElements;
	}
}
