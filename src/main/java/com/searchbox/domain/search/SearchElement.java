package com.searchbox.domain.search;

import java.net.URL;

import com.searchbox.ann.search.SearchComponent;

public abstract class SearchElement implements Comparable<SearchElement>{

	String label;
	
	Integer position = 0;
	
	SearchElement(String label){
		this.label = label;
	}
	
	SearchElement(String label, Integer position){
		this.label = label;
		this.position = position;
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	@Override
	public int compareTo(SearchElement searchElement) {
		return this.getPosition().compareTo(searchElement.getPosition());
		
	}
	
	public String getParamPrefix(){
		SearchComponent a = this.getClass().getAnnotation(SearchComponent.class);
		if(a==null){
			return "";
		} else {
			return a.prefix();
		}
	}
	
	public URL getView(){
		//TODO
		System.out.println("XOXOXOXOXOX: " + this.getClass());
		System.out.println("XOXOXOXOXOX: " + this.getClass().getResource("view.jspx"));
		return this.getClass().getResource("view.jspx");
	}
}
