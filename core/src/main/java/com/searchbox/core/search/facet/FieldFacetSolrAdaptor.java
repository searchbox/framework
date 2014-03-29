package com.searchbox.core.search.facet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.core.SearchAdapter;
import com.searchbox.core.SearchAdapter.Time;
import com.searchbox.core.SearchAdapterMethod;
import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.core.engine.SearchEngine;
import com.searchbox.core.query.Query;
import com.searchbox.core.response.Response;

@SearchAdapter
public class FieldFacetSolrAdaptor {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(FieldFacetSolrAdaptor.class);

  @SearchAdapterMethod(execute = Time.PRE)
  public void addFacetField(SearchEngine engine, FieldFacet facet, Query query,
      FieldAttribute attribute) {

    if (attribute.getField().getKey().equals(facet.getFieldName())) {
      String facetKey = engine.getKeyForField(attribute);
      query.addFacet(facetKey, facet.getSticky());
    }
  }

  @SearchAdapterMethod(execute = Time.POST)
  public void getFacetValues(SearchEngine engine, FieldFacet fieldFacet,
      Response response, FieldAttribute attribute) {

    if (fieldFacet.getFieldName() == null) {
      LOGGER.error("FieldFacet \"" + fieldFacet.getLabel()
          + "\" has no field!!!");
      return;
    }

    if (!attribute.getField().getKey().equals(fieldFacet.getFieldName())) {
      return;
    }

    // String facetKey = engine.getKeyForField(attribute);
    // if (response.getFacetFields() != null) {
    // for (Facet facet : response.getFacetFields()) {
    // if (facet.getName().equals(facetKey)) {
    // for (Count value : facet.getValues()) {
    // fieldFacet.addValueElement(value.getName(), (int) value.getCount());
    // }
    // }
    // }
    // }
  }
}