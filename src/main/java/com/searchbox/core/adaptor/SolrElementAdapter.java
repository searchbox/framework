package com.searchbox.core.adaptor;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;

import com.searchbox.core.search.SearchElement;
import com.searchbox.domain.Collection;
import com.searchbox.domain.Preset;

public interface SolrElementAdapter<K extends SearchElement> 
	extends SearchElementAdapter<K, SolrQuery, QueryResponse> {

	@Override
	public SolrQuery doAdapt(Collection collection, K searchElement, SolrQuery query);

	@Override
	public K doAdapt(Preset preset, K searchElement, SolrQuery query, QueryResponse response);

}
