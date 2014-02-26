package com.searchbox.framework.web;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.core.dm.Preset;
import com.searchbox.core.engine.SearchEngine;
import com.searchbox.core.search.CachedContent;
import com.searchbox.core.search.SearchCondition;
import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.SearchResult;
import com.searchbox.framework.domain.FieldAttributeDefinition;
import com.searchbox.framework.domain.PresetDefinition;
import com.searchbox.framework.domain.SearchElementDefinition;
import com.searchbox.framework.domain.Searchbox;
import com.searchbox.framework.repository.FieldAttributeRepository;
import com.searchbox.framework.repository.PresetRepository;
import com.searchbox.framework.repository.SearchEngineRepository;
import com.searchbox.framework.repository.SearchboxRepository;
import com.searchbox.framework.service.ApplicationConversionService;
import com.searchbox.framework.service.DirectoryService;
import com.searchbox.framework.service.SearchEngineService;
import com.searchbox.framework.service.SearchService;

@Controller
public class SearchController {

	private static Logger logger = LoggerFactory
			.getLogger(SearchController.class);

	@Autowired
	ApplicationConversionService conversionService;
	
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
	public List<Preset> getAllPresets(
			@RequestParam(value = "searchbox", required = false) Searchbox searchbox) {
		ArrayList<Preset> presets = new ArrayList<Preset>();
		if (searchbox != null) {
			for (PresetDefinition pdef : presetRepository
					.findAllBySearchbox(searchbox)) {
				presets.add(pdef.getInstance());
			}
		}
		return presets;
	}

	protected String getIndexView() {
		return "search/index";
	}

	protected String getHomeView() {
		return "search/home";
	}

	protected ModelAndView executeSearch(Searchbox searchbox,
			PresetDefinition preset, HttpServletRequest request,
			ModelAndView model) {
		
		model.setViewName(this.getIndexView());	

		// Fetch all search Conditions within HTTP params
		Set<SearchCondition> conditions = new HashSet<SearchCondition>();
		for (String param : conversionService.getSearchConditionParams()) {
			if (request.getParameterValues(param) != null) {
				for (String value : request.getParameterValues(param)) {
					if (value != null && !value.isEmpty()) {
						try {
							SearchCondition cond = (SearchCondition) conversionService
									.convert(value, conversionService
											.getSearchConditionClass(param));
							conditions.add(cond);
						} catch (Exception e) {
							logger.error("Could not convert " + value, e);
						}
					}
				}
			}
		}

		SearchResult result = new SearchResult();

		// Build the Preset DTO with dependancies
		Set<SearchElement> searchElements = new TreeSet<SearchElement>();

		for (SearchElementDefinition elementdefinition : preset.getSearchElements()) {
			try {
				SearchElement searchElement = elementdefinition.getInstance();
				if (CachedContent.class.isAssignableFrom(searchElement
						.getClass())) {
					Integer hash = ((CachedContent) searchElement)
							.getContentHash();

					String tempFile = searchElement.getClass().getSimpleName()
							+ "_" + searchElement.getPosition() + "_" + hash
							+ ".jspx";
					if (!directoryService.fileExists(tempFile)) {
						directoryService.createFile(tempFile,
								((CachedContent) searchElement).getContent());
					}
					((CachedContent) searchElement).setPath(directoryService
							.getApplicationRelativePath(tempFile));
				}
				
				searchElements.add(searchElement);
			} catch (Exception e) {
				logger.error("Could not get SearchElement for: "
						+ elementdefinition, e);
			}
		}
	
		Set<FieldAttribute> fieldAttributes = new HashSet<FieldAttribute>();
		for(FieldAttributeDefinition def:preset.getFieldAttributes()){
			fieldAttributes.add(def.getInstance());
		}
		
		SearchEngine<?, ?> searchEngine = searchEngineService.getSearchEngine(
				preset.getCollection().getSearchEngine().getName());
		
		Set<SearchElement> resultElements =  searchService.execute(searchEngine,
				searchElements, fieldAttributes, conditions);
		
		for (SearchElement element : resultElements) {
			logger.debug("Adding to result view element["
					+ element.getPosition() + "] = " + element.getLabel());
			result.addElement(element);
		}
	
		model.addObject("result", result);
		model.addObject("preset", preset);

		return model;
	}

	@RequestMapping("/{slug}")
	public ModelAndView searchPreset(
			@RequestParam(value = "searchbox", required = false) Searchbox searchbox,
			@PathVariable String slug,
			@ModelAttribute("searchboxes") ArrayList<Searchbox> searchboxes,
			HttpServletRequest request, ModelAndView model,
			RedirectAttributes redirectAttributes) {

		if (searchbox == null) {
			// TODO Deal with empty searchbox
			searchbox = searchboxes.get(0);
			redirectAttributes.addAttribute("searchbox", searchbox);
			ModelAndView redirect = new ModelAndView(new RedirectView(slug,
					true));
			return redirect;
		} else {
			PresetDefinition pdef = presetRepository
					.findPresetDefinitionBySearchboxAndSlug(searchbox, slug);
			
			if (pdef == null) {

				slug = searchbox.getPresets().get(0).getSlug();
				redirectAttributes.addAttribute("preset", slug);
				ModelAndView redirect = new ModelAndView(new RedirectView(slug,
						true));
				return redirect;
			} else {
	
				return executeSearch(searchbox, pdef, request, model);
			}
		}
	}

	@RequestMapping("/")
	public ModelAndView search(
			@RequestParam(value = "searchbox", required = false) Searchbox searchbox,
			@RequestParam(value = "preset", required = false) String slug,
			@ModelAttribute("searchboxes") ArrayList<Searchbox> searchboxes,
			HttpServletRequest request, ModelAndView model,
			RedirectAttributes redirectAttributes) {

		if (searchbox == null) {
			// TODO Deal with empty searchbox
			searchbox = searchboxes.get(0);
			redirectAttributes.addAttribute("searchbox", searchbox);
		}

		if (slug == null) {
			// TODO Deal with empty preset
			slug = searchbox.getPresets().get(0).getSlug();
			redirectAttributes.addAttribute("preset", slug);
		}

		ModelAndView redirect = new ModelAndView(new RedirectView(slug, true));
		return redirect;
	}
}
