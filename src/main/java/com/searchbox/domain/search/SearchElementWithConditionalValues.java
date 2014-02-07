package com.searchbox.domain.search;

import java.util.SortedSet;
import java.util.TreeSet;

import com.searchbox.ref.Order;
import com.searchbox.ref.Sort;

public abstract class SearchElementWithConditionalValues<K extends ConditionalValueElement<?,T>, T extends SearchCondition> 
	extends SearchElement implements SearchConditionToElementMerger {
	
	protected Order order;
	protected Sort sort;
	
	SortedSet<K> values;
	
	public abstract void mergeSearchCondition(SearchCondition condition);

	public SearchElementWithConditionalValues() {
		super(null);
		values = new TreeSet<K>();
	}
	
	public SearchElementWithConditionalValues(String label) {
		super(label);
		values = new TreeSet<K>();
	}

	public SearchElementWithConditionalValues(String label, Integer position) {
		super(label, position);
		values = new TreeSet<K>();
	}
	
	public SearchElementWithConditionalValues<K,T> addValueElement(K valueElement){
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
