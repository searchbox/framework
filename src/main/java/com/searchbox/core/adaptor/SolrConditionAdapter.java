package com.searchbox.core.adaptor;

import org.apache.solr.client.solrj.SolrQuery;

import com.searchbox.core.dm.Collection;
import com.searchbox.core.search.SearchCondition;

public interface SolrConditionAdapter<K extends SearchCondition> extends SearchConditionAdapter<K, SolrQuery> {

	@Override
	public SolrQuery doAdapt(Collection collection, K condition, SolrQuery query);
}
