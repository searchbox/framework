package com.searchbox.core.search.result;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.core.SearchAdapter;
import com.searchbox.core.SearchAdapter.Time;
import com.searchbox.core.SearchAdapterMethod;
import com.searchbox.core.SearchCollector;
import com.searchbox.core.dm.Collection;
import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.core.dm.FieldAttribute.USE;
import com.searchbox.core.engine.SearchEngine;
import com.searchbox.core.query.Query;
import com.searchbox.core.response.Response;

@SearchAdapter
public class TemplateElementSolrAdapter {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(TemplateElementSolrAdapter.class);

  @SearchAdapterMethod(execute = Time.PRE)
  public void setHighlightFieldsForTemplate(SearchEngine engine,
      TemplateElement searchElement, Query query, FieldAttribute attribute) {

    if (!attribute.getHighlight()) {
      return;
    }

    String fieldHighlightKey = engine.getKeyForField(attribute, USE.SEARCH);
    query.addHighlightField(fieldHighlightKey);
  }

  @SearchAdapterMethod(execute = Time.PRE)
  public void setRequieredFieldsForTemplate(SearchEngine engine,
      Collection collection, TemplateElement searchElement, Query query,
      FieldAttribute attribute) {

    if (!query.hasFields()) {
      query.addField("score");
      query.addField(searchElement.getIdField());
    }

    Set<String> fields = searchElement.getRequiredFields();
    LOGGER.debug("Required fields: {}", fields);

    if (fields.contains(attribute.getField().getKey())) {
      String key = engine.getKeyForField(attribute, USE.DEFAULT);
      LOGGER.trace("Adding {} as fl for {}", attribute.getField().getKey(),
          attribute.getLabel());
      query.addField(attribute.getLabel(), attribute.getField().getKey());
    }
  }

  @SearchAdapterMethod(execute = Time.POST)
  public void generateHitElementsForTemplate(TemplateElement element,
      Response response, FieldAttribute attribute, Collection collection,
      SearchCollector collector, Hit hit) {

    LOGGER.debug("Search for ID Attribute. {} Got: {} Needed: {}", (!attribute
        .getField().getKey().equalsIgnoreCase(element.getIdField())), attribute
        .getField().getKey(), element.getIdField());

    if (!attribute.getField().getKey().equalsIgnoreCase(element.getIdField())) {
      return;
    }

    LOGGER.debug("Generate Hit!!! for id {}", attribute.getField().getKey());

    hit.setIdFieldName(element.getIdField());
    hit.setTitleFieldName(element.getTitleField());
    hit.setUrlFieldName(element.getUrlField());

    // Set the template as per element definition
    LOGGER.debug("Template file is {}", element.getTemplateFile());
    hit.setDisplayTemplate(element.getTemplateFile());

    // And we collect the hit for future use :)
    collector.getCollectedItems(element.getCollectorKey()).add(hit);
    // if(!element.getHits().contains(hit)){
    // element.getHits().add(hit);
    // }

  }
}