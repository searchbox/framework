/*******************************************************************************
 * Copyright Searchbox - http://www.searchbox.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
