package com.searchbox.framework.service;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.searchbox.core.SearchElement;
import com.searchbox.core.SearchElement.Type;
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
        Resource resource = context.getResource(tmpl.getTemplateFile());
        LOGGER.debug("Read file for template from: {}", resource);
        String content = FileUtils.readFileToString(resource.getFile());
        LOGGER.trace("File content for template is {}", content);
        tmpl.setFields(StringUtils.extractHitFields(content));
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return element;
  }

  public Set<SearchElement> getSearchElements(PresetEntity preset,
      String process) {

    Set<SearchElementEntity<?>> elementEntities = new TreeSet<SearchElementEntity<?>>();

    elementEntities.addAll(preset
        .getSearchElements(PresetEntity.DEFAULT_PROCESS));
    elementEntities.addAll(preset.getSearchElements(process));

    // Build the Preset DTO with dependancies
    Set<SearchElement> searchElements = new TreeSet<SearchElement>();

    // Filter on the elements that we want for the process
    for (SearchElementEntity<?> elementEntity : elementEntities) {
      LOGGER.info("Adding SearchElementDefinition: {}", elementEntity);
      try {
        SearchElement searchElement = this.getSearchElement(elementEntity);

        LOGGER.trace("Adding SearchElementDefinition: {}", searchElement);
        searchElements.add(searchElement);
      } catch (Exception e) {
        LOGGER.error("Could not get SearchElement for: {}", elementEntity, e);
      }
    }

    // Get the required SearchElement from childrens
    // TODO should be generaly done for all getters of SearchElement
    for (PresetEntity child : preset.getChildren()) {
      elementEntities = new TreeSet<SearchElementEntity<?>>();
      elementEntities.addAll(child
          .getSearchElements(PresetEntity.DEFAULT_PROCESS));
      elementEntities.addAll(child.getSearchElements(process));
      for (SearchElementEntity<?> elementEntity : elementEntities) {
        if (preset.getInheritedTypes().contains(elementEntity.getClazz())) {
          searchElements.add(this.getSearchElement(elementEntity));
        }
      }
    }
    return searchElements;
  }

}
