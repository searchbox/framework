package com.searchbox.core.search;

import java.net.URL;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import com.searchbox.anno.SearchAttribute;
import com.searchbox.anno.SearchComponent;

@Configurable(autowire=Autowire.BY_TYPE)
public abstract class SearchElement implements Comparable<SearchElement>{

	private Long definitionId;
	
	@SearchAttribute
	private String label;
	
	private Integer position;
	
	protected SearchElementType type = SearchElementType.FILTER;
	
	public SearchElement(){}
	
	protected SearchElement(String label){
		this.label = label;
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

	public SearchElementType getType() {
		return type;
	}

	public void setType(SearchElementType type) {
		this.type = type;
	}

	public Long getDefinitionId() {
		return definitionId;
	}

	public void setDefinitionId(Long definitionId) {
		this.definitionId = definitionId;
	}

	@Override
	public int compareTo(SearchElement searchElement) {
		return this.getPosition().compareTo(searchElement.getPosition());
		
	}
	
	//TODO this should not be here...
	public String getParamPrefix(){
		SearchComponent a = this.getClass().getAnnotation(SearchComponent.class);
		if(a==null){
			return "";
		} else {
			return a.prefix();
		}
	}
	
	public URL getView(){
		//TODO partial should be next to the .class file... 
		System.out.println("XOXOXOXOXOX: " + this.getClass());
		System.out.println("XOXOXOXOXOX: " + this.getClass().getResource("view.jspx"));
		return this.getClass().getResource("view.jspx");
	}
	
	@Override
	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
