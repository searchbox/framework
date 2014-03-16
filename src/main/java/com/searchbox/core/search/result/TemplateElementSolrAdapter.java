package com.searchbox.core.search.result;

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
import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.core.dm.FieldAttribute.USE;
import com.searchbox.engine.solr.SolrSearchEngine;

@SearchAdapter
public class TemplateElementSolrAdapter {

  private static final Logger LOGGER = LoggerFactory.getLogger(TemplateElementSolrAdapter.class);

  @SearchAdapterMethod(execute = Time.PRE)
  public void setHighlightFieldsForTemplate(SolrSearchEngine engine, 
      TemplateElement searchElement, SolrQuery query, FieldAttribute attribute) {

    if (!attribute.getHighlight()) {
      return;
    }

    String fieldHighlightKey = engine.getKeyForField(attribute, USE.SEARCH);

    if (query.getHighlightFields() == null || !Arrays.asList(
        query.getHighlightFields()).contains(fieldHighlightKey)) {
      query.addHighlightField(fieldHighlightKey);
    }
  }
  
  
  @SearchAdapterMethod(execute = Time.PRE)
  public void setRequieredFieldsForTemplate(SolrSearchEngine engine,
      TemplateElement searchElement, SolrQuery query,
      FieldAttribute attribute) {
    
    //TODO check if template has a template. if not ask for all fields.
    if(query.getFields() == null){
      query.setFields("score","[shard]","*");
    }
    
    Set<String> fields = searchElement.getRequiredFields();
   
    /** FIXME this is REQUIRED!!! 
    if (fields.contains(attribute.getField().getKey())) {
      String key = engine.getKeyForField(attribute, USE.DEFAULT);
      if (!query.getFields().contains(key)) {
        List<String> qfields = Lists.newArrayList();
        qfields.addAll(Arrays.asList(query.getFields().split(",")));
        qfields.add(attribute.getField().getKey().replace("-", "\\-")+":"+key);
        query.setFields(qfields.toArray(new String[0]));
      }
    }
    LOGGER.info("Query is: " + query);
    */
  }

  @SearchAdapterMethod(execute = Time.POST)
  public void generateHitElementsForTemplate(TemplateElement element,
      QueryResponse response, FieldAttribute attribute, 
      SearchCollector collector) {
   
    LOGGER.debug("Search for ID Attribute. {} Got: {} Needed: {}",
        (!attribute.getField().getKey().equalsIgnoreCase(element.getIdField())),
        attribute.getField().getKey(),
        element.getIdField());

    if (!attribute.getField().getKey().equalsIgnoreCase(element.getIdField())) {
      return;
    }

    LOGGER.info("Generate Hit!!!");

    Iterator<SolrDocument> documents = response.getResults().iterator();
    while (documents.hasNext()) {
      SolrDocument document = documents.next();
      Hit hit = new Hit((Float) document.get("score"));
      for (String field : document.getFieldNames()) {
        hit.addFieldValue(field, document.get(field));
      }
      // Now we push the highlights
      Object id = document.getFirstValue(attribute.getField().getKey());
      Map<String, List<String>> highlights = response.getHighlighting().get(id);
      for (String highlihgtkey : highlights.keySet()) {
        for (String fieldkey : document.getFieldNames()) {
          if (highlihgtkey.contains(fieldkey)) {
            hit.getHighlights().put(fieldkey, highlights.get(highlihgtkey));
          }
        }
      }
      
      //Set the template as per element definition
      LOGGER.info("Template file is {}", element.getTemplateFile());
      hit.setDisplayTemplate(element.getTemplateFile());
      
      //And we collect the hit for future use :)
      collector.getCollectedItems(element.getCollectorKey()).add(hit);
    }
  }
}