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
import com.searchbox.core.SearchElement;
import com.searchbox.core.SearchElement.Type;
import com.searchbox.core.dm.Collection;
import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.core.dm.SearchableCollection;
import com.searchbox.core.engine.SearchEngine;
import com.searchbox.core.search.AbstractSearchCondition;
import com.searchbox.core.search.RetryElement;
import com.searchbox.framework.model.FieldAttributeEntity;
import com.searchbox.framework.model.PresetEntity;
import com.searchbox.framework.model.SearchElementEntity;
import com.searchbox.framework.model.SearchboxEntity;
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
  public List<SearchboxEntity> getAllSearchboxes() {
    ArrayList<SearchboxEntity> searchboxes = new ArrayList<SearchboxEntity>();
    Iterator<SearchboxEntity> sbx = searchboxRepository.findAll().iterator();
    while (sbx.hasNext()) {
      searchboxes.add(sbx.next());
    }
    return searchboxes;
  }

  @ModelAttribute("presets")
  public List<PresetEntity> getAllPresets(@PathVariable SearchboxEntity searchbox) {
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
  public ModelAndView getHome(@PathVariable SearchboxEntity searchbox,
      HttpServletRequest request, ModelAndView model,
      RedirectAttributes redirectAttributes) {
    model.setViewName(this.getViewFolder() + "/home");

    // TODO when security is true, check LoggedIn
    PresetEntity preset = searchbox.getPresets().first();
    LOGGER.info("No Preset, redirect to: {}", preset.getSlug());
    ModelAndView redirect = new ModelAndView(new RedirectView("/"
        + searchbox.getSlug() + "/" + preset.getSlug(), true));
    return redirect;
  }

  @RequestMapping(value = { "/{preset}", "/{preset}/" })
  public ModelAndView getDefaultPreset(@PathVariable SearchboxEntity searchbox,
      HttpServletRequest request, @PathVariable PresetEntity preset,
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
      @ModelAttribute("searchboxes") List<SearchboxEntity> searchboxes,
      @PathVariable SearchboxEntity searchbox, @PathVariable PresetEntity preset,
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
  
  private Set<FieldAttribute> getAllFieldAttribute(PresetEntity preset){
    Set<FieldAttribute> fieldAttributes = new HashSet<FieldAttribute>();
    for (FieldAttributeEntity def : preset.getFieldAttributes()) {
      fieldAttributes.add(def.build());
    }
    
    //Inheritence of FieldAttribute for children
    if(!preset.getChildren().isEmpty() && preset.getInheritFieldAttributes()){
      for(PresetEntity child:preset.getChildren()){
        for (FieldAttributeEntity def : child.getFieldAttributes()) {
          fieldAttributes.add(def.build());
        } 
      }
    }
    return fieldAttributes;
  }

  protected Set<SearchElement> executeRequest(SearchboxEntity searchbox,
      PresetEntity preset, String process,
      Set<AbstractSearchCondition> conditions, SearchCollector collector) {

    Set<SearchElement> searchElements = elementService.getSearchElements(preset, process);
    LOGGER.info("Required Search elements are {}", searchElements);
    
    Set<FieldAttribute> fieldAttributes = getAllFieldAttribute(preset);
    
    Collection collection = preset.getCollection().build();
    
    if(!(SearchableCollection.class.isAssignableFrom(collection.getClass()))){
      LOGGER.error("Collection {} does NOT implement SearchableCollection!!!",
          collection.getName());
    }
    
    SearchEngine<?, ?> searchEngine = ((SearchableCollection)collection).getSearchEngine();
    
    LOGGER.debug("Current SearchEngine: {}", searchEngine);
    LOGGER.debug("Current Collection: {}", collection);

    Set<SearchElement> resultElements = searchService.execute(searchEngine,
        collection, searchElements, fieldAttributes, conditions, collector);

    LOGGER.info("Resulting SearchElements are {}",resultElements);
    
    // Check if we have a retry clause
    boolean retry = false;
    for (SearchElement element : resultElements) {
      if (RetryElement.class.isAssignableFrom(element.getClass())) {
        if (((RetryElement) element).shouldRetry()) {
          retry = true;
        }
      }
    }

    if (retry) {
      resultElements = searchService.execute(searchEngine, collection,
          searchElements, fieldAttributes, conditions, collector);
    }
    return resultElements;
  }
}
