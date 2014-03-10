package com.searchbox.core.search.result;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.core.SearchAdapter;
import com.searchbox.core.SearchAdapter.Time;
import com.searchbox.core.SearchAdapterMethod;
import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.core.dm.FieldAttribute.USE;
import com.searchbox.engine.solr.SolrSearchEngine;

@SearchAdapter
public class TemplatedHitListAdapter  {
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(TemplatedHitListAdapter.class);
	
	@SearchAdapterMethod(execute=Time.PRE)
	public void setHighlightFieldsForTemplate(SolrSearchEngine engine, TemplatedHitList searchElement,
			SolrQuery query, FieldAttribute attribute) {
		
		if(!attribute.getHighlight()){
			return;
		}
		
		String fieldHighlightKey = engine.getKeyForField(attribute, USE.SEARCH);
		
		if(query.getHighlightFields() == null || 
				!Arrays.asList(query.getHighlightFields()).contains(fieldHighlightKey)){
			query.addHighlightField(fieldHighlightKey);			
		}
	}

	@SearchAdapterMethod(execute=Time.PRE)
	public void addHighlightFieldsForTemplate(TemplatedHitList searchElement,
			SolrQuery query, FieldAttribute attribute) {
		
		if(!attribute.getHighlight()){
			return;
		}
		if(query.getFacetFields() == null ||
			!Arrays.asList(query.getFacetFields()).contains(attribute.getField().getKey())){
			query.addField(attribute.getField().getKey());
		}
	}
	
	@SearchAdapterMethod(execute=Time.PRE)
	public void setRequieredFieldsForTemplate(TemplatedHitList searchElement,
			SolrQuery query) {
		Set<String> fields = new HashSet<String>();
		for(String field:searchElement.getFields()){
			fields.add(field);
		}
		if(!searchElement.getFields().contains(searchElement.getTitleField())){
			fields.add(searchElement.getTitleField());
		}
		if(!searchElement.getFields().contains(searchElement.getUrlField())){
			fields.add(searchElement.getUrlField());
		}
		if(!searchElement.getFields().contains(searchElement.getIdField())){
			fields.add(searchElement.getIdField());
		}
		fields.add("score");
		query.setFields(fields.toArray(new String[0]));
		
		//Setting highlights parameters
		query.setHighlight(true);
		query.setHighlightSnippets(3);
	}

	@SearchAdapterMethod(execute=Time.POST)
	public void generateHitElementsForTemplate(TemplatedHitList element, QueryResponse response,
			FieldAttribute attribute) {

		if(!attribute.getField().getKey().equalsIgnoreCase(element.getIdField())){
			return;
		}
		
		Iterator<SolrDocument> documents = response.getResults().iterator();
		while(documents.hasNext()){
			SolrDocument document = documents.next();
			Hit hit = element.newHit((Float) document.get("score"));
			for(String field:document.getFieldNames()){
				hit.addFieldValue(field, document.get(field));
			}
			//Now we push the highlights
			Object id = document.getFirstValue(attribute.getField().getKey());
			Map<String, List<String>> highlights = response.getHighlighting().get(id);
			for(String highlihgtkey:highlights.keySet()){
				for(String fieldkey:document.getFieldNames()){
					if(highlihgtkey.contains(fieldkey)){
						hit.getHighlights().put(fieldkey, highlights.get(highlihgtkey));
						break;
					}
				}
			}
		}
	}
}