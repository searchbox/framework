package com.searchbox.core.search.result;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;

import com.searchbox.anno.SearchAdapter;
import com.searchbox.anno.SearchAdapterMethod;
import com.searchbox.anno.SearchAdapterMethod.Timing;
import com.searchbox.anno.SearchAttribute;
import com.searchbox.anno.SearchComponent;
import com.searchbox.core.search.CachedContent;
import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.SearchElementWithValues;
import com.searchbox.ref.StringUtils;

@SearchComponent
public class TemplatedHitList extends SearchElementWithValues<Hit> implements CachedContent {
		
	protected List<String> fields;
	
	@SearchAttribute
	private String template;
	
	private String templateFile;
	
	@SearchAttribute String titleField;
	
	@SearchAttribute String urlField;
	
	@SearchAttribute String idField;
	
	public TemplatedHitList(){
		super("Result Set with Template",SearchElement.Type.VIEW);
		this.fields = new ArrayList<String>();
	}
	
	public String getTemplate(){
		return this.template;
	}
	
	public void setTemplate(String template){
		this.template = template;
		this.getFields().addAll(StringUtils.extractHitFields(template));
	}
	
	public String getTemplateFile(){
		return this.templateFile;
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

	@Override
	public int getContentHash() {
		return this.template.hashCode();
	}

	@Override
	public String getContent() {
		return "<jsp:root xmlns:jsp=\"http://java.sun.com/JSP/Page\" "+
				"version=\"2.0\">"+
				this.template+"</jsp:root>";
	}

	@Override
	public void setPath(String path) {
		this.templateFile = path;
	}
}

@SearchAdapter(target=TemplatedHitList.class)
class TemplatedHitListAdapter  {

	@SearchAdapterMethod(timing=Timing.BEFORE)
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

	@SearchAdapterMethod(timing=Timing.AFTER)
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