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
package com.searchbox.framework.web.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.searchbox.core.ref.Order;
import com.searchbox.core.ref.Sort;
import com.searchbox.framework.domain.PresetDefinition;
import com.searchbox.framework.domain.SearchElementDefinition;
import com.searchbox.framework.domain.UnknownAttributeDefinition;
import com.searchbox.framework.repository.PresetRepository;
import com.searchbox.framework.repository.SearchElementRepository;

@Controller
@RequestMapping("/admin/{searchbox}/searchElementDefinition")
public class SearchElementDefinitionController {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(SearchElementDefinitionController.class);

  @Autowired
  ConversionService conversionService;

  @Autowired
  SearchElementRepository repository;

  @Autowired
  PresetRepository presetRepository;

  @ModelAttribute("OrderEnum")
  public List<Order> getReferenceOrder() {
    return Arrays.asList(Order.values());
  }

  @ModelAttribute("SortEnum")
  public List<Sort> getReferenceSort() {
    return Arrays.asList(Sort.values());
  }

  @ModelAttribute("presetdefinitions")
  public List<PresetDefinition> getPresetDefinitions() {
    ArrayList<PresetDefinition> presetDefinitions = new ArrayList<PresetDefinition>();
    Iterator<PresetDefinition> presets = presetRepository.findAll().iterator();
    while (presets.hasNext()) {
      presetDefinitions.add(presets.next());
    }
    return presetDefinitions;
  }

  @RequestMapping(method = RequestMethod.POST)
  public ModelAndView update(@Valid SearchElementDefinition elementDefinition,
      BindingResult bindingResult, HttpServletRequest httpServletRequest,
      ServerHttpResponse response) {
    LOGGER.info("Creating an filed element: "
        + elementDefinition.getClazz().getSimpleName() + " for preset: "
        + elementDefinition.getPreset().getSlug());

    ModelAndView model = new ModelAndView(
        "admin/SearchElementDefinition/updateForm");

    if (bindingResult.hasErrors()) {
      LOGGER.error("Bindding has error...");
      for (ObjectError error : bindingResult.getAllErrors()) {
        LOGGER.error("Error: " + error.getDefaultMessage());
      }
      response.setStatusCode(HttpStatus.PRECONDITION_FAILED);
      return model;
    }
    try {
      elementDefinition = repository.save(elementDefinition);
    } catch (Exception e) {
      LOGGER.error("Could not save elementDefinition", e);
    }
    model.addObject("searchElementDefinition", elementDefinition);
    return model;
  }

  @RequestMapping(params = "form")
  public String create(Model uiModel) {
    LOGGER.info("Creating EMPTY filed element");
    // populateEditForm(uiModel, new SearchElementDefinition());
    return "/admin/searchElementDefinition/create";
  }

  @RequestMapping(value = "/{id}")
  public ModelAndView show(@PathVariable("id") Long id) {
    LOGGER.info("VIEW an filed element");
    SearchElementDefinition elementDef = repository.findOne(id);
    ModelAndView model = new ModelAndView(
        "admin/SearchElementDefinition/updateForm");
    model.addObject("searchElementDefinition", elementDef);
    return model;
  }

  @InitBinder
  protected void initBinder(WebDataBinder binder) {
    binder.addValidators(new Validator() {

      @Override
      public boolean supports(Class<?> clazz) {
        return SearchElementDefinition.class.isAssignableFrom(clazz);
      }

      @Override
      public void validate(Object target, Errors errors) {
        SearchElementDefinition element = (SearchElementDefinition) target;
        for (UnknownAttributeDefinition attr : element.getAttributes()) {
          if (!attr.getType().getName()
              .equals(attr.getValue().getClass().getName())) {
            if (conversionService.canConvert(attr.getValue().getClass(),
                attr.getType())) {
              attr.setValue(conversionService.convert(attr.getValue(),
                  attr.getType()));
            }
          }
        }
      }
    });
  }
}
