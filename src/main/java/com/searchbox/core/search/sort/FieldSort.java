package com.searchbox.core.search.sort;

import java.io.Serializable;
import java.util.List;

import org.apache.lucene.search.Query;

import com.searchbox.anno.SearchComponent;
import com.searchbox.core.search.ConditionalSearchElement;
import com.searchbox.core.search.ConditionalValueElement;
import com.searchbox.core.search.SearchCondition;
import com.searchbox.core.search.SearchElementType;
import com.searchbox.core.search.SearchElementWithConditionalValues;
import com.searchbox.ref.Sort;

@SearchComponent(prefix = "fs", condition = FieldSort.Condition.class)
public class FieldSort extends SearchElementWithConditionalValues<FieldSort.Value, FieldSort.Condition> {
	
	public FieldSort() {
		super("Sort Component");
		this.setType(SearchElementType.SORT);
	}
		
	public static class Value extends ConditionalValueElement<String, FieldSort.Condition> 
		implements Serializable{

		public Sort sort;
		public Boolean selected;
		
		public Value() {
			super();
		}
		
		public Value(String field, Sort sort) {
			super("Sort Condition");
			this.value = field;
			this.sort = sort;
		}

		@Override
		public String geParamValue() {
			return value + "##" + sort;
		}

		@Override
		public Condition getSearchCondition() {
			return new FieldSort.Condition(this.value, this.sort);
		}
	}

	public static class Condition extends SearchCondition {

		String fieldName;
		Sort sort;

		Condition(String fieldName, Sort sort) {
			this.fieldName = fieldName;
			this.sort = sort;
		}
	}

	@Override
	public void mergeSearchCondition(SearchCondition condition) {
		if(FieldSort.Condition.class.isAssignableFrom(condition.getClass())){
			FieldSort.Condition sortCondition = (FieldSort.Condition)condition;
			for(FieldSort.Value value:this.getValues()){
				if(value.getValue().equals(sortCondition.fieldName) &&
						value.sort.equals(sortCondition.sort)){
					value.selected = true;
				}
			}
		}
	}
}