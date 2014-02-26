package com.searchbox.core.search;

import java.util.SortedSet;
import java.util.TreeSet;

import com.searchbox.core.SearchAttribute;
import com.searchbox.core.ref.Order;
import com.searchbox.core.ref.Sort;

public class SearchElementWithValues<K extends ValueElement> extends SearchElement {
	
	@SearchAttribute
	protected Order order;
	
	@SearchAttribute
	protected Sort sort;
	
	protected SortedSet<K> values;

	public SearchElementWithValues() {
		super(null, SearchElement.Type.UNKNOWN);
		values = new TreeSet<K>();
	}
	
	public SearchElementWithValues(String label, SearchElement.Type type) {
		super(label, type);
		values = new TreeSet<K>();
	}

	public SearchElementWithValues<K> addValueElement(K valueElement){
		this.values.add(valueElement);
		return this;
	}
	
	public SortedSet<K> getValues(){
		return this.values;
	}
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Sort getSort() {
		return sort;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}
}
