package com.searchbox.core.search;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SearchResult {

	public SearchResult(){
		
	}
	
	List<String> fields = new ArrayList<String>();
	SortedSet<SearchElement> elements = new TreeSet<SearchElement>();

	public void addElement(SearchElement element) {
		this.elements.add(element);
	}
	
	public SortedSet<SearchElement> getElements(SearchElementType type){
		TreeSet<SearchElement> typedElements = new TreeSet<SearchElement>();
		for(SearchElement element:this.elements){
			if(element.type.equals(type)){
				typedElements.add(element);
			}
		}
		return typedElements;
	}

	public SortedSet<SearchElement> getElements() {
		return this.elements;
	}
	
	@Override
	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
