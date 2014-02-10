package com.searchbox.core.adaptor;

import org.apache.solr.client.solrj.SolrQuery;

import com.searchbox.core.search.SearchCondition;
import com.searchbox.domain.Collection;

public interface SolrConditionAdapter<K extends SearchCondition> extends SearchConditionAdapter<K, SolrQuery> {

	@Override
	public SolrQuery doAdapt(Collection collection, K condition, SolrQuery query);
}
