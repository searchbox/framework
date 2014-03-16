package com.searchbox.framework.service;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.searchbox.core.ref.StringUtils;
import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.result.TemplateElement;
import com.searchbox.framework.domain.SearchElementDefinition;

@Service
public class SearchElementService {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(SearchElementService.class);

  @Autowired
  ApplicationContext context;
  
  public SearchElement getSearchElement(SearchElementDefinition definition) {

    SearchElement element = (SearchElement) definition.toObject();
    element.setLabel(definition.getLabel());
    element.setPosition(definition.getPosition());
    element.setDefinitionId(definition.getId());
    if (definition.getType() != null) {
      element.setType(definition.getType());
    }
    
    //TODO should use an interface
    if(TemplateElement.class.isAssignableFrom(element.getClass())){
      TemplateElement tmpl = (TemplateElement)element;
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
}
