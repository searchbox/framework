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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.searchbox.core.SearchCollector;
import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.core.engine.SearchEngine;
import com.searchbox.core.search.AbstractSearchCondition;
import com.searchbox.core.search.RetryElement;
import com.searchbox.core.search.SearchElement;
import com.searchbox.framework.domain.FieldAttributeDefinition;
import com.searchbox.framework.domain.PresetDefinition;
import com.searchbox.framework.domain.SearchElementDefinition;
import com.searchbox.framework.domain.Searchbox;
import com.searchbox.framework.repository.PresetRepository;
import com.searchbox.framework.repository.SearchboxRepository;
import com.searchbox.framework.service.SearchElementService;
import com.searchbox.framework.service.SearchService;

@Controller
@RequestMapping("/{searchbox}")
public class SearchboxController {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(SearchboxController.class);

  @Autowired
  ApplicationConversionService conversionService;

  @Autowired
  SearchService searchService;

  @Autowired
  SearchboxRepository searchboxRepository;

  @Autowired
  protected PresetRepository presetRepository;

  @Autowired
  protected SearchElementService elementService;

  public SearchboxController() {
  }

  protected String getViewFolder() {
    return "search";
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
  public List<PresetDefinition> getAllPresets(@PathVariable Searchbox searchbox) {
    return presetRepository.findAllBySearchbox(searchbox);
  }

  @ModelAttribute("conditions")
  public Set<AbstractSearchCondition> getSearchConditions(
      HttpServletRequest request) {
    // Fetch all search Conditions within HTTP params
    Set<AbstractSearchCondition> conditions = new HashSet<AbstractSearchCondition>();
    for (String param : conversionService.getSearchConditionParams()) {
      if (request.getParameterValues(param) != null) {
        for (String value : request.getParameterValues(param)) {
          if (value != null && !value.isEmpty()) {
            try {
              AbstractSearchCondition cond = (AbstractSearchCondition) conversionService
                  .convert(value,
                      conversionService.getSearchConditionClass(param));
              conditions.add(cond);
            } catch (Exception e) {
              LOGGER.error("Could not convert " + value, e);
            }
          }
        }
      }
    }
    return conditions;
  }

  @ModelAttribute("collector")
  public SearchCollector getSearchCollector() {
    return new SearchCollector();
  }

  @RequestMapping(value = { "", "/" })
  @ResponseBody
  public ModelAndView getHome(@PathVariable Searchbox searchbox,
      HttpServletRequest request, ModelAndView model,
      RedirectAttributes redirectAttributes) {
    model.setViewName(this.getViewFolder() + "/home");

    // TODO when security is true, check LoggedIn
    PresetDefinition preset = searchbox.getPresets().get(0);
    LOGGER.info("No Preset, redirect to: {}", preset.getSlug());
    ModelAndView redirect = new ModelAndView(new RedirectView("/"
        + searchbox.getSlug() + "/" + preset.getSlug(), true));
    return redirect;
  }

  @RequestMapping(value = { "/{preset}", "/{preset}/" })
  public ModelAndView getDefaultPreset(@PathVariable Searchbox searchbox,
      HttpServletRequest request, @PathVariable PresetDefinition preset,
      ModelAndView model, RedirectAttributes redirectAttributes) {

    String process = preset.getDefaultProcess();
    LOGGER.info(
        "Missing process. Redirecting to default process of preset: {}",
        process);
    ModelAndView redirect = new ModelAndView(new RedirectView("/"
        + searchbox.getSlug() + "/" + preset.getSlug() + "/" + process, true));
    return redirect;

  }

  @RequestMapping(value = { "/{preset}/{process}", "/{preset}/{process}/" })
  public ModelAndView executeSearch(@PathVariable String process,
      @ModelAttribute("searchboxes") List<Searchbox> searchboxes,
      @PathVariable Searchbox searchbox, @PathVariable PresetDefinition preset,
      @ModelAttribute("collector") SearchCollector collector,
      @ModelAttribute("conditions") Set<AbstractSearchCondition> conditions,
      ModelAndView model, RedirectAttributes redirectAttributes) {

    LOGGER.debug("search page for: {} with preset: {} and process: {}",
        searchbox, preset, process);

    // TODO check if we have a view for that process.
    model.setViewName(this.getViewFolder() + "/" + process);

    Set<SearchElement> resultElements = executeRequest(searchbox, preset,
        process, conditions, collector);

    model.addObject("preset", preset);
    model.addObject("process", process);
    model.addObject("elements", resultElements);
    model.addObject("collector", collector);

    return model;
  }

  protected Set<SearchElement> executeRequest(Searchbox searchbox,
      PresetDefinition preset, String process,
      Set<AbstractSearchCondition> conditions, SearchCollector collector) {

    Set<SearchElementDefinition> searchElementDefinitions = new TreeSet<SearchElementDefinition>();
    searchElementDefinitions.addAll(preset
        .getSearchElements(PresetDefinition.DEFAULT_PROCESS));
    searchElementDefinitions.addAll(preset.getSearchElements(process));

    // Build the Preset DTO with dependancies
    Set<SearchElement> searchElements = new TreeSet<SearchElement>();

    // Filter on the elements that we want for the process
    for (SearchElementDefinition elementdefinition : searchElementDefinitions) {
      LOGGER.debug("Adding SearchElementDefinition: {}", elementdefinition);
      try {
        SearchElement searchElement = elementService
            .getSearchElement(elementdefinition);
        LOGGER.trace("Adding SearchElementDefinition: {}", searchElement);
        searchElements.add(searchElement);
      } catch (Exception e) {
        LOGGER
            .error("Could not get SearchElement for: " + elementdefinition, e);
      }
    }

    Set<FieldAttribute> fieldAttributes = new HashSet<FieldAttribute>();
    for (FieldAttributeDefinition def : preset.getFieldAttributes()) {
      fieldAttributes.add(def.getInstance());
    }

    SearchEngine<?, ?> searchEngine = preset.getCollection().getSearchEngine()
        .getInstance();
    searchEngine.setCollection(preset.getCollection().getInstance());

    LOGGER.debug("Current SearchEngine: {}", searchEngine);

    Set<SearchElement> resultElements = searchService.execute(searchEngine,
        searchElements, fieldAttributes, conditions, collector);

    // Check if we have a retry clause
    // TODO put the rety in collector.
    boolean retry = false;
    for (SearchElement element : resultElements) {
      if (RetryElement.class.isAssignableFrom(element.getClass())) {
        if (((RetryElement) element).shouldRetry()) {
          retry = true;
        }
      }
    }

    if (retry) {
      resultElements = searchService.execute(searchEngine, searchElements,
          fieldAttributes, conditions, collector);
    }
    collector.getCollectedItems("test").add("Hello mademoiselle");
    return resultElements;
  }
}
