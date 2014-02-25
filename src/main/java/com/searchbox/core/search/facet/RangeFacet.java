package com.searchbox.core.search.facet;

import com.searchbox.anno.SearchComponent;
import com.searchbox.core.search.ConditionalSearchElementWithValues;
import com.searchbox.core.search.SearchCondition;
import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.ValueElement;

@SearchComponent
public class RangeFacet extends
		ConditionalSearchElementWithValues<RangeFacet.Value,  RangeFacet.Condition> {
	

	private final String fieldName;
	private final String lowerElement = null;
	private final String upperElement = null;

	public RangeFacet(String label, String fieldName) {
		super(label,SearchElement.Type.FACET);
		this.fieldName = fieldName;
	}

	public RangeFacet addValueElement(String label, Integer count) {
		return this.addValueElement(label, label, count);
	}

	public RangeFacet addValueElement(String label, String value, Integer count) {
		this.addValueElement(new RangeFacet.Value(label, value, count));
		return this;
	}
	
	@Override
	public  RangeFacet.Condition getSearchCondition() {
		return new RangeFacet.Condition(this.fieldName, this.lowerElement,
				this.upperElement);
	}

	@Override
	public String geParamValue() {
		return this.fieldName + "[" + this.lowerElement + "##"
				+ this.upperElement + "]";
	}

	public class Value extends ValueElement {

		/**
		 * 
		 */
		private static final long serialVersionUID = 9161435550332928573L;
		
		private String value;
		private Integer count;

		public Value(String label, String value, Integer count) {
			super(label);
			this.setValue(value);
			this.count = count;
		}

		public Value(String label, String value) {
			super(label);
			this.setValue(value);
		}

		public Integer getCount() {
			return this.count;
		}

		@Override
		public int compareTo(ValueElement other) {
			// TODO Auto-generated method stub
			return 0;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}

	public class Condition extends SearchCondition {

		String fieldName;
		String lowerElement;
		String upperElement;

		Condition(String field, String from, String to) {
			this.fieldName = field;
			this.lowerElement = from;
			this.upperElement = to;
		}
	}

	@Override
	public void mergeSearchCondition(SearchCondition condition) {
		// TODO Auto-generated method stub
		
	}
}