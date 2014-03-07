package com.searchbox.engine.solr;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrResponse;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.util.ContentStreamBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.core.dm.Field;
import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.core.dm.FieldAttribute.USE;
import com.searchbox.core.engine.AbstractSearchEngine;
import com.searchbox.core.engine.ManagedSearchEngine;

public abstract class SolrSearchEngine extends
		AbstractSearchEngine<SolrQuery, SolrResponse> implements
		ManagedSearchEngine {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SolrSearchEngine.class);

	private static final String SEARCHABLE_TEXT_NO_LANG_FIELD = "_txt";
	private static final String HIGHLIGHT_FIELD = "_txt";
	private static final String NON_SORTABLE_FIELD = "s";

	private static final String DATE_FIELD = "_tdt";
	private static final String BOOLEAN_FIELD = "_b";
	private static final String INTEGER_FIELD = "_ti";
	private static final String FLOAT_FIELD = "_tf";
	private static final String DOUBLE_FIELD = "_td";
	private static final String LONG_FIELD = "_tl";
	private static final String TEXT_FIELD = "_s";

	private static final String SPELLCHECK_FIELD = "spell";
	private static final String SUGGESTION_FIELD = "suggest";

	public SolrSearchEngine() {
		super(SolrQuery.class, SolrResponse.class);
	}

	public SolrSearchEngine(String name) {
		super(name, SolrQuery.class, SolrResponse.class);
	}

	protected abstract SolrServer getSolrServer();

	protected abstract boolean addCopyFields(Field field, Set<String> copyFields);

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public SolrResponse execute(SolrQuery query) {
		try {
			return this.getSolrServer().query(query);
		} catch (SolrServerException e) {
			throw new RuntimeException("Could nexecute Query on  engine", e);
		}
	}

	@Override
	public boolean indexFile(File file) {
		LOGGER.info("Indexing file: " + file.getAbsolutePath());
		ContentStreamBase contentstream = new ContentStreamBase.FileStream(file);
		contentstream.setContentType("text/xml");
		ContentStreamUpdateRequest request = new ContentStreamUpdateRequest(
				"/update");
		request.addContentStream(contentstream);
		UpdateResponse response;
		try {
			response = request.process(this.getSolrServer());
			LOGGER.debug("Solr Response: " + response);
			response = this.getSolrServer().commit();
			LOGGER.debug("Solr commit: " + response);
			return true;
		} catch (SolrServerException | IOException e) {
			LOGGER.error("Could not index file: " + file, e);
		}
		return false;
	}

	@Override
	public boolean indexMap(Map<String, Object> fields) {
		SolrInputDocument document = new SolrInputDocument();
		for (Entry<String, Object> entry : fields.entrySet()) {
			if (Collection.class.isAssignableFrom(entry.getValue().getClass())) {
				for (Object value : ((Collection<?>) entry.getValue())) {
					document.addField(entry.getKey(), value);
				}
			} else {
				document.addField(entry.getKey(), entry.getValue());
			}
		}
		UpdateRequest update = new UpdateRequest();
		update.add(document);
		try {
			UpdateResponse response = update.process(this.getSolrServer());
			LOGGER.debug("Updated FieldMap with status: "
					+ response.getStatus());
			response = this.getSolrServer().commit();
			LOGGER.debug("Solr commit: " + response);
			return true;
		} catch (Exception e) {
			LOGGER.error("Could not index FieldMap", e);
			return false;
		}
	}

	@Override
	public boolean updateForField(FieldAttribute fieldAttribute) {
		/** Get the translation for the field's key */
		Set<String> fieldNames = this.getAllKeysForField(fieldAttribute);

		return this.addCopyFields(fieldAttribute.getField(), fieldNames);
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

		if (Boolean.class
				.isAssignableFrom(fieldAttribute.getField().getClazz())) {
			prepend += BOOLEAN_FIELD;
			usages.put(USE.DEFAULT, field.getKey() + BOOLEAN_FIELD + append);

		} else if (Date.class.isAssignableFrom(fieldAttribute.getField()
				.getClazz())) {
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

		} else if (Long.class.isAssignableFrom(fieldAttribute.getField()
				.getClazz())) {
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
			if (String.class.isAssignableFrom(fieldAttribute.getField()
					.getClazz())) {
				if (fieldAttribute.getLang().isEmpty()) {
					usages.put(USE.SEARCH, field.getKey()
							+ SEARCHABLE_TEXT_NO_LANG_FIELD);
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
			usages.put(USE.TF, field.getKey() + HIGHLIGHT_FIELD);
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
