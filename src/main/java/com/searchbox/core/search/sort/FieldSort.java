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
package com.searchbox.core.search.sort;

import java.io.Serializable;
import java.util.SortedSet;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;

import com.searchbox.core.PreSearchAdapter;
import com.searchbox.core.SearchAdapter;
import com.searchbox.core.SearchAttribute;
import com.searchbox.core.SearchComponent;
import com.searchbox.core.SearchCondition;
import com.searchbox.core.SearchConverter;
import com.searchbox.core.ref.Sort;
import com.searchbox.core.search.AbstractSearchCondition;
import com.searchbox.core.search.ConditionalValueElement;
import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.SearchElementWithConditionalValues;
import com.searchbox.core.search.ValueElement;

@SearchComponent
public class FieldSort extends SearchElementWithConditionalValues<FieldSort.Value, FieldSort.Condition> {
	
	@SearchAttribute
	protected SortedSet<FieldSort.Value> values;
	
	public FieldSort() {
		super("Sort Component",SearchElement.Type.SORT);
	}
	
	public static class Value extends ConditionalValueElement<FieldSort.Condition>
		implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -230559535275452676L;
		
		public String fieldName;
		public Sort sort;
		public Boolean selected;
		
		public Value() {
			super("");
		}
		
		public Value(String label, String field, Sort sort) {
			super(label);
			this.fieldName = field;
			this.sort = sort;
			this.selected = false;
		}
		
		public String getFieldName() {
			return fieldName;
		}

		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}

		public Sort getSort() {
			return sort;
		}

		public void setSort(Sort sort) {
			this.sort = sort;
		}

		public Boolean getSelected() {
			return selected;
		}

		public void setSelected(Boolean selected) {
			this.selected = selected;
		}

		@Override
		public String geParamValue() {
			return fieldName + "##" + sort;
		}

		@Override
		public Condition getSearchCondition() {
			return new FieldSort.Condition(this.fieldName, this.sort);
		}

		@Override
		public int compareTo(ValueElement other) {
			return this.fieldName.compareTo(((FieldSort.Value)other).fieldName);
		}

		@Override
		public Class<?> getConditionClass() {
			return FieldSort.Condition.class;
		}
	}

	@SearchCondition(urlParam="s")
	public static class Condition extends AbstractSearchCondition {

		String fieldName;
		Sort sort;

		Condition(String fieldName, Sort sort) {
			this.fieldName = fieldName;
			this.sort = sort;
		}
	}

	@Override
	public void mergeSearchCondition(AbstractSearchCondition condition) {
		if(FieldSort.Condition.class.isAssignableFrom(condition.getClass())){
			FieldSort.Condition sortCondition = (FieldSort.Condition)condition;
			for(FieldSort.Value value:this.getValues()){
				if(value.fieldName.equals(sortCondition.fieldName) &&
						value.sort.equals(sortCondition.sort)){
					value.selected = true;
				}
			}
		}
	}

	public static FieldSort.Value getRelevancySort() {
		return new FieldSort.Value("Relevancy", "score", Sort.DESC);
	}
	
	@SearchConverter	
	public static class Converter implements
	org.springframework.core.convert.converter.Converter<String, Condition> {

		@Override
		public Condition convert(String source) {
			String field = source.split("##")[0];
			String sort = source.split("##")[1];
			if(sort.equalsIgnoreCase(Sort.DESC.toString())){
				return new Condition(field, Sort.DESC);				
			} else {
				return new Condition(field, Sort.ASC);				
			}
		}
	}
}

@SearchAdapter
class FieldSortSolrAdatptor {

	@PreSearchAdapter
	public SolrQuery setSortCondition(FieldSort.Condition condition,
			SolrQuery query) {
		if(condition.sort.equals(Sort.ASC)) {
			query.addSort(condition.fieldName, ORDER.asc);
		} else {
			query.addSort(condition.fieldName, ORDER.desc);
		}
		return query;
	}

}
