package com.searchbox.core.search.sort;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;

import com.searchbox.core.PreSearchAdapter;
import com.searchbox.core.SearchAdapter;
import com.searchbox.core.ref.Sort;

@SearchAdapter
public class FieldSortSolrAdatptor {

	@PreSearchAdapter
	public SolrQuery setSortCondition(FieldSort.Condition condition,
			SolrQuery query) {
		if(condition.sort.equals(Sort.ASC)) {
			query.addSort(condition.fieldName, ORDER.asc);
		} else {
			query.addSort(condition.fieldName, ORDER.desc);
		}
		return query;
	}

}