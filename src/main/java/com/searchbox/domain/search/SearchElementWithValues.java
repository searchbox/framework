package com.searchbox.domain.search;

import java.util.SortedSet;
import java.util.TreeSet;

public class SearchElementWithValues<K extends ValueElement<?>> extends SearchElement {
	
	SortedSet<K> values;

	public SearchElementWithValues() {
		super(null);
		values = new TreeSet<K>();
	}
	
	public SearchElementWithValues(String label) {
		super(label);
		values = new TreeSet<K>();
	}

	public SearchElementWithValues(String label, Integer position) {
		super(label, position);
		values = new TreeSet<K>();
	}
	
	public SearchElementWithValues<K> addValueElement(K valueElement){
		this.values.add(valueElement);
		return this;
	}
	
	public SortedSet<K> getValues(){
		return this.values;
	}
}
