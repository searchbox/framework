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
package com.searchbox.core.search.facet;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.FacetParams;

import com.searchbox.core.PostSearchAdapter;
import com.searchbox.core.PreSearchAdapter;
import com.searchbox.core.SearchAdapter;
import com.searchbox.core.SearchAttribute;
import com.searchbox.core.SearchComponent;
import com.searchbox.core.ref.Order;
import com.searchbox.core.ref.Sort;
import com.searchbox.core.search.AbstractSearchCondition;
import com.searchbox.core.search.ConditionalValueElement;
import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.SearchElementWithConditionalValues;
import com.searchbox.core.search.ValueElement;
import com.searchbox.core.search.filter.FieldValueCondition;

@SearchComponent
public class FieldFacet
		extends
		SearchElementWithConditionalValues<FieldFacet.Value, FieldValueCondition> {
	
	@SearchAttribute
	private String fieldName;

	@SearchAttribute
	private Boolean sticky = true;

	public FieldFacet() {
		super("", SearchElement.Type.FACET);
	}

	public FieldFacet(String label, String fieldName) {
		super(label, SearchElement.Type.FACET);
		this.fieldName = fieldName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public FieldFacet addValueElement(String label, Integer count) {
		return this.addValueElement(label, label, count);
	}

	public FieldFacet addValueElement(String label, String value, Integer count) {
		this.addValueElement(new FieldFacet.Value(label, value, count));
		return this;
	}

	public class Value extends
			ConditionalValueElement<FieldValueCondition> {

		/**
		 * 
		 */
		private static final long serialVersionUID = -5020007167116586645L;

		public String value;
		public Integer count;
		public Boolean selected = false;

		public Value(String label, String value, Integer count) {
			super(label);
			this.value = value;
			this.count = count;
		}

		public Value(String label, String value) {
			super(label);
			this.value = value;
		}

		public Integer getCount() {
			return this.count;
		}

		public Boolean getSelected() {
			return this.selected;
		}

		public String getValue() {
			return this.value;
		}

		public Value setSelected(Boolean selected) {
			this.selected = selected;
			return this;
		}

		@Override
		public FieldValueCondition getSearchCondition() {
			return new FieldValueCondition(fieldName, this.value, sticky);
		}

		@Override
		public String geParamValue() {
			return fieldName + "[" + this.value + "]";
		}

		@Override
		public int compareTo(ValueElement other) {
			FieldFacet.Value o = (FieldFacet.Value) other;
			int diff = 0;
			if (order.equals(Order.BY_KEY)) {
				diff = this.getLabel().compareTo(o.getLabel()) * 10;
			} else if (!this.getCount().equals(o.getCount())) {
				diff = this.getCount().compareTo(o.getCount()) * 10;
			} else {
				diff = this.getCount().compareTo(o.getCount())
						* 10
						+ (this.getLabel().compareTo(o.getLabel()) * ((sort
								.equals(Sort.ASC)) ? 1 : -1));
			}
			return diff * ((sort.equals(Sort.ASC)) ? 1 : -1);
		}

		@Override
		public Class<?> getConditionClass() {
			return FieldValueCondition.class;
		}
	}

	@Override
	public void mergeSearchCondition(AbstractSearchCondition condition) {
		if (FieldValueCondition.class.equals(condition.getClass())) {
			if (this.fieldName.equals(((FieldValueCondition) condition)
					.getFieldName())) {
				for (FieldFacet.Value value : this.getValues()) {
					if (value.value
							.equals(((FieldValueCondition) condition)
									.getValue())) {
						value.setSelected(true);
					}
				}
			}
		}
	}

	public boolean getSticky() {
		return this.sticky;
	}

	public void setSticky(boolean sticky) {
		this.sticky = sticky;
	}

}

@SearchAdapter
class FieldFacetSolrAdaptor {

	@PreSearchAdapter
	public SolrQuery addFacetField(FieldFacet facet, SolrQuery query) {
		boolean defined = false;
		String[] facetFields = query.getFacetFields();
		if (facetFields != null) {
			for (String facetField : query.getParams(FacetParams.FACET_FIELD)) {
				if (facetField.contains(facet.getFieldName())) {
					defined = true;
				}
			}
		}
		if (!defined) {
			if (facet.getSticky()) {
				query.addFacetField("{!ex=" + facet.getFieldName() + "}"
						+ facet.getFieldName());
			} else {
				query.addFacetField(facet.getFieldName());
			}
		}
		return query;
	}

	@PostSearchAdapter
	public FieldFacet getFacetValues(FieldFacet fieldFacet,
			QueryResponse response) {
		if (response.getFacetFields() != null) {
			for (FacetField facet : response.getFacetFields()) {
				if (facet.getName().equals(fieldFacet.getFieldName())) {
					for (Count value : facet.getValues()) {
						fieldFacet.addValueElement(value.getName(),
								(int) value.getCount());
					}
				}
			}
		}
		return fieldFacet;
	}
}
