package com.searchbox.core.search.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.params.DisMaxParams;
import org.apache.solr.common.util.NamedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.core.SearchAdapter;
import com.searchbox.core.SearchAdapter.Time;
import com.searchbox.core.SearchAdapterMethod;
import com.searchbox.core.dm.Collection;
import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.core.dm.FieldAttribute.USE;
import com.searchbox.engine.solr.SolrSearchEngine;

@SearchAdapter
public class EdismaxQuerySolrAdaptor {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(EdismaxQuerySolrAdaptor.class);

  @SearchAdapterMethod(execute = Time.PRE)
  public void setDefaultQuery(EdismaxQuery searchElement, SolrQuery query) {
    query.setParam("defType", "edismax");
    query.set(DisMaxParams.ALTQ, "*:*");
    query.setParam("q.op", searchElement.getOperator().toString());
  }

  @SearchAdapterMethod(execute = Time.PRE)
  public void setQueryFields(SolrSearchEngine searchEngine,
      EdismaxQuery SearchElement, SolrQuery query, FieldAttribute fieldAttribute) {
    LOGGER.debug("Checking Field Attr for EdismaxQuery -- Field: {} ",
        fieldAttribute.getField().getKey());

    if (fieldAttribute.getSearchable()) {
      LOGGER.debug("Found searchable field: {}", fieldAttribute.getField()
          .getKey());
      Float boost = (fieldAttribute.getBoost() != null) ? fieldAttribute
          .getBoost() : 1.0f;
      String key = searchEngine.getKeyForField(fieldAttribute, USE.SEARCH);
      String currentFields = query.get(DisMaxParams.QF);
      query.set(DisMaxParams.QF,
          ((currentFields != null && !currentFields.isEmpty()) ? currentFields
              + " " : "")
              + ((key == null) ? fieldAttribute.getField().getKey() : key)
              + "^" + boost);
    }
  }

  @SearchAdapterMethod(execute = Time.POST)
  public void udpateElementQuery(EdismaxQuery searchElement, SolrQuery query,
      QueryResponse response) {
    searchElement.setQuery(query.getQuery());
    if (!searchElement.shouldRetry()
        && response.getSpellCheckResponse() != null) {
      LOGGER.debug("Collation query: "
          + response.getSpellCheckResponse().getCollatedResult());
      searchElement.setCollationQuery(response.getSpellCheckResponse()
          .getCollatedResult());
      searchElement.setHitCount(response.getResults().getNumFound());
    }
  }

  @SearchAdapterMethod(execute = Time.PRE)
  public void getQueryCondition(EdismaxQuery searchElement,
      EdismaxQuery.Condition condition, SolrQuery query) {
    if (searchElement.shouldRetry()
        && searchElement.getCollationQuery() != null
        && !searchElement.getCollationQuery().isEmpty()) {
      // query.setQuery(ClientUtils.escapeQueryChars(searchElement.getCollationQuery()));
      query.setQuery(searchElement.getCollationQuery());
    } else {
      query.setQuery(ClientUtils.escapeQueryChars(condition.getQuery()));
    }
  }

  @SearchAdapterMethod(execute = Time.ASYNCH)
  public void getSugestions(SolrSearchEngine engine, Collection collection,
      EdismaxQuery.Condition condition, Map<String, Object> result) {

    SolrQuery query = engine.newQuery(collection);
    query.setRequestHandler("/suggest");
    query.setQuery(condition.getQuery());
    SolrResponse response = engine.execute(query);

    List<String> suggestionTerms = new ArrayList<String>();
    /**
     * {"status":0,"QTime":1,"suggestions":["aa","aa a lambda and a kappa",
     * "aa trna and its 3", "aaap therapy",
     * "aachen are presented under the confin of the above definition" ,"aar"]}
     */
    Map<String, NamedList<Object>> data = (Map<String, NamedList<Object>>) response
        .getResponse().findRecursive("suggest");
    for (String suggester : data.keySet()) {
      NamedList<Object> suggestions = (NamedList<Object>) data.get(suggester)
          .get(condition.getQuery());
      List<Object> terms = (List<Object>) suggestions.get("suggestions");
      for (Object term : terms) {
        NamedList<Object> actualterm = (NamedList<Object>) term;
        suggestionTerms.add((String) actualterm.get("term"));

      }
    }
    result.put("status", 0);
    result.put("QTime", response.getElapsedTime());
    result.put("suggestions", suggestionTerms);
  }
}