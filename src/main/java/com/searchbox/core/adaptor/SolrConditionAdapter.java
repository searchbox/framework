package com.searchbox.core.adaptor;

import org.apache.solr.client.solrj.SolrQuery;

import com.searchbox.core.dm.Preset;
import com.searchbox.core.search.SearchCondition;

public interface SolrConditionAdapter<K extends SearchCondition> extends SearchConditionAdapter<K, SolrQuery> {

	@Override
	public SolrQuery doAdapt(Preset preset, K condition, SolrQuery query);
}
