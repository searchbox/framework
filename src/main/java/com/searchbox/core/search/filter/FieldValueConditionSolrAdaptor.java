package com.searchbox.core.search.filter;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.util.ClientUtils;

import com.searchbox.core.PreSearchAdapter;
import com.searchbox.core.SearchAdapter;
import com.searchbox.engine.solr.SolrSearchEngine;

@SearchAdapter
public class FieldValueConditionSolrAdaptor {
	
	@PreSearchAdapter
	public void createFilterQueries(SolrSearchEngine engine, FieldValueCondition condition, SolrQuery query) {
		
		String conditionValue = ClientUtils.escapeQueryChars(condition.getValue());
		String facetKey = engine.getKeyForField(condition.getField());

		boolean isnew = true;
		List<String> fqs = new ArrayList<String>();
		if (query.getFilterQueries() != null) {
			for (String fq : query.getFilterQueries()) {
				if (fq.contains(facetKey + ":")) {
					isnew = false;
					fq = fq + " OR " + conditionValue;
				}
				fqs.add(fq);
			}
		}
		if (isnew) {
			if (condition.getTaged()) {
				fqs.add("{!tag=" + facetKey + "}"
						+ facetKey + ":" + conditionValue);
			} else {
				fqs.add(facetKey + ":" + conditionValue);
			}
		}
		query.setFilterQueries(fqs.toArray(new String[fqs.size()]));
	}
}