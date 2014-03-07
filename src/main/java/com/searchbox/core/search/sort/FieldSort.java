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

import com.searchbox.core.SearchAttribute;
import com.searchbox.core.SearchComponent;
import com.searchbox.core.SearchCondition;
import com.searchbox.core.SearchConverter;
import com.searchbox.core.dm.Field;
import com.searchbox.core.ref.Sort;
import com.searchbox.core.ref.StringUtils;
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
		
		private String field;
		
		private Sort sort;
		
		private Boolean selected;		
		
		public Value(String label, String field, Sort sort) {
			super(label);
			this.field = field;
			this.sort = sort;
			this.selected = false;
		}
		
		
		public String getFieldName() {
			return field;
		}

		public Sort getSort() {
			return sort;
		}
		
		public Boolean getSelected() {
			return selected;
		}

		public void setSelected(Boolean selected) {
			this.selected = selected;
		}

		@Override
		public String geParamValue() {
			return field + " " + this.sort;
		}

		@Override
		public Condition getSearchCondition() {
			return new FieldSort.Condition(this.field, this.sort);
		}

		@Override
		public int compareTo(ValueElement other) {
			return this.getLabel().compareTo(((FieldSort.Value)other).getLabel());
		}

		@Override
		public Class<?> getConditionClass() {
			return FieldSort.Condition.class;
		}
	}

	@SearchCondition(urlParam="s")
	public static class Condition extends AbstractSearchCondition {

		private final String field;
		private final Sort sort;

		Condition(String field, Sort sort) {
			this.field = field;
			this.sort = sort;
		}

		/**
		 * @return the field
		 */
		public String getField() {
			return field;
		}

		/**
		 * @return the sort
		 */
		public Sort getSort() {
			return sort;
		}
	}

	@Override
	public void mergeSearchCondition(AbstractSearchCondition condition) {
		if(FieldSort.Condition.class.isAssignableFrom(condition.getClass())){
			FieldSort.Condition sortCondition = (FieldSort.Condition)condition;
			for(FieldSort.Value value:this.getValues()){
				if(value.getFieldName().equals(sortCondition.getField()) &&
						value.getSort().equals(sortCondition.getSort())){
					value.selected = true;
				}
			}
		}
	}

	public static FieldSort.Value getRelevancySort() {
		return new FieldSort.Value("Relevancy", "score", Sort.DESC);
	}
	
	@SearchConverter	
	public static class SortConverter implements
	org.springframework.core.convert.converter.Converter<String, Condition> {

		@Override
		public Condition convert(String source) {
			String cfield = source.split(" ")[0];
			String sort = source.split(" ")[1];
			
			if(sort.equalsIgnoreCase(Sort.DESC.toString())){
				return new Condition(cfield, Sort.DESC);				
			} else {
				return new Condition(cfield, Sort.ASC);				
			}
		}
	}
}
