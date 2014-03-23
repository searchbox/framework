package com.searchbox.framework.web;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.searchbox.core.SearchAdapter;
import com.searchbox.core.SearchElement;
import com.searchbox.core.dm.Collection;
import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.core.engine.SearchEngine;
import com.searchbox.core.search.AbstractSearchCondition;
import com.searchbox.framework.model.FieldAttributeEntity;
import com.searchbox.framework.model.PresetEntity;
import com.searchbox.framework.model.SearchElementEntity;
import com.searchbox.framework.model.SearchboxEntity;
import com.searchbox.framework.repository.SearchElementRepository;
import com.searchbox.framework.service.SearchAdapterService;
import com.searchbox.framework.service.SearchElementService;

@Controller
@RequestMapping("/asynch/{searchbox}")
public class ASynchController {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(ASynchController.class);

  @Autowired
  ApplicationConversionService conversionService;

  @Autowired
  private SearchAdapterService adapterService;

  @Autowired
  SearchElementRepository elementRepository;

  @Autowired
  SearchElementService elementService;

  public ASynchController() {
  }

  @RequestMapping(value = { "/{preset}/element/{id}", "/{preset}/element/{id}/" })
  @ResponseBody
  public Map<String, Object> executeAsynchElement(
      @PathVariable SearchboxEntity searchbox, @PathVariable Long id,
      @PathVariable PresetEntity preset, HttpServletRequest request,
      ModelAndView model, RedirectAttributes redirectAttributes) {

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

    LOGGER.debug("Assynch conditions: {}", conditions);

    SearchElementEntity elementDefinition = elementRepository.findOne(id);
    SearchElement element = elementService.getSearchElement(elementDefinition);

    Set<FieldAttribute> fieldAttributes = new HashSet<FieldAttribute>();
    for (FieldAttributeEntity def : preset.getFieldAttributes()) {
      fieldAttributes.add(def.build());
    }

    SearchEngine<?, ?> searchEngine = preset.getCollection().getSearchEngine()
        .build();
    Collection collection = preset.getCollection().build();
    
    Map<String, Object> results = new HashMap<String, Object>();

    adapterService.doAdapt(SearchAdapter.Time.ASYNCH, null, searchEngine, collection,
        searchEngine.newQuery(collection), results, fieldAttributes, element, conditions);

    return results;
  }

}
