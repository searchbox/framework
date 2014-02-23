package com.searchbox.core.search.facet;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;

import com.searchbox.anno.SearchAdapter;
import com.searchbox.anno.SearchAdapterMethod;
import com.searchbox.anno.SearchAdapterMethod.Target;
import com.searchbox.anno.SearchAttribute;
import com.searchbox.anno.SearchComponent;
import com.searchbox.core.search.ConditionalValueElement;
import com.searchbox.core.search.SearchCondition;
import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.SearchElementWithConditionalValues;
import com.searchbox.core.search.ValueElement;
import com.searchbox.ref.Order;
import com.searchbox.ref.Sort;

@SearchComponent(prefix = "ff", condition = FieldFacet.ValueCondition.class, converter = FieldFacet.Converter.class)
public class FieldFacet
		extends
		SearchElementWithConditionalValues<FieldFacet.Value, FieldFacet.ValueCondition> {

	@SearchAttribute
	private String fieldName;
	
	@SearchAttribute
	private Boolean sticky = true;

	public FieldFacet() {
		super("",SearchElement.Type.FACET);
	}

	public FieldFacet(String label, String fieldName) {
		super(label,SearchElement.Type.FACET);
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

	public class Value extends ConditionalValueElement<FieldFacet.ValueCondition>{

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
		
		public String getValue(){
			return this.value;
		}

		public Value setSelected(Boolean selected) {
			this.selected = selected;
			return this;
		}

		@Override
		public FieldFacet.ValueCondition getSearchCondition() {
			return new FieldFacet.ValueCondition(fieldName, this.value);
		}

		@Override
		public String geParamValue() {
			return fieldName + "[" + this.value + "]";
		}

		@Override
		public int compareTo(ValueElement other) {
			FieldFacet.Value o = (FieldFacet.Value)other;
			int diff = 0;
			if (order.equals(Order.BY_KEY)) {
				diff = this.getLabel().compareTo(o.getLabel())*10;
			} else if(!this.getCount().equals(o.getCount())){
				diff = this.getCount().compareTo(o.getCount())*10;
			} else {
				diff = this.getCount().compareTo(o.getCount())*10+
						(this.getLabel().compareTo(o.getLabel())*((sort.equals(Sort.ASC)) ? 1 : -1));
			}
			return diff * ((sort.equals(Sort.ASC)) ? 1 : -1);
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
	}

	public static class Converter
			implements
			org.springframework.core.convert.converter.Converter<String, ValueCondition> {

		@Override
		public ValueCondition convert(String source) {
			String field = source.split("\\[")[0];
			String value = source.split("\\[")[1].split("]")[0];
			return new ValueCondition(field, value);
		}
	}
	
	@Override
	public void mergeSearchCondition(SearchCondition condition) {
		if (FieldFacet.ValueCondition.class.equals(condition.getClass())) {
			if (this.fieldName.equals(((FieldFacet.ValueCondition) condition)
					.getFieldName())) {
				for (FieldFacet.Value value : this.getValues()) {
					if (value.value.equals(
							((FieldFacet.ValueCondition) condition).getValue())) {
						value.setSelected(true);
					}
				}
			}
		}
	}

	public boolean getSticky() {
		return this.sticky;
	}
	
	public void setSticky(boolean sticky){
		this.sticky = sticky;
	}
	
}

@SearchAdapter(target=FieldFacet.class)
class FieldFacetSolrAdaptor {
	
	@SearchAdapterMethod(target=Target.PRE)
	public SolrQuery createFilterQueries(FieldFacet facet, 
			FieldFacet.ValueCondition condition, SolrQuery query) {
		String conditionValue = ClientUtils.escapeQueryChars(condition.value);
		boolean isnew = true;
		List<String> fqs = new ArrayList<String>();
		if(query.getFilterQueries() != null){
			for(String fq:query.getFilterQueries()){
				if(fq.contains(condition.fieldName+":")){
					isnew = false;
					fq = fq+" OR " + conditionValue;
				}
				fqs.add(fq);
			}
		}
		if(isnew){
			if(facet.getSticky()){
				fqs.add("{!tag="+condition.fieldName+"}"+condition.fieldName+":"+conditionValue);
			} else {
				fqs.add(condition.fieldName+":"+conditionValue);
			}
		}
		query.setFilterQueries(fqs.toArray(new String[fqs.size()]));
		return query;
	}

	@SearchAdapterMethod(target=Target.PRE)
	public SolrQuery addFacetField(FieldFacet facet, SolrQuery query) {
		if(facet.getSticky()){
			query.addFacetField("{!ex="+facet.getFieldName()+"}"+facet.getFieldName());
		} else {
			query.addFacetField(facet.getFieldName());
		}
		return query;
	}

	@SearchAdapterMethod(target=Target.POST)
	public FieldFacet getFacetValues(FieldFacet fieldFacet, QueryResponse response) {
		for(FacetField facet:response.getFacetFields()){
			if(facet.getName().equals(fieldFacet.getFieldName())){
				for(Count value:facet.getValues()){
					fieldFacet.addValueElement(value.getName(), (int)value.getCount());
				}
			}
		}
		return fieldFacet;
	}
}