package com.searchbox.core.search.facet;

import com.searchbox.anno.SearchAdaptor;
import com.searchbox.core.adaptor.SearchConditionAdapter;
import com.searchbox.core.adaptor.SearchElementAdapter;
import com.searchbox.core.engine.SolrQuery;
import com.searchbox.core.engine.SolrResponse;
import com.searchbox.core.search.facet.FieldFacet.ValueCondition;
import com.searchbox.domain.Collection;
import com.searchbox.domain.Preset;

@SearchAdaptor
public class FieldFacetSolrAdaptor 
	implements SearchElementAdapter<FieldFacet, SolrQuery, SolrResponse>,
	SearchConditionAdapter<FieldFacet.ValueCondition, SolrQuery> {
	
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

	@Override
	public FieldFacet doAdapt(Preset preset, FieldFacet searchElement,
			SolrQuery query, SolrResponse response) {
		// TODO Generate all FacetValues from response.
		return searchElement;
	}
}