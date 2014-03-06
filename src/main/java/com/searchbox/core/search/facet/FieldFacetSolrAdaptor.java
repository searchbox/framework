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
		
		String facetKey = engine.getKeyForField(facet.getField());
		
		boolean defined = false;
		String[] facetFields = query.getFacetFields();
		if (facetFields != null) {
			for (String facetField : query.getParams(FacetParams.FACET_FIELD)) {
				if (facetField.contains(facetKey)) {
					defined = true;
				}
			}
		}
		if (!defined) {
			if (facet.getSticky()) {
				query.addFacetField("{!ex=" + facetKey + "}"
						+ facetKey);
			} else {
				query.addFacetField(facetKey);
			}
		}
		return query;
	}

	@PostSearchAdapter
	public FieldFacet getFacetValues(SolrSearchEngine engine, FieldFacet fieldFacet,
			QueryResponse response) {
		String facetKey = engine.getKeyForField(fieldFacet.getField());
		if (response.getFacetFields() != null) {
			for (FacetField facet : response.getFacetFields()) {
				if (facet.getName().equals(facetKey)) {
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