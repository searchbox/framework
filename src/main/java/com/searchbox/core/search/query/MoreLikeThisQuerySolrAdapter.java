package com.searchbox.core.search.query;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.params.MoreLikeThisParams;
import org.apache.solr.common.util.ContentStream;
import org.apache.solr.common.util.ContentStreamBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.core.SearchAdapter;
import com.searchbox.core.SearchAdapter.Time;
import com.searchbox.core.SearchAdapterMethod;
import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.core.dm.FieldAttribute.USE;
import com.searchbox.engine.solr.SolrSearchEngine;

@SearchAdapter
public class MoreLikeThisQuerySolrAdapter {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(MoreLikeThisQuerySolrAdapter.class);

  @SearchAdapterMethod(execute = Time.PRE)
  public void setDefaultQuery(MoreLikeThisQuery searchElement, SolrQuery query) {


//    ContentStreamUpdateRequest up = new ContentStreamUpdateRequest("/mlt");
//    ContentStream cs = new ContentStreamBase.StringStream(mltv[0]);

    query.setRequestHandler("/mlt");
    query.setRows(3);
    query.setHighlight(false);
  }

  @SearchAdapterMethod(execute = Time.PRE)
  public void getQueryCondition(MoreLikeThisQuery searchElement,
      QueryCondition condition, SolrQuery query) {
    String qq = condition.getQuery();
    qq = qq.substring(0, Math.min(2000,qq.length()));
    query.add("stream.body",ClientUtils.escapeQueryChars(qq));
//    query.setQuery(ClientUtils.escapeQueryChars(condition.getQuery()));
  }


  @SearchAdapterMethod(execute = Time.PRE)
  public void setQueryFields(SolrSearchEngine searchEngine,
      MoreLikeThisQuery SearchElement, SolrQuery query, FieldAttribute fieldAttribute) {
    LOGGER.debug("Checking Field Attr for EdismaxQuery -- Field: {} ",
        fieldAttribute.getField().getKey());

    if (fieldAttribute.getSearchable()) {
      LOGGER.debug("Found searchable field: {}", fieldAttribute.getField()
          .getKey());
      Float boost = (fieldAttribute.getBoost() != null) ? fieldAttribute
          .getBoost() : 1.0f;
      String key = searchEngine.getKeyForField(fieldAttribute, USE.SEARCH);
      String currentQueryFields = query.get(MoreLikeThisParams.QF);
      String currentFields = query.get(MoreLikeThisParams.SIMILARITY_FIELDS);
      String newCurrentQueryFields =  ((currentQueryFields != null && !currentQueryFields.isEmpty()) ? currentQueryFields
          + " " : "")
          + ((key == null) ? fieldAttribute.getField().getKey() : key)
          + "^" + boost;
      String newCurrentFields =  ((currentFields != null && !currentFields.isEmpty()) ? currentFields
          + "," : "")
          + ((key == null) ? fieldAttribute.getField().getKey() : key);
      query.set(MoreLikeThisParams.QF,newCurrentQueryFields);
      query.set(MoreLikeThisParams.SIMILARITY_FIELDS, newCurrentFields);
    }
  }
}