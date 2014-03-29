package com.searchbox.core.search.filter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.core.SearchAdapter;
import com.searchbox.core.SearchAdapter.Time;
import com.searchbox.core.SearchAdapterMethod;
import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.core.engine.SearchEngine;
import com.searchbox.core.query.Query;

@SearchAdapter
public class FieldValueConditionSolrAdaptor {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(FieldValueConditionSolrAdaptor.class);
  

  @SearchAdapterMethod(execute = Time.PRE)
  public void createFilterQueries(SearchEngine engine,
      FieldAttribute attribute, FieldValueCondition condition, Query query) {


    if (attribute.getField().getKey().equals(condition.getFieldName())) {
          LOGGER.debug("Adapting fieldValueCondition {}",condition);
      String conditionValue = condition.getValue();
      String facetKey = engine.getKeyForField(attribute);
      query.addFieldValueCondition(facetKey, conditionValue, condition.getTaged());
      return;
    }
  }
}