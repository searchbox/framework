package com.searchbox.core.search.filter;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.params.FacetParams;
import org.springframework.core.convert.converter.Converter;

import com.searchbox.core.PreSearchAdapter;
import com.searchbox.core.SearchAdapter;
import com.searchbox.core.SearchConverter;
import com.searchbox.core.search.SearchCondition;
import com.searchbox.core.search.facet.FieldFacet;

public class FieldValueCondition extends SearchCondition {

	String fieldName;
	String value;
	Boolean taged;
	
	public FieldValueCondition(String fieldName, String value) {
		this.fieldName = fieldName;
		this.value = value;
		this.taged = false;
	}
	
	public FieldValueCondition(String fieldName, String value, Boolean taged) {
		this.fieldName = fieldName;
		this.value = value;
		this.taged = taged;
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
	
	public Boolean getTaged() {
		return taged;
	}

	public void setTaged(Boolean taged) {
		this.taged = taged;
	}



	@SearchConverter
	public static class FieldValueConditionConverter
			implements Converter<String, FieldValueCondition> {

		@Override
		public FieldValueCondition convert(String source) {
			String field = source.split("\\[")[0];
			String value = source.split("\\[")[1].split("]")[0];
			return new FieldValueCondition(field, value);
		}
	}
}


@SearchAdapter
class FieldValueConditionSolrAdaptor {
	
	@PreSearchAdapter
	public SolrQuery createFilterQueries(FieldValueCondition condition, SolrQuery query) {
		String conditionValue = ClientUtils.escapeQueryChars(condition.getValue());
		boolean isnew = true;
		List<String> fqs = new ArrayList<String>();
		if (query.getFilterQueries() != null) {
			for (String fq : query.getFilterQueries()) {
				if (fq.contains(condition.getFieldName() + ":")) {
					isnew = false;
					fq = fq + " OR " + conditionValue;
				}
				fqs.add(fq);
			}
		}
		if (isnew) {
			if (condition.getTaged()) {
				fqs.add("{!tag=" + condition.getFieldName() + "}"
						+ condition.getFieldName() + ":" + conditionValue);
			} else {
				fqs.add(condition.getFieldName() + ":" + conditionValue);
			}
		}
		query.setFilterQueries(fqs.toArray(new String[fqs.size()]));
		return query;
	}

}
