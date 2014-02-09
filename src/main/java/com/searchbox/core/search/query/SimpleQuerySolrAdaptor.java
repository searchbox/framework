package com.searchbox.core.search.query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.anno.SearchAdaptor;
import com.searchbox.core.adaptor.SearchConditionAdaptor;
import com.searchbox.core.adaptor.SearchElementAdaptor;
import com.searchbox.core.engine.SolrQuery;
import com.searchbox.core.search.query.SimpleQuery.Condition;
import com.searchbox.domain.Collection;
import com.searchbox.web.ApplicationConversionService;

@SearchAdaptor
public class SimpleQuerySolrAdaptor implements SearchElementAdaptor<SimpleQuery, SolrQuery>,
		SearchConditionAdaptor<SimpleQuery.Condition, SolrQuery> {

	private static Logger logger = LoggerFactory.getLogger(SimpleQuerySolrAdaptor.class);

	@Override
	public SolrQuery doAdapt(Collection collection, Condition condition,
			SolrQuery query) {
		logger.info("Setting query to: " + condition.getQuery());
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
