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

public abstract class SearchElementWithConditionalValues<K extends ConditionalValueElement<T>, T extends AbstractSearchCondition> 
	extends SearchElement implements SearchConditionToElementMerger {
	
	@SearchAttribute
	protected Order order = Order.BY_VALUE;
	
	@SearchAttribute
	protected Sort sort = Sort.DESC;
	
	SortedSet<K> values;
	
	public abstract void mergeSearchCondition(AbstractSearchCondition condition);

	public SearchElementWithConditionalValues() {
		super(null,SearchElement.Type.UNKNOWN);
		values = new TreeSet<K>();
	}
	
	public SearchElementWithConditionalValues(String label, SearchElement.Type type) {
		super(label,type);
		values = new TreeSet<K>();
	}

	public SearchElementWithConditionalValues<K,T> addValueElement(K valueElement){
		this.values.add(valueElement);
		return this;
	}
	
	public SortedSet<K> getValues(){
		return this.values;
	}
	
	public void setValues(SortedSet<K> values){
		this.values = values;
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
