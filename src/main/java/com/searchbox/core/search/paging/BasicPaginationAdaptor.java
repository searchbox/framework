package com.searchbox.core.search.paging;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;

import com.searchbox.core.PostSearchAdapter;
import com.searchbox.core.PreSearchAdapter;
import com.searchbox.core.SearchAdapter;
import com.searchbox.core.search.paging.BasicPagination.PageCondition;

@SearchAdapter
public class BasicPaginationAdaptor  {

	@PreSearchAdapter
	public void setNumberOfHitsPerPage(BasicPagination searchElement, SolrQuery query){
		query.setRows(searchElement.getHitsPerPage());
	}
	
	@PostSearchAdapter
	public BasicPagination getPaginationSettings(BasicPagination searchElement,
			SolrQuery query, QueryResponse response) {
		
		Integer hitsPerPage = query.getRows();
		Long numberOfHits = response.getResults().getNumFound();

		//If rows in not set, we must guess it form resultset
		if(hitsPerPage == null){
			hitsPerPage = response.getResults().size();
		}
		
		searchElement.setMaxPage((int)(Math.ceil(numberOfHits/hitsPerPage)));
		searchElement.setNumberOfHits(numberOfHits);
		searchElement.setHitsPerPage(hitsPerPage);
		return searchElement;
	}

	@PreSearchAdapter
	public SolrQuery setPagination(BasicPagination searchElement, PageCondition condition,
			SolrQuery query) {
		query.setStart(condition.page*searchElement.getHitsPerPage());
		query.setRows(searchElement.getHitsPerPage());
		return query;
	}
}