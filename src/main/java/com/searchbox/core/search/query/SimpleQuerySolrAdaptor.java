package com.searchbox.core.search.query;

import com.searchbox.core.adaptor.SearchConditionAdaptor;
import com.searchbox.core.adaptor.SearchElementAdaptor;
import com.searchbox.core.engine.SolrQuery;
import com.searchbox.core.search.query.SimpleQuery.Condition;
import com.searchbox.domain.Collection;

public class SimpleQuerySolrAdaptor implements SearchElementAdaptor<SimpleQuery, SolrQuery>,
		SearchConditionAdaptor<SimpleQuery.Condition, SolrQuery> {

	@Override
	public SolrQuery doAdapt(Collection collection, Condition condition,
			SolrQuery query) {

		//TODO use collection to populate QF (QueryFields)
		query.setQuery(condition.getQuery());
		
		return query;
	}

	@Override
	public SolrQuery doAdapt(Collection collection, SimpleQuery SearchElement,
			SolrQuery query) {
		query.setQuery("*:*");
		return query;
	}

}
