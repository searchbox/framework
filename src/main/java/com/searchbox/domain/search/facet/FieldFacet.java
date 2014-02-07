package com.searchbox.domain.search.facet;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.searchbox.ann.search.SearchComponent;
import com.searchbox.domain.search.ConditionalValueElement;
import com.searchbox.domain.search.SearchCondition;
import com.searchbox.domain.search.SearchElementType;
import com.searchbox.domain.search.SearchElementWithConditionalValues;
import com.searchbox.ref.Order;
import com.searchbox.ref.Sort;

@RooJavaBean
@RooToString
@SearchComponent(prefix="ff", condition=FieldFacet.ValueCondition.class, converter=FieldFacet.Converter.class)
public class FieldFacet extends SearchElementWithConditionalValues<FieldFacet.Value, FieldFacet.ValueCondition> {

	private final String fieldName;
	
	public FieldFacet(){
		fieldName = "";
	}

	public FieldFacet(String label, String fieldName) {
		super(label);
		this.fieldName = fieldName;
		this.setType(SearchElementType.FACET);
	}
	
	public FieldFacet addValueElement(String label, Integer count){
		return this.addValueElement(label, label, count);
	}
	
	public FieldFacet addValueElement(String label, String value, Integer count){
		this.addValueElement(new FieldFacet.Value(label, value, count));
		return this;
	}
	
	public class Value extends ConditionalValueElement<String, FieldFacet.ValueCondition> implements Comparable<Value>{

		private Integer count;
		private Boolean selected;

		public Value(String label, String value, Integer count) {
			super(label, value);
			this.count = count;
		}

		public Value(String label, String value) {
			super(label, value);
		}
		
		public Integer getCount(){
			return this.count;
		}
		
		public Boolean getSelected(){
			return this.selected;
		}
		
		public Value setSelected(Boolean selected){
			this.selected = selected;
			return this;
		}

		@Override
		public FieldFacet.ValueCondition getSearchCondition() {
			return new FieldFacet.ValueCondition(fieldName, this.value);
		}

		@Override
		public String geParamValue() {
			return fieldName+"["+this.value+"]";
		}

		@Override
		public int compareTo(Value o) {
			int diff = 0;
			if(order.equals(Order.KEY)){
				diff = this.getLabel().compareTo(o.getLabel());
			} else {
				diff = this.getCount().compareTo(o.getCount());
			}
			return diff*((sort.equals(Sort.ASC))?1:-1);
		}
	}

	public static class ValueCondition extends SearchCondition {
	
		String fieldName;
		String value;
	
		public ValueCondition(String fieldName, String value) {
			this.fieldName = fieldName;
			this.value = value;
		}
	
		public String getFieldName() {
			return fieldName;
		}

		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		@Override
		protected Query getConditionalQuery() {
			return new TermQuery(new Term(fieldName, value));
		}
	}
	
	public static class Converter implements 
		org.springframework.core.convert.converter.Converter<String, ValueCondition>{

		@Override
		public ValueCondition convert(String source) {
			String field = source.split("\\[")[0];
			String value = source.split("\\[")[1].split("]")[0];
			return new ValueCondition(field, value);
		}
		
	}

	@Override
	public void mergeSearchCondition(SearchCondition condition) {
		if(FieldFacet.ValueCondition.class.equals(condition.getClass())){
			if(this.fieldName.equals(((FieldFacet.ValueCondition)condition).getFieldName())){
				for(FieldFacet.Value value:this.getValues()){
					if(value.getValue().equals(((FieldFacet.ValueCondition)condition).getValue())){
						value.setSelected(true);
					}
				}
			}
		}
	}
}