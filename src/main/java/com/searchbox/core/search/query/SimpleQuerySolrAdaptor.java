package com.searchbox.core.search.query;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.anno.SearchAdaptor;
import com.searchbox.core.adaptor.SolrConditionAdapter;
import com.searchbox.core.adaptor.SolrElementAdapter;
import com.searchbox.core.search.query.SimpleQuery.Condition;
import com.searchbox.domain.Collection;
import com.searchbox.domain.Preset;

@SearchAdaptor
public class SimpleQuerySolrAdaptor implements SolrConditionAdapter<SimpleQuery.Condition>,
	SolrElementAdapter<SimpleQuery> {

	private static Logger logger = LoggerFactory.getLogger(SimpleQuerySolrAdaptor.class);
	@Override
	public SolrQuery doAdapt(Collection collection, SimpleQuery SearchElement,
			SolrQuery query) {
		if(SearchElement.getQuery() == null || SearchElement.getQuery().isEmpty()){
			query.setQuery("*:*");
		} else {
			query.setQuery(SearchElement.getQuery());
		}
		query.setRequestHandler("edismax");
		query.set("q.alt", "*:*");
		return query;
	}

	@Override
	public SimpleQuery doAdapt(Preset preset, SimpleQuery searchElement,
			SolrQuery query, QueryResponse response) {
		if(query.getQuery().equals("*:*")){
			searchElement.setQuery("");
		} else {
			searchElement.setQuery(query.getQuery());			
		}
		return searchElement;
	}

	@Override
	public SolrQuery doAdapt(Collection collection, Condition condition,
			SolrQuery query) {
		if(condition.getQuery() == null || condition.getQuery().isEmpty()){
			query.setQuery("*:*");
		} else {
			query.setQuery(condition.getQuery());
		}
		return query;
	}

}
