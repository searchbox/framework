package com.searchbox.core.search.result;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;

import com.searchbox.core.PostSearchAdapter;
import com.searchbox.core.PreSearchAdapter;
import com.searchbox.core.SearchAdapter;

@SearchAdapter
public class TemplatedHitListAdapter  {

	@PreSearchAdapter
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
	}

	@PostSearchAdapter
	public void generateHitElementsForTemplate(TemplatedHitList element, QueryResponse response) {
		
		Iterator<SolrDocument> documents = response.getResults().iterator();
		while(documents.hasNext()){
			SolrDocument document = documents.next();
			Hit hit = element.newHit((Float) document.get("score"));
			for(String field:document.getFieldNames()){
				hit.addFieldValue(field, document.get(field));
			}
		}
	}

}