package com.searchbox.core.search.sort;

import java.io.Serializable;
import java.util.SortedSet;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.QueryResponse;

import com.searchbox.anno.SearchAdapter;
import com.searchbox.anno.SearchAttribute;
import com.searchbox.anno.SearchComponent;
import com.searchbox.core.adaptor.SolrConditionAdapter;
import com.searchbox.core.adaptor.SolrElementAdapter;
import com.searchbox.core.dm.Preset;
import com.searchbox.core.search.ConditionalValueElement;
import com.searchbox.core.search.SearchCondition;
import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.SearchElementWithConditionalValues;
import com.searchbox.core.search.ValueElement;
import com.searchbox.ref.Sort;

@SearchComponent(prefix = "fs", condition = FieldSort.Condition.class, converter=FieldSort.Converter.class)
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
class FieldSortSolrAdatptor implements SolrElementAdapter<FieldSort>,
SolrConditionAdapter<FieldSort.Condition> {

	@Override
	public SolrQuery doAdapt(Preset preset, FieldSort.Condition condition,
			SolrQuery query) {
		if(condition.sort.equals(Sort.ASC)) {
			query.addSort(condition.fieldName, ORDER.asc);
		} else {
			query.addSort(condition.fieldName, ORDER.desc);
		}
		return query;
	}

	@Override
	public SolrQuery doAdapt(Preset preset, FieldSort searchElement,
			SolrQuery query) {
		//TODO check if we need field in FL for sorting. Solr doc
		//query.addField("score");
		return query;
	}

	@Override
	public FieldSort doAdapt(Preset preset, FieldSort searchElement,
			SolrQuery query, QueryResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

}
