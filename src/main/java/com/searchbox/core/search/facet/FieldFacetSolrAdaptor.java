package com.searchbox.core.search.facet;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.FacetParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.core.SearchAdapter;
import com.searchbox.core.SearchAdapter.Time;
import com.searchbox.core.SearchAdapterMethod;
import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.engine.solr.SolrSearchEngine;

@SearchAdapter
public class FieldFacetSolrAdaptor {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(FieldFacetSolrAdaptor.class);

	@SearchAdapterMethod(execute=Time.PRE)
	public void addFacetField(SolrSearchEngine engine, FieldFacet facet,
			SolrQuery query, FieldAttribute attribute) {
		
		if(!attribute.getField().getKey().equals(facet.getFieldName())){
			return;
		}
		
		String facetKey = engine.getKeyForField(attribute);
		
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
	}

	@SearchAdapterMethod(execute=Time.POST)
	public void getFacetValues(SolrSearchEngine engine, FieldFacet fieldFacet,
			QueryResponse response, FieldAttribute attribute) {
		
		if(!attribute.getField().getKey().equals(fieldFacet.getFieldName())){
			return;
		}
		
		if(fieldFacet.getField() == null){
			LOGGER.error("FieldFacet \""+fieldFacet.getLabel() +"\" has no field!!!");
			return;
		}
		
		String facetKey = engine.getKeyForField(attribute);
		
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
	}
}