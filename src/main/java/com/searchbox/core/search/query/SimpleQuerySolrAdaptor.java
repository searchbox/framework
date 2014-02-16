package com.searchbox.core.search.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.params.DisMaxParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.anno.SearchAdaptor;
import com.searchbox.core.adaptor.SolrConditionAdapter;
import com.searchbox.core.adaptor.SolrElementAdapter;
import com.searchbox.core.dm.Preset;
import com.searchbox.core.dm.PresetFieldAttribute;
import com.searchbox.core.search.query.SimpleQuery.Condition;

@SearchAdaptor
public class SimpleQuerySolrAdaptor implements SolrConditionAdapter<SimpleQuery.Condition>,
	SolrElementAdapter<SimpleQuery> {

	private static Logger logger = LoggerFactory.getLogger(SimpleQuerySolrAdaptor.class);
	@Override
	public SolrQuery doAdapt(Preset preset, SimpleQuery SearchElement,
			SolrQuery query) {
		if(SearchElement.getQuery() == null || SearchElement.getQuery().isEmpty()){
			query.setQuery("*:*");
		} else {
			query.setQuery(SearchElement.getQuery());
		}
		query.setRequestHandler("edismax");
		query.set(DisMaxParams.ALTQ, "*:*");
		
		//fetching all searchable fields
		List<String> qfs = new ArrayList<String>();
		for(PresetFieldAttribute fieldAttr:preset.getFieldAttributes()){
			if(fieldAttr.getSearchable()){
				Float boost = (fieldAttr.getBoost()!=null)?fieldAttr.getBoost():1.0f;
				qfs.add(fieldAttr.getField().getKey()+"^"+boost);
			}
		}
		query.set(DisMaxParams.QF, StringUtils.join(qfs," "));
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
	public SolrQuery doAdapt(Preset preset, Condition condition,
			SolrQuery query) {
		if(condition.getQuery() == null || condition.getQuery().isEmpty()){
			query.setQuery("*:*");
		} else {
			query.setQuery(condition.getQuery());
		}
		return query;
	}

}
