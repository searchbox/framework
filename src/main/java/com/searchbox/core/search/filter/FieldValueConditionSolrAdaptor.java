package com.searchbox.core.search.filter;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.core.SearchAdapter;
import com.searchbox.core.SearchAdapter.Time;
import com.searchbox.core.SearchAdapterMethod;
import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.engine.solr.SolrSearchEngine;

@SearchAdapter
public class FieldValueConditionSolrAdaptor {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(FieldValueConditionSolrAdaptor.class);
  

  @SearchAdapterMethod(execute = Time.PRE)
  public void createFilterQueries(SolrSearchEngine engine,
      FieldAttribute attribute, FieldValueCondition condition, SolrQuery query) {

    LOGGER.debug("Adapting fieldValueCondition {}",condition);

    if (!attribute.getField().getKey().equals(condition.getFieldName())) {
      return;
    }

    String conditionValue = condition.getValue();
    String facetKey = engine.getKeyForField(attribute);

    boolean isnew = true;
    List<String> fqs = new ArrayList<String>();
    if (query.getFilterQueries() != null) {
      for (String fq : query.getFilterQueries()) {
        if (fq.contains(facetKey)) {
          isnew = false;
          fq = fq + " OR " + facetKey + ":" + conditionValue;
        }
        fqs.add(fq);
      }
    }
    if (isnew) {
      if (condition.getTaged()) {
        fqs.add("{!tag=" + facetKey + "}" + facetKey + ":" + conditionValue);
      } else {
        fqs.add(facetKey + ":" + conditionValue);
      }
    }
    query.setFilterQueries(fqs.toArray(new String[fqs.size()]));
  }
}