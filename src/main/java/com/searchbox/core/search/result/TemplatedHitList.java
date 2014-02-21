package com.searchbox.core.search.result;

import java.util.Iterator;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;

import org.springframework.beans.factory.annotation.Autowired;

import com.searchbox.anno.SearchAdaptor;
import com.searchbox.anno.SearchAttribute;
import com.searchbox.anno.SearchComponent;
import com.searchbox.core.adaptor.SolrElementAdapter;
import com.searchbox.core.dm.Preset;
import com.searchbox.core.search.CachedContent;
import com.searchbox.core.search.result.HitList.Hit;
import com.searchbox.ref.StringUtils;
import com.searchbox.service.DirectoryService;

@SearchComponent
public class TemplatedHitList extends HitList implements CachedContent {
	
	@Autowired
	DirectoryService directoryService;
		
	protected List<String> fields;
	
	@SearchAttribute
	private String template;
	
	private String templateFile;
	
	public TemplatedHitList(){
		super();
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

@SearchAdaptor
class TemplatedHitListAdapter implements SolrElementAdapter<TemplatedHitList> {

	@Override
	public SolrQuery doAdapt(Preset preset, TemplatedHitList searchElement,
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

	@Override
	public TemplatedHitList doAdapt(Preset preset, TemplatedHitList element,
			SolrQuery query, QueryResponse response) {
		
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