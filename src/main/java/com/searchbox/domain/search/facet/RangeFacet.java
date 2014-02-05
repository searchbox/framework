package com.searchbox.domain.search.facet;

import org.apache.lucene.search.Query;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.searchbox.ann.search.SearchComponent;
import com.searchbox.domain.search.ConditionalSearchElementWithValues;
import com.searchbox.domain.search.SearchCondition;
import com.searchbox.domain.search.SearchElementType;
import com.searchbox.domain.search.ValueElement;

@RooJavaBean
@RooToString
@SearchComponent(prefix = "fr", condition = RangeFacet.Condition.class)
public class RangeFacet extends
		ConditionalSearchElementWithValues<RangeFacet.Value,  RangeFacet.Condition> {

	private final String fieldName;
	private final String lowerElement = null;
	private final String upperElement = null;

	public RangeFacet(String label, String fieldName) {
		super(label);
		this.fieldName = fieldName;
		this.setType(SearchElementType.FACET);
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

	public class Value extends ValueElement<String> {

		private Integer count;

		public Value(String label, String value, Integer count) {
			super(label, value);
			this.count = count;
		}

		public Value(String label, String value) {
			super(label, value);
		}

		public Integer getCount() {
			return this.count;
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

		@Override
		protected Query getConditionalQuery() {
			return null;// new TermRangeQuery(fieldName, fromValue, toValue,
						// true, true);
		}
	}
}