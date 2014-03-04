package com.searchbox.core.search.query;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.params.DisMaxParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.core.PostSearchAdapter;
import com.searchbox.core.PreSearchAdapter;
import com.searchbox.core.SearchAdapter;
import com.searchbox.core.dm.FieldAttribute;

@SearchAdapter
public class EdismaxQuerySolrAdaptor {
	
	private static Logger logger = LoggerFactory
			.getLogger(EdismaxQuerySolrAdaptor.class);
	
	@PreSearchAdapter
	public void setDefaultQuery(SolrQuery query){
		query.setParam("defType", "edismax");
		query.setRequestHandler("edismax");
		query.set(DisMaxParams.ALTQ, "*:*");
	}
	
	
	@PreSearchAdapter
	public void setQueryFields(EdismaxQuery SearchElement,
			SolrQuery query, FieldAttribute fieldAttribute) {
		logger.info("Checking Field Attr for EdismaxQuery -- Field:" + fieldAttribute.getField().getKey());

		if(fieldAttribute.getSearchable()){
			logger.info("Found searchable field: " + fieldAttribute.getField().getKey());
			Float boost = (fieldAttribute.getBoost()!=null)?fieldAttribute.getBoost():1.0f;
			String currentFields = query.get(DisMaxParams.QF);
			query.set(DisMaxParams.QF, 
					((currentFields!=null && !currentFields.isEmpty())?currentFields+" ":"")+
							fieldAttribute.getField().getKey()+"^"+boost);
		}
	}

	@PostSearchAdapter
	public EdismaxQuery udpateElementQuery(EdismaxQuery searchElement, SolrQuery query) {
		searchElement.setQuery(query.getQuery());			
		return searchElement;
	}

	@PreSearchAdapter
	public SolrQuery getQueryCondition(EdismaxQuery.Condition condition, SolrQuery query) {
		query.setQuery(condition.getQuery());
		return query;
	}
}