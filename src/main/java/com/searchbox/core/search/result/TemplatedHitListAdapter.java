package com.searchbox.core.search.result;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.elasticsearch.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.core.SearchAdapter;
import com.searchbox.core.SearchAdapter.Time;
import com.searchbox.core.SearchAdapterMethod;
import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.core.dm.FieldAttribute.USE;
import com.searchbox.engine.solr.SolrSearchEngine;

@SearchAdapter
public class TemplatedHitListAdapter {

  @SuppressWarnings("unused")
  private static final Logger LOGGER = LoggerFactory.getLogger(TemplatedHitListAdapter.class);

  @SearchAdapterMethod(execute = Time.PRE)
  public void setHighlightFieldsForTemplate(SolrSearchEngine engine, TemplatedHitList searchElement, SolrQuery query,
      FieldAttribute attribute) {

    if (!attribute.getHighlight()) {
      return;
    }

    String fieldHighlightKey = engine.getKeyForField(attribute, USE.SEARCH);

    if (query.getHighlightFields() == null || !Arrays.asList(query.getHighlightFields()).contains(fieldHighlightKey)) {
      query.addHighlightField(fieldHighlightKey);
    }
  }

  // @SearchAdapterMethod(execute = Time.PRE)
  // public void addHighlightFieldsForTemplate(TemplatedHitList searchElement,
  // SolrQuery query, FieldAttribute attribute) {
  //
  // if (!attribute.getHighlight()) {
  // return;
  // }
  // if (query.getFacetFields() == null
  // || !Arrays.asList(query.getFacetFields()).contains(
  // attribute.getField().getKey())) {
  // query.addField(attribute.getField().getKey());
  // }
  // }

  @SearchAdapterMethod(execute = Time.PRE)
  public void setRequieredFieldsForTemplate(SolrSearchEngine engine, TemplatedHitList searchElement, SolrQuery query,
      FieldAttribute attribute) {
    
    if(query.getFields() == null){
      query.setFields("score");
    }
    
    Set<String> fields = searchElement.getRequiredFields();
   
    if (fields.contains(attribute.getField().getKey())) {
      String key = engine.getKeyForField(attribute, USE.DEFAULT);
      if (!query.getFields().contains(key)) {
        List<String> qfields = Lists.newArrayList();
        qfields.addAll(Arrays.asList(query.getFields().split(",")));
        qfields.add(attribute.getField().getKey()+":"+key);
        qfields.remove("*");
        query.setFields(qfields.toArray(new String[0]));
      }
    }
  }

  @SearchAdapterMethod(execute = Time.POST)
  public void generateHitElementsForTemplate(TemplatedHitList element, QueryResponse response, FieldAttribute attribute) {

    LOGGER.debug("Search for ID Attribute. {} Got: {} Needed: {}",
        (!attribute.getField().getKey().equalsIgnoreCase(element.getIdField())), attribute.getField().getKey(),
        element.getIdField());

    if (!attribute.getField().getKey().equalsIgnoreCase(element.getIdField())) {
      return;
    }

    Iterator<SolrDocument> documents = response.getResults().iterator();
    while (documents.hasNext()) {
      SolrDocument document = documents.next();
      Hit hit = element.newHit((Float) document.get("score"));
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
            break;
          }
        }
      }
    }
  }
}