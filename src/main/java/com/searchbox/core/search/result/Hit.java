package com.searchbox.core.search.result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.searchbox.core.search.ValueElement;

public class Hit extends ValueElement  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6419221783189375788L;
	
	public Map<String, Object> fieldValues;
	
	private Float score;
	
	private String IDFieldName;
	
	private String TitleFieldName;
	
	private String URLFieldName;
	
	public Hit(Float score){
		super("");
		this.score = score;
		this.fieldValues = new HashMap<String, Object>();
	}
	
	public String getIDFieldName() {
		return IDFieldName;
	}

	public void setIDFieldName(String iDFieldName) {
		IDFieldName = iDFieldName;
	}

	public String getTitleFieldName() {
		return TitleFieldName;
	}

	public void setTitleFieldName(String titleFieldName) {
		TitleFieldName = titleFieldName;
	}

	public String getURLFieldName() {
		return URLFieldName;
	}

	public void setURLFieldName(String uRLFieldName) {
		URLFieldName = uRLFieldName;
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
		Object id = this.fieldValues.get(this.IDFieldName);
		if(List.class.isAssignableFrom(id.getClass())){
			return ((List<String>)id).get(0);
		} else {
			return (String)id;
		}
	}
	
	@SuppressWarnings("unchecked")
	public String getTitle(){
		Object title = this.fieldValues.get(this.TitleFieldName);
		if(List.class.isAssignableFrom(title.getClass())){
			return ((List<String>)title).get(0);
		} else {
			return (String)title;
		}
	}
	
	@SuppressWarnings("unchecked")
	public String getUrl(){
		Object url = this.fieldValues.get(this.URLFieldName);
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