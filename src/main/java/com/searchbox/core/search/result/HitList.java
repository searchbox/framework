package com.searchbox.core.search.result;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;

import com.searchbox.anno.PostSearchAdapter;
import com.searchbox.anno.PreSearchAdapter;
import com.searchbox.anno.SearchAdapter;
import com.searchbox.anno.SearchAttribute;
import com.searchbox.anno.SearchComponent;
import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.SearchElementWithValues;


@SearchComponent
public class HitList extends SearchElementWithValues<Hit> {
	
	@SearchAttribute
	protected List<String> fields;
	
	@SearchAttribute String titleField;
	
	@SearchAttribute String urlField;
	
	@SearchAttribute String idField;
	
	public HitList(){
		super("Result Set",SearchElement.Type.VIEW);
		this.fields = new ArrayList<String>();
	}
	
	public String getTitleField() {
		return titleField;
	}

	public void setTitleField(String titleField) {
		this.titleField = titleField;
	}

	public String getUrlField() {
		return urlField;
	}

	public void setUrlField(String urlField) {
		this.urlField = urlField;
	}

	public String getIdField() {
		return idField;
	}

	public void setIdField(String idField) {
		this.idField = idField;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	public List<String> getFields() {
		return this.fields;
	}

	public void addHit(Hit hit) {
		this.values.add(hit);
	}

	public Hit newHit(Float score) {
		Hit hit = new Hit(score);
		hit.setIDFieldName(this.idField);
		hit.setTitleFieldName(this.titleField);
		hit.setURLFieldName(this.urlField);
		this.addHit(hit);
		return hit;
	}
}

@SearchAdapter
class HitListAdapter {

	@PreSearchAdapter
	public SolrQuery setRequieredFields(HitList searchElement,
			SolrQuery query) {
		for(String field:searchElement.getFields()){
			query.addField(field);
		}
		if(!searchElement.getFields().contains(searchElement.getTitleField())){
			query.addField(searchElement.getTitleField());
		}
		if(!searchElement.getFields().contains(searchElement.getUrlField())){
			query.addField(searchElement.getUrlField());
		}
		if(!searchElement.getFields().contains(searchElement.getIdField())){
			query.addField(searchElement.getIdField());
		}
		query.addField("score");
		return query;
	}

	@PostSearchAdapter
	public HitList generateHits(HitList element, QueryResponse response) {
		
		Iterator<SolrDocument> documents = response.getResults().iterator();
		while(documents.hasNext()){
			SolrDocument document = documents.next();
			Hit hit = element.newHit((Float) document.get("score"));
			for(String field:document.getFieldNames()){
				hit.addFieldValue(field, document.get(field));
			}
		}
		return element;
	}
}
