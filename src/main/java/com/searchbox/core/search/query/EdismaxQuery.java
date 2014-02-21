package com.searchbox.core.search.query;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.DisMaxParams;

import com.searchbox.anno.SearchAdaptor;
import com.searchbox.anno.SearchComponent;
import com.searchbox.core.adaptor.SolrConditionAdapter;
import com.searchbox.core.adaptor.SolrElementAdapter;
import com.searchbox.core.dm.Preset;
import com.searchbox.core.dm.PresetFieldAttribute;
import com.searchbox.core.search.ConditionalSearchElement;
import com.searchbox.core.search.SearchCondition;
import com.searchbox.core.search.SearchElementType;

@SearchComponent(prefix = "q", condition = EdismaxQuery.Condition.class, converter=EdismaxQuery.Converter.class)
public class EdismaxQuery extends ConditionalSearchElement<EdismaxQuery.Condition> {

	private String query;

	public EdismaxQuery() {
		super("query component");
		this.setType(SearchElementType.QUERY);
	}
	
	public EdismaxQuery(String query) {
		super("query component");
		this.setType(SearchElementType.QUERY);
		this.query = query;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	@Override
	public String geParamValue() {
		return query;
	}

	@Override
	public EdismaxQuery.Condition getSearchCondition() {
		return new EdismaxQuery.Condition(query);
	}
	
	public static class Condition extends SearchCondition {

		String query;

		Condition(String query) {
			this.query = query;
		}

		public String getQuery() {
			return query;
		}
	}
	

	public static class Converter implements 
	org.springframework.core.convert.converter.Converter<String, EdismaxQuery.Condition> {
	
		@Override
		public EdismaxQuery.Condition convert(String source) {
			return new EdismaxQuery.Condition(source);
		}
	}


	@Override
	public void mergeSearchCondition(SearchCondition condition) {
		if(EdismaxQuery.Condition.class.equals(condition.getClass())){
			this.query = ((EdismaxQuery.Condition)condition).getQuery();
		}
	}
}

@SearchAdaptor
class SimpleQuerySolrAdaptor implements SolrConditionAdapter<EdismaxQuery.Condition>,
	SolrElementAdapter<EdismaxQuery> {

	@Override
	public SolrQuery doAdapt(Preset preset, EdismaxQuery SearchElement,
			SolrQuery query) {
		
		query.setQuery(SearchElement.getQuery());
		
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
	public EdismaxQuery doAdapt(Preset preset, EdismaxQuery searchElement,
			SolrQuery query, QueryResponse response) {
		searchElement.setQuery(query.getQuery());			
		return searchElement;
	}

	@Override
	public SolrQuery doAdapt(Preset preset, EdismaxQuery.Condition condition,
			SolrQuery query) {
		
		query.setQuery(condition.getQuery());
		return query;
	}

}

