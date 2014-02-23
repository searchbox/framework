package com.searchbox.core.search;

import java.net.URL;

import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.searchbox.anno.SearchAttribute;
import com.searchbox.anno.SearchComponent;

@MappedSuperclass
public abstract class SearchElement implements Comparable<SearchElement>{
	
	public enum Type {
		QUERY, FACET, FILTER, VIEW, ANALYTIC, SORT, STAT, DEBUG, UNKNOWN
	}
	
	@SearchAttribute
	private String label;
	
	private Integer position;
	
	protected Type type = Type.FILTER;
	
	public SearchElement(){}
	
	protected SearchElement(String label, SearchElement.Type type){
		this.label = label;
		this.type = type;
	}
	
	/* (non-Javadoc)
	 * @see com.searchbox.core.search.SearchElement#getLabel()
	 */
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	/* (non-Javadoc)
	 * @see com.searchbox.core.search.SearchElement#getPosition()
	 */
	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	/* (non-Javadoc)
	 * @see com.searchbox.core.search.SearchElement#getType()
	 */
	public SearchElement.Type getElementType() {
		return type;
	}

	public void setType(SearchElement.Type type) {
		this.type = type;
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
