package com.searchbox.core.search.result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.core.SearchAdapter;
import com.searchbox.core.SearchAdapter.Time;
import com.searchbox.core.SearchAdapterMethod;
import com.searchbox.core.SearchCollector;
import com.searchbox.core.dm.Collection;
import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.core.dm.FieldAttribute.USE;
import com.searchbox.engine.solr.SolrSearchEngine;

@SearchAdapter
public class TemplateElementSolrAdapter {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(TemplateElementSolrAdapter.class);

  @SearchAdapterMethod(execute = Time.PRE)
  public void setHighlightFieldsForTemplate(SolrSearchEngine engine,
      TemplateElement searchElement, SolrQuery query, FieldAttribute attribute) {

    if (!attribute.getHighlight()) {
      return;
    }

    String fieldHighlightKey = engine.getKeyForField(attribute, USE.SEARCH);
    query.setHighlight(true);
    query.setHighlightSnippets(3);
    
    
    if (query.getHighlightFields() == null
        || !Arrays.asList(query.getHighlightFields()).contains(
            fieldHighlightKey)) {
      query.addHighlightField(fieldHighlightKey);
    }
  }

  @SearchAdapterMethod(execute = Time.PRE)
  public void setRequieredFieldsForTemplate(SolrSearchEngine engine, Collection collection,
      TemplateElement searchElement, SolrQuery query, FieldAttribute attribute) {
    

    // TODO check if template has a template. if not ask for all fields.
    if (query.getFields() == null) {
      query.setFields("score", "[shard]",searchElement.getIdField());
    }

    Set<String> fields = searchElement.getRequiredFields();
    LOGGER.debug("Required fields: {}", fields);

    if (fields.contains(attribute.getField().getKey())) {
      String key = engine.getKeyForField(attribute, USE.DEFAULT);
      LOGGER.trace("Adding {} as fl for {}",key, attribute.getField().getKey());
      if (!query.getFields().contains(key)) {
        List<String> qfields = new ArrayList<>();
        qfields.addAll(Arrays.asList(query.getFields().split(",")));
        qfields.add(attribute.getField().getKey() + ":" + key);
        query.setFields(qfields.toArray(new String[0]));
      }
    }
  }

  @SearchAdapterMethod(execute = Time.POST)
  public void generateHitElementsForTemplate(TemplateElement element,
      QueryResponse response, FieldAttribute attribute, Collection collection,
      SearchCollector collector) {

    LOGGER.debug("Search for ID Attribute. {} Got: {} Needed: {}", (!attribute
        .getField().getKey().equalsIgnoreCase(element.getIdField())), attribute
        .getField().getKey(), element.getIdField());

    if (!attribute.getField().getKey().equalsIgnoreCase(element.getIdField())) {
      return;
    }

    LOGGER.debug("Generate Hit!!! for id {}", attribute.getField().getKey());

    Iterator<SolrDocument> documents = response.getResults().iterator();
    while (documents.hasNext()) {
      SolrDocument document = documents.next();
      Hit hit = new Hit((Float) document.get("score"));

      // Set fields per element configuration
      hit.setIdFieldName(element.getIdField());
      hit.setTitleFieldName(element.getTitleField());
      hit.setUrlFieldName(element.getUrlField());

      // Set the template as per element definition
      LOGGER.debug("Template file is {}", element.getTemplateFile());
      hit.setDisplayTemplate(element.getTemplateFile());

      for (String field : document.getFieldNames()) {
        hit.addFieldValue(field, document.get(field));
      }
      // Now we push the highlights
      Object id = document.getFirstValue(attribute.getField().getKey());
      if(response.getHighlighting() != null){
        Map<String, List<String>> highlights = response.getHighlighting().get(id);
        if(highlights != null){
          for (String highlihgtkey : highlights.keySet()) {
            for (String fieldkey : document.getFieldNames()) {
              if (highlihgtkey.contains(fieldkey)) {
                hit.getHighlights().put(fieldkey, highlights.get(highlihgtkey));
              }
            }
          }
        }
      }

      // And we collect the hit for future use :)
      collector.getCollectedItems(element.getCollectorKey()).add(hit);
//      if(!element.getHits().contains(hit)){
//        element.getHits().add(hit);
//      }
    }
  }
}