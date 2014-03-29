package com.searchbox.core.search.sort;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.core.SearchAdapter;
import com.searchbox.core.SearchAdapter.Time;
import com.searchbox.core.SearchAdapterMethod;
import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.core.dm.FieldAttribute.USE;
import com.searchbox.core.engine.SearchEngine;
import com.searchbox.core.query.Query;

@SearchAdapter
public class FieldSortSolrAdatptor {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(FieldSortSolrAdatptor.class);

  @SearchAdapterMethod(execute = Time.PRE)
  public void setSortCondition(SearchEngine engine,
      FieldSort.Condition condition, Query query, FieldAttribute attribute) {
    
     if (attribute.getField().getKey().equals(condition.getField())) {
       attribute.setSortable(true);

      String key = engine.getKeyForField(attribute, USE.SORT);
      query.sort(key, condition.getSort());
    }
  }
}