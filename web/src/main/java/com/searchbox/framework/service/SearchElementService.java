package com.searchbox.framework.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.searchbox.core.SearchElement;
import com.searchbox.core.ref.StringUtils;
import com.searchbox.core.search.result.TemplateElement;
import com.searchbox.framework.model.PresetEntity;
import com.searchbox.framework.model.SearchElementEntity;

@Service
public class SearchElementService {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(SearchElementService.class);

  @Autowired
  ApplicationContext context;

  Map<String, Set<String>> templateFields;

  public SearchElementService() {
    templateFields = new HashMap<>();
  }

  @Transactional
  public SearchElement getSearchElement(SearchElementEntity<?> definition) {

    SearchElement element = (SearchElement) definition.build();
    element.setLabel(definition.getLabel());
    element.setPosition(definition.getPosition());
    // if (definition.getType() != null) {
    // element.setType(definition.getType());
    // }

    // TODO should use an interface
    if (TemplateElement.class.isAssignableFrom(element.getClass())) {
      TemplateElement tmpl = (TemplateElement) element;
      try {
        if (!templateFields.containsKey(tmpl.getTemplateFile())) {
          Resource resource = context.getResource(tmpl.getTemplateFile());
          LOGGER.debug("Read file for template from: {}", resource);
          String content = FileUtils.readFileToString(resource.getFile());
          LOGGER.trace("File content for template is {}", content);
          templateFields.put(tmpl.getTemplateFile(),
              StringUtils.extractHitFields(content));
        }
        tmpl.setFields(templateFields.get(tmpl.getTemplateFile()));

      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return element;
  }

  @Transactional
  public Set<SearchElement> getSearchElements(PresetEntity preset,
      String process) {

    Set<SearchElementEntity<?>> elementEntities = new TreeSet<SearchElementEntity<?>>();

    elementEntities.addAll(preset
        .getSearchElements(PresetEntity.DEFAULT_PROCESS));
    elementEntities.addAll(preset.getSearchElements(true, process));

    // Build the Preset DTO with dependancies
    Set<SearchElement> searchElements = new TreeSet<SearchElement>();

    // Filter on the elements that we want for the process
    for (SearchElementEntity<?> elementEntity : elementEntities) {
      LOGGER.debug("Adding SearchElementDefinition: {}", elementEntity);
      try {
        SearchElement searchElement = this.getSearchElement(elementEntity);

        LOGGER.trace("Adding SearchElementDefinition: {}", searchElement);
        searchElements.add(searchElement);
      } catch (Exception e) {
        LOGGER.error("Could not get SearchElement for: {}", elementEntity, e);
      }
    }

    return searchElements;
  }

}
