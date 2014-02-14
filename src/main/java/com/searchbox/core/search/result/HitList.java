package com.searchbox.core.search.result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.searchbox.anno.SearchComponent;
import com.searchbox.core.search.SearchElementType;
import com.searchbox.core.search.SearchElementWithValues;
import com.searchbox.core.search.ValueElement;
import com.searchbox.core.search.result.HitList.Hit;


@SearchComponent
public class HitList extends SearchElementWithValues<HitList.Hit> {
	
	private List<String> fields;
	private String titleField;
	private String urlField;
	private String idField;
	
	public HitList(){
		super("Result Set");
		this.type = SearchElementType.VIEW;
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
	
	public class Hit extends ValueElement<Map<String, Object>> implements Comparable<Hit> {

		private Float score;
		
		public Hit(Float score){
			super("");
			this.score = score;
			this.value = new HashMap<String, Object>();
		}
		
		public Float getScore(){
			return this.score;
		}

		public void setScore(Float score) {
			this.score = score;
		}
		
		public void addFieldValue(String name, Object value){
			this.value.put(name, value);
		}
		
		public String getTitle(){
			Object title = this.value.get(titleField);
			if(List.class.isAssignableFrom(title.getClass())){
				return ((List<String>)title).get(0);
			} else {
				return (String)title;
			}
		}
		
		public String getUrl(){
			Object url = this.value.get(urlField);
			if(List.class.isAssignableFrom(url.getClass())){
				return ((List<String>)url).get(0);
			} else {
				return (String)url;
			}
		}
		
		@Override
		public int compareTo(Hit hit) {
			return score.compareTo(hit.getScore()+0.001f) * -1;
		}
	}
}
