package com.searchbox.core.search.facet;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.FacetParams;

import com.searchbox.core.PostSearchAdapter;
import com.searchbox.core.PreSearchAdapter;
import com.searchbox.core.SearchAdapter;
import com.searchbox.engine.solr.SolrSearchEngine;

@SearchAdapter
public class FieldFacetSolrAdaptor {

	@PreSearchAdapter
	public SolrQuery addFacetField(SolrSearchEngine engine, FieldFacet facet,
			SolrQuery query) {
		
		//String facetKey = engine.getKeyForField
		boolean defined = false;
		String[] facetFields = query.getFacetFields();
		if (facetFields != null) {
			for (String facetField : query.getParams(FacetParams.FACET_FIELD)) {
				if (facetField.contains(facet.getFieldName())) {
					defined = true;
				}
			}
		}
		if (!defined) {
			if (facet.getSticky()) {
				query.addFacetField("{!ex=" + facet.getFieldName() + "}"
						+ facet.getFieldName());
			} else {
				query.addFacetField(facet.getFieldName());
			}
		}
		return query;
	}

	@PostSearchAdapter
	public FieldFacet getFacetValues(FieldFacet fieldFacet,
			QueryResponse response) {
		if (response.getFacetFields() != null) {
			for (FacetField facet : response.getFacetFields()) {
				if (facet.getName().equals(fieldFacet.getFieldName())) {
					for (Count value : facet.getValues()) {
						fieldFacet.addValueElement(value.getName(),
								(int) value.getCount());
					}
				}
			}
		}
		return fieldFacet;
	}
}