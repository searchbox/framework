package com.searchbox.domain.search;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
public class SearchResult {

	public SearchResult(){
		
	}
	
	List<String> fields = new ArrayList<String>();
	SortedSet<SearchElement> elements = new TreeSet<SearchElement>();
//	SortedSet<Facet> facets = new TreeSet<Facet>();


	public void addElement(SearchElement element) {
		element.setPosition(this.elements.size() + 1);
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

}
