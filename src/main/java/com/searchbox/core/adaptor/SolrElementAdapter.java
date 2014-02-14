package com.searchbox.core.adaptor;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;

import com.searchbox.core.dm.Collection;
import com.searchbox.core.dm.Preset;
import com.searchbox.core.search.SearchElement;

public interface SolrElementAdapter<K extends SearchElement> 
	extends SearchElementAdapter<K, SolrQuery, QueryResponse> {

	@Override
	public SolrQuery doAdapt(Collection collection, K searchElement, SolrQuery query);

	@Override
	public K doAdapt(Preset preset, K searchElement, SolrQuery query, QueryResponse response);

}
