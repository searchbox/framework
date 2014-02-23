package com.searchbox.core.search.result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;

import com.searchbox.anno.SearchAdapter;
import com.searchbox.anno.SearchAdapterMethod;
import com.searchbox.anno.SearchAdapterMethod.Target;
import com.searchbox.anno.SearchAttribute;
import com.searchbox.anno.SearchComponent;
import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.SearchElementWithValues;
import com.searchbox.core.search.ValueElement;


@SearchComponent
public class HitList extends SearchElementWithValues<HitList.Hit> {
	
	@SearchAttribute
	protected List<String> fields;
	
	@SearchAttribute
	private String titleField;
	
	@SearchAttribute
	private String urlField;
	
	@SearchAttribute
	private String idField;
	
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
		this.addHit(hit);
		return hit;
	}
	
	public class Hit extends ValueElement  {

		/**
		 * 
		 */
		private static final long serialVersionUID = -6419221783189375788L;
		
		public Map<String, Object> fieldValues;
		private Float score;
		
		public Hit(Float score){
			super("");
			this.score = score;
			this.fieldValues = new HashMap<String, Object>();
		}
		
		public Float getScore(){
			return this.score;
		}

		public void setScore(Float score) {
			this.score = score;
		}
		
		public void addFieldValue(String name, Object value){
			this.fieldValues.put(name, value);
		}
		
		@SuppressWarnings("unchecked")
		public String getId(){
			Object id = this.fieldValues.get(idField);
			if(List.class.isAssignableFrom(id.getClass())){
				return ((List<String>)id).get(0);
			} else {
				return (String)id;
			}
		}
		
		@SuppressWarnings("unchecked")
		public String getTitle(){
			Object title = this.fieldValues.get(titleField);
			if(List.class.isAssignableFrom(title.getClass())){
				return ((List<String>)title).get(0);
			} else {
				return (String)title;
			}
		}
		
		@SuppressWarnings("unchecked")
		public String getUrl(){
			Object url = this.fieldValues.get(urlField);
			if(List.class.isAssignableFrom(url.getClass())){
				return ((List<String>)url).get(0);
			} else {
				return (String)url;
			}
		}
		
		public Map<String, Object> getFieldValues(){
			return this.fieldValues;
		}

		@Override
		public int compareTo(ValueElement other) {
			return score.compareTo(((Hit)other).getScore()+0.001f) * -1;
		}
		
	}
}

@SearchAdapter(target=HitList.class)
class HitListAdapter {

	@SearchAdapterMethod(target=Target.PRE)
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

	@SearchAdapterMethod(target=Target.POST)
	public HitList doAdapt(HitList element, QueryResponse response) {
		
		Iterator<SolrDocument> documents = response.getResults().iterator();
		while(documents.hasNext()){
			SolrDocument document = documents.next();
			HitList.Hit hit = element.newHit((Float) document.get("score"));
			for(String field:document.getFieldNames()){
				hit.addFieldValue(field, document.get(field));
			}
		}
		return element;
	}
}
