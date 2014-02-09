package com.searchbox.core.search.facet;

import com.searchbox.anno.SearchAdaptor;
import com.searchbox.core.adaptor.SearchConditionAdaptor;
import com.searchbox.core.adaptor.SearchElementAdaptor;
import com.searchbox.core.engine.SolrQuery;
import com.searchbox.core.search.facet.FieldFacet.ValueCondition;
import com.searchbox.domain.Collection;

@SearchAdaptor
public class FieldFacetSolrAdaptor 
	implements SearchElementAdaptor<FieldFacet, SolrQuery>,
	SearchConditionAdaptor<FieldFacet.ValueCondition, SolrQuery> {
	
	@Override
	public SolrQuery doAdapt(Collection collection,
			ValueCondition condition, SolrQuery query) {
		query.add("fq", condition.fieldName+":"+condition.value);
		return query;
	}

	@Override
	public SolrQuery doAdapt(Collection collection, FieldFacet element, SolrQuery query) {
		query.addFacetField(element.getFieldName());
		return query;
	}
}