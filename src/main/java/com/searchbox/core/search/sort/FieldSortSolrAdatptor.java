package com.searchbox.core.search.sort;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.core.SearchAdapter;
import com.searchbox.core.SearchAdapter.Time;
import com.searchbox.core.SearchAdapterMethod;
import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.core.dm.FieldAttribute.USE;
import com.searchbox.core.ref.Sort;
import com.searchbox.engine.solr.SolrSearchEngine;

@SearchAdapter
public class FieldSortSolrAdatptor {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(FieldSortSolrAdatptor.class);

  @SearchAdapterMethod(execute = Time.PRE)
  public void setSortCondition(SolrSearchEngine engine,
      FieldSort.Condition condition, SolrQuery query, FieldAttribute attribute) {
    if (condition.getField().equals("score")) {
      if (condition.getSort().equals(Sort.ASC)) {
        query.addSort("score", ORDER.asc);
      } else {
        query.addSort("score", ORDER.desc);
      }
    } else if (attribute.getField().getKey().equals(condition.getField())) {
      attribute.setSortable(true);
      if (condition.getSort().equals(Sort.ASC)) {
        query.addSort(engine.getKeyForField(attribute, USE.SORT), ORDER.asc);
      } else {
        query.addSort(engine.getKeyForField(attribute, USE.SORT), ORDER.desc);
      }
    }
  }
}