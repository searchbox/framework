package com.searchbox.core.search.query;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.core.SearchAdapter;
import com.searchbox.core.SearchAdapter.Time;
import com.searchbox.core.SearchAdapterMethod;
import com.searchbox.core.dm.Collection;
import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.core.dm.FieldAttribute.USE;
import com.searchbox.core.engine.SearchEngine;
import com.searchbox.core.query.Query;
import com.searchbox.core.response.Response;

@SearchAdapter
public class EdismaxQuerySolrAdaptor {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(EdismaxQuerySolrAdaptor.class);

  @SearchAdapterMethod(execute = Time.PRE)
  public void setDefaultQuery(EdismaxQuery searchElement, Query query) {
    query.operator(searchElement.getOperator());
  }

  @SearchAdapterMethod(execute = Time.PRE)
  public void setQueryFields(SearchEngine searchEngine,
      EdismaxQuery SearchElement, Query query, FieldAttribute fieldAttribute) {
    
    LOGGER.debug("Checking Field Attr for EdismaxQuery -- Field: {} ",
        fieldAttribute.getField().getKey());

    if (fieldAttribute.getSearchable()) {
      LOGGER.debug("Found searchable field: {}", fieldAttribute.getField()
          .getKey());
      Float boost = (fieldAttribute.getBoost() != null) ? fieldAttribute
          .getBoost() : 1.0f;
      String key = searchEngine.getKeyForField(fieldAttribute, USE.SEARCH);
      query.addSearchField(key, boost);
    }
  }

  @SearchAdapterMethod(execute = Time.POST)
  public void udpateElementQuery(EdismaxQuery searchElement, Query query,
      Response response) {
    searchElement.setQuery(query.getText());
//    if (!searchElement.shouldRetry()
//        && response.getSpellCheckResponse() != null) {
//      LOGGER.debug("Collation query: "
//          + response.getSpellCheckResponse().getCollatedResult());
//      searchElement.setCollationQuery(response.getSpellCheckResponse()
//          .getCollatedResult());
//      searchElement.setHitCount(response.getResults().getNumFound());
//    }
  }

  @SearchAdapterMethod(execute = Time.PRE)
  public void getQueryCondition(EdismaxQuery searchElement,
      EdismaxQuery.Condition condition, Query query) {
    if (searchElement.shouldRetry()
        && searchElement.getCollationQuery() != null
        && !searchElement.getCollationQuery().isEmpty()) {
      // query.setQuery(ClientUtils.escapeQueryChars(searchElement.getCollationQuery()));
      query.setText(searchElement.getCollationQuery());
    } else {
      query.setText(condition.getQuery());
    }
  }

  @SearchAdapterMethod(execute = Time.ASYNCH)
  public void getSugestions(SearchEngine engine, Collection collection,
      EdismaxQuery.Condition condition, Map<String, Object> result) {

//    SolrQuery query = engine.newQuery(collection);
//    query.setRequestHandler("/suggest");
//    query.setQuery(condition.getQuery());
//    SolrResponse response = engine.execute(collection, query);
//
//    List<String> suggestionTerms = new ArrayList<String>();
//    /**
//     * {"status":0,"QTime":1,"suggestions":["aa","aa a lambda and a kappa",
//     * "aa trna and its 3", "aaap therapy",
//     * "aachen are presented under the confin of the above definition" ,"aar"]}
//     */
//    Map<String, NamedList<Object>> data = (Map<String, NamedList<Object>>) response
//        .getResponse().findRecursive("suggest");
//    for (String suggester : data.keySet()) {
//      NamedList<Object> suggestions = (NamedList<Object>) data.get(suggester)
//          .get(condition.getQuery());
//      List<Object> terms = (List<Object>) suggestions.get("suggestions");
//      for (Object term : terms) {
//        NamedList<Object> actualterm = (NamedList<Object>) term;
//        suggestionTerms.add((String) actualterm.get("term"));
//
//      }
//    }
    result.put("status", 0);
//    result.put("QTime", response.getElapsedTime());
//    result.put("suggestions", suggestionTerms);
  }
}