package com.searchbox.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

import com.searchbox.app.domain.PresetDefinition;
import com.searchbox.app.domain.PresetFieldAttributeDefinition;
import com.searchbox.app.domain.SearchElementDefinition;
import com.searchbox.app.domain.Searchbox;
import com.searchbox.app.repository.PresetDefinitionRepository;
import com.searchbox.app.repository.SearchboxRepository;
import com.searchbox.core.dm.Preset;
import com.searchbox.core.search.CachedContent;
import com.searchbox.core.search.SearchCondition;
import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.SearchResult;
import com.searchbox.service.DirectoryService;
import com.searchbox.service.SearchService;

@Controller
public class SearchController {

	private static Logger logger = LoggerFactory
			.getLogger(SearchController.class);

	@Autowired
	ConversionService conversionService;

	@Autowired
	ApplicationConversionService searchComponentService;

	@Autowired
	SearchService searchService;

	@Autowired
	DirectoryService directoryService;

	@Autowired
	SearchboxRepository searchboxRepository;

	@Autowired
	protected
	PresetDefinitionRepository presetDefinitionRepository;

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
			@RequestParam(value="searchbox", required=false) Searchbox searchbox) {
		ArrayList<Preset> presets = new ArrayList<Preset>();
		if(searchbox != null){
			for (PresetDefinition pdef : presetDefinitionRepository
					.findAllBySearchbox(searchbox)) {
				presets.add(pdef.toPreset());
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
			PresetDefinition presetDefinition, HttpServletRequest request,
			ModelAndView model) {

		if (searchbox == null) {
			model.setViewName(this.getHomeView());
			return model;
		} else {
			model.setViewName(this.getIndexView());
		}

		if (presetDefinition == null) {
			logger.info("No preset, should forward to first preset");
			presetDefinition = searchbox.getPresets().get(0);
		}

		// Fetch all search Conditions within HTTP params
		List<SearchCondition> conditions = new ArrayList<SearchCondition>();
		for (String param : searchComponentService.getSearchConditionParams()) {
			if (request.getParameterValues(param) != null) {
				for (String value : request.getParameterValues(param)) {
					if (value != null && !value.isEmpty()) {
						try {
							SearchCondition cond = (SearchCondition) conversionService
									.convert(value, searchComponentService
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
		Preset preset = presetDefinition.toPreset();
		for (SearchElementDefinition elementdefinition : presetDefinition
				.getSearchElements()) {
			try {
				SearchElement searchElement = elementdefinition.toElement();
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
				preset.addSearchElement(searchElement);
			} catch (Exception e) {
				logger.error("Could not get SearchElement for: "
						+ elementdefinition, e);
			}
		}
		for (PresetFieldAttributeDefinition fielddefinition : presetDefinition
				.getFieldAttributes()) {
			try {
				preset.addFieldAttribute(fielddefinition
						.toPresetFieldAttribute());
			} catch (Exception e) {
				logger.error("Could not get SearchElement for: "
						+ fielddefinition);
			}
		}

		if (preset != null) {
			for (SearchElement element : searchService.execute(preset,
					conditions)) {
				logger.debug("Adding to result view element["
						+ element.getPosition() + "] = " + element.getLabel());
				result.addElement(element);
			}
		}

		model.addObject("result", result);
		model.addObject("preset", preset);

		return model;
	}

	@RequestMapping("/{slug}")
	public ModelAndView searchPreset(
			@RequestParam(value="searchbox", required=false) Searchbox searchbox,
			@PathVariable String slug,
			@ModelAttribute("searchboxes") ArrayList<Searchbox> searchboxes,
			HttpServletRequest request, ModelAndView model,
			RedirectAttributes redirectAttributes) {
		
		
		if(searchbox == null){
			//TODO Deal with empty searchbox
			searchbox = searchboxes.get(0);
			redirectAttributes.addAttribute("searchbox", searchbox);
			ModelAndView redirect = new ModelAndView(new RedirectView(slug, true));
			return redirect;
		}

		PresetDefinition pdef = presetDefinitionRepository
				.findPresetDefinitionBySearchboxAndSlug(searchbox, slug);

		if(pdef == null){
			slug = searchbox.getPresets().get(0).getSlug();			
			redirectAttributes.addAttribute("preset", slug);
			ModelAndView redirect = new ModelAndView(new RedirectView(slug, true));
			return redirect;
		}
		
		return executeSearch(searchbox, pdef, request, model);
	}

	@RequestMapping("/")
	public ModelAndView search(
			@RequestParam(value="searchbox", required=false) Searchbox searchbox,
			@RequestParam(value = "preset", required = false) String slug,
			@ModelAttribute("searchboxes") ArrayList<Searchbox> searchboxes,
			HttpServletRequest request, ModelAndView model,
			RedirectAttributes redirectAttributes) {
		
		if(searchbox == null){
			//TODO Deal with empty searchbox
			searchbox = searchboxes.get(0);
			redirectAttributes.addAttribute("searchbox", searchbox);
		}
		
		if(slug == null){
			//TODO Deal with empty preset
			slug = searchbox.getPresets().get(0).getSlug();			
			redirectAttributes.addAttribute("preset", slug);
		}
		
		ModelAndView redirect = new ModelAndView(new RedirectView(slug, true));
		return redirect;
	}
}
