package com.searchbox.engine.solr;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest.ACTION;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.util.ContentStreamBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.core.dm.Collection;
import com.searchbox.core.dm.Field;
import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.core.dm.FieldAttribute.USE;
import com.searchbox.core.engine.AbstractSearchEngine;
import com.searchbox.core.engine.ManagedSearchEngine;
import com.searchbox.engine.FieldMappingSearchEngine;

public abstract class SolrSearchEngine extends
    AbstractSearchEngine<SolrQuery, QueryResponse> implements
    ManagedSearchEngine, FieldMappingSearchEngine {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(SolrSearchEngine.class);

  private static final String SEARCHABLE_TEXT_NO_LANG_FIELD = "_txt";
  private static final String NON_SORTABLE_FIELD = "s";

  private static final String DATE_FIELD = "_tdt";
  private static final String BOOLEAN_FIELD = "_b";
  private static final String INTEGER_FIELD = "_ti";
  private static final String FLOAT_FIELD = "_tf";
  private static final String DOUBLE_FIELD = "_td";
  private static final String LONG_FIELD = "_tl";
  private static final String TEXT_FIELD = "_s";

  private static final String SPELLCHECK_FIELD = "text";
  private static final String SUGGESTION_FIELD = "suggest";

  public SolrSearchEngine() {
    super(SolrQuery.class, QueryResponse.class);
  }

  public SolrSearchEngine(String name) {
    super(name, SolrQuery.class, QueryResponse.class);
  }

  protected abstract SolrServer getSolrServer();

  protected abstract boolean updateDataModel(Collection collection, 
      Map<Field, Set<String>> copyFields);

  @Override
  public SolrQuery newQuery(Collection collection) {
    try {
      SolrQuery query = this.getQueryClass().newInstance();
      query.setParam("collection", collection.getName());
      return query;
    } catch (Exception e) {
      LOGGER.error("Could not create new Query Object for searchEngine", e);
      throw new RuntimeException(
          "Could not create new Query Object for searchEngine", e);
    }
  }

  @Override
  public QueryResponse execute(SolrQuery query) {
    try {
      return this.getSolrServer().query(query);
    } catch (SolrServerException e) {
      LOGGER.warn("Could not execute query {}", query);
      throw new RuntimeException("Could nexecute Query on  engine", e);
    }
  }

  @Override
  public void reloadPlugins(Collection collection) {
    try {
      LOGGER.info("Updating Solr Suggester");
      SolrQuery query = this.newQuery(collection);
      query.setRequestHandler("/suggest");
      query.setQuery("a");
      query.setParam("suggest.build", true);
      LOGGER.info("Query is: " + query);
      QueryResponse response = this.execute(query);

      LOGGER.info("Updating Solr Spellchecker");
      query = this.newQuery(collection);
      query.setRequestHandler("/spell");
      query.setParam("spellcheck.build", true);
      response = this.execute(query);
    } catch (Exception e) {
      LOGGER.warn("Error while reloading plugins (Nothing commited yet?)");
    }
  }

  @Override
  public boolean indexFile(Collection collection, File file) {
    LOGGER.info("Indexing for collection: " + collection.getName());
    LOGGER.info("Indexing file: " + file.getAbsolutePath());
    ContentStreamBase contentstream = new ContentStreamBase.FileStream(file);
    contentstream.setContentType("text/xml");
    ContentStreamUpdateRequest request = new ContentStreamUpdateRequest(
        "/update");
    request.setCommitWithin(1000);
    request.setParam("collection", collection.getName());
    request.addContentStream(contentstream);
    UpdateResponse response;
    try {
      response = request.process(this.getSolrServer());
      LOGGER.info("Solr Response: " + response);
      // TODO not there...
      UpdateRequest commit = new UpdateRequest();
      commit.setParam("collection", collection.getName());
      commit.setAction(ACTION.COMMIT, false, false);
      commit.process(this.getSolrServer());
      return true;
    } catch (SolrServerException | IOException e) {
      LOGGER.error("Could not index file: " + file, e);
    }
    return false;
  }

  @Override
  public boolean indexMap(Collection collection, Map<String, Object> fields) {

    SolrInputDocument document = new SolrInputDocument();
    //TODO should never need this.. Make sure we use the ID field...
    if (!fields.containsKey("id")) {
      document.addField("id", fields.get(collection.getIdFieldName()));
    }
    for (Entry<String, Object> entry : fields.entrySet()) {
      if(entry.getValue() == null){
        continue;
      } else if (Collection.class.isAssignableFrom(entry.getValue().getClass())) {
        for (Object value : ((java.util.Collection<?>) entry.getValue())) {
          document.addField(entry.getKey(), value);
        }
      } else {
        document.addField(entry.getKey(), entry.getValue());
      }
    }

    UpdateRequest update = new UpdateRequest();
    update.setCommitWithin(10000);
    update.setParam("collection", collection.getName());
    update.add(document);
    try {
      LOGGER.debug("Indexing document {}", document);
      UpdateResponse response = update.process(this.getSolrServer());
      LOGGER.debug("Updated Solr with status: " + response.getStatus());
      return true;
    } catch (Exception e) {
      LOGGER.error("Could not index FieldMap", e);
      return false;
    }
  }
  
  @Override
  public boolean updateDataModel(Collection collection, List<FieldAttribute> fieldAttributes) {
    /** Get the translation for the field's key */
    Map<Field, Set<String>> copyFields = new HashMap<Field, Set<String>>();
    for (FieldAttribute fieldAttribute : fieldAttributes) {
      copyFields.put(fieldAttribute.getField(),
          this.getAllKeysForField(fieldAttribute));
    }
    LOGGER.info("Updating all fields");
    this.updateDataModel(collection, copyFields);
    LOGGER.info("Reloading engine");
    this.reloadEngine(collection);
    return true;

  }

  @Override
  public Set<String> getAllKeysForField(FieldAttribute fieldAttribute) {
    Set<String> fields = new TreeSet<String>();
    fields.addAll(this.mapFieldUsage(fieldAttribute).values());
    return fields;
  }

  @Override
  public String getKeyForField(FieldAttribute fieldAttribute) {
    return this.getKeyForField(fieldAttribute, USE.DEFAULT);
  }

  @Override
  public String getKeyForField(FieldAttribute fieldAttribute, USE operation) {
    return this.mapFieldUsage(fieldAttribute).get(operation);
  }

  private Map<USE, String> mapFieldUsage(FieldAttribute fieldAttribute) {

    Field field = fieldAttribute.getField();

    Map<USE, String> usages = new HashMap<USE, String>();

    String append = "";
    String prepend = "";

    if (!fieldAttribute.getSortable()) {
      append = NON_SORTABLE_FIELD;
    }

    if (Boolean.class.isAssignableFrom(fieldAttribute.getField().getClazz())) {
      prepend += BOOLEAN_FIELD;
      usages.put(USE.DEFAULT, field.getKey() + BOOLEAN_FIELD + append);

    } else if (Date.class
        .isAssignableFrom(fieldAttribute.getField().getClazz())) {
      prepend += DATE_FIELD;
      usages.put(USE.DEFAULT, field.getKey() + DATE_FIELD + append);

    } else if (Integer.class.isAssignableFrom(fieldAttribute.getField()
        .getClazz())) {
      prepend += INTEGER_FIELD;
      usages.put(USE.DEFAULT, field.getKey() + INTEGER_FIELD + append);

    } else if (Float.class.isAssignableFrom(fieldAttribute.getField()
        .getClazz())) {
      prepend += FLOAT_FIELD;
      usages.put(USE.DEFAULT, field.getKey() + FLOAT_FIELD + append);

    } else if (Double.class.isAssignableFrom(fieldAttribute.getField()
        .getClazz())) {
      prepend += DOUBLE_FIELD;
      usages.put(USE.DEFAULT, field.getKey() + DOUBLE_FIELD + append);

    } else if (Long.class
        .isAssignableFrom(fieldAttribute.getField().getClazz())) {
      prepend += LONG_FIELD;
      usages.put(USE.DEFAULT, field.getKey() + LONG_FIELD + append);

    } else if (String.class.isAssignableFrom(fieldAttribute.getField()
        .getClazz())) {
      prepend += TEXT_FIELD;
      usages.put(USE.DEFAULT, field.getKey() + TEXT_FIELD + append);
    }

    if (fieldAttribute.getSortable()) {
      usages.put(USE.SORT, field.getKey() + prepend + append);
    }

    if (fieldAttribute.getSearchable()) {
      if (String.class.isAssignableFrom(fieldAttribute.getField().getClazz())) {
        if (fieldAttribute.getLang().isEmpty()) {
          usages
              .put(USE.SEARCH, field.getKey() + SEARCHABLE_TEXT_NO_LANG_FIELD);
        } else {
          for (String lang : fieldAttribute.getLang()) {
            usages.put(USE.SEARCH, field.getKey() + "_" + lang);
          }
        }
      } else {
        usages.put(USE.SEARCH, field.getKey() + prepend + append);
      }
    }

    if (fieldAttribute.getHighlight()) {
      // We shoudl use the USE.SEARCH attribute for now...
      // usages.put(USE.TF, field.getKey() + HIGHLIGHT_FIELD);
    }

    if (fieldAttribute.getSpelling()) {
      usages.put(USE.SPELL, SPELLCHECK_FIELD);
    }

    if (fieldAttribute.getSuggestion()) {
      usages.put(USE.SUGGEST, SUGGESTION_FIELD);
    }

    return usages;
  }

}
