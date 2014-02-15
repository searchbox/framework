package com.searchbox.core.search.sort;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.QueryResponse;

import com.searchbox.anno.SearchAdaptor;
import com.searchbox.core.adaptor.SolrConditionAdapter;
import com.searchbox.core.adaptor.SolrElementAdapter;
import com.searchbox.core.dm.Collection;
import com.searchbox.core.dm.Preset;
import com.searchbox.core.search.sort.FieldSort.Condition;
import com.searchbox.ref.Sort;

@SearchAdaptor
public class FieldSortSolrAdatptor implements SolrElementAdapter<FieldSort>,
SolrConditionAdapter<FieldSort.Condition> {

	@Override
	public SolrQuery doAdapt(Collection collection, Condition condition,
			SolrQuery query) {
		if(condition.sort.equals(Sort.ASC)) {
			query.addSort(condition.fieldName, ORDER.asc);
		} else {
			query.addSort(condition.fieldName, ORDER.desc);
		}
		return query;
	}

	@Override
	public SolrQuery doAdapt(Collection collection, FieldSort searchElement,
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
