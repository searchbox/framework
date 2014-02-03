package com.searchbox.domain.search.facet;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import com.searchbox.domain.search.ConditionalValueElement;
import com.searchbox.domain.search.SearchCondition;
import com.searchbox.domain.search.SearchElementWithValues;

@RooJavaBean
@RooToString
public class FieldFacet extends SearchElementWithValues<FieldFacet.Value> {

	private final String fieldName;

	public FieldFacet(String label, String fieldName) {
		super(label);
		this.fieldName = fieldName;
	}
	
	public FieldFacet addValueElement(String label, Integer count){
		return this.addValueElement(label, label, count);
	}
	
	public FieldFacet addValueElement(String label, String value, Integer count){
		this.addValueElement(new FieldFacet.Value(label, value, count));
		return this;
	}
	
	public class Value extends ConditionalValueElement<String> {

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

		@Override
		public SearchCondition getSearchCondition() {
			return new FieldFacetValueCondition(fieldName, this.value);
		}
	}
}

class FieldFacetValueCondition extends SearchCondition {

	String fieldName;
	String value;

	FieldFacetValueCondition(String fieldName, String value) {
		this.fieldName = fieldName;
		this.value = value;
	}

	@Override
	protected Query getConditionalQuery() {
		return new TermQuery(new Term(fieldName, value));
	}
}