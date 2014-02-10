package com.searchbox.core.search.stat;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;

import com.searchbox.anno.SearchAdaptor;
import com.searchbox.core.adaptor.SolrElementAdapter;
import com.searchbox.domain.Collection;
import com.searchbox.domain.Preset;

@SearchAdaptor
public class BasicSearchStatsAdapter implements SolrElementAdapter<BasicSearchStats>{

	@Override
	public SolrQuery doAdapt(Collection collection,
			BasicSearchStats searchElement, SolrQuery query) {
		return query;
	}

	@Override
	public BasicSearchStats doAdapt(Preset preset,
			BasicSearchStats searchElement, SolrQuery query,
			QueryResponse response) {
		System.out.println("XOXOXOXOXOXOXO");
		searchElement.setHitCount(response.getResults().getNumFound());
		searchElement.setSearchTime(response.getElapsedTime());
		return searchElement;
	}
}