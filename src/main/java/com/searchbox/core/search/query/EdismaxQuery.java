package com.searchbox.core.search.query;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.params.DisMaxParams;

import com.searchbox.core.PostSearchAdapter;
import com.searchbox.core.PreSearchAdapter;
import com.searchbox.core.SearchAdapter;
import com.searchbox.core.SearchComponent;
import com.searchbox.core.SearchConverter;
import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.core.search.ConditionalSearchElement;
import com.searchbox.core.search.SearchCondition;
import com.searchbox.core.search.SearchElement;

@SearchComponent(urlParam="q")
public class EdismaxQuery extends ConditionalSearchElement<EdismaxQuery.Condition> {
	
	private String query;

	public EdismaxQuery() {
		super("query component",SearchElement.Type.QUERY);
	}
	
	public EdismaxQuery(String query) {
		super("query component",SearchElement.Type.QUERY);
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
	
	@SearchConverter
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

@SearchAdapter
class EdismaxQuerySolrAdaptor {
	
	@PreSearchAdapter
	public void setDefaultQuery(SolrQuery query){
		query.setRequestHandler("edismax");
		query.set(DisMaxParams.ALTQ, "*:*");
	}
	
	
	@PreSearchAdapter
	public void setQueryFields(EdismaxQuery SearchElement,
			SolrQuery query, FieldAttribute fieldAttribute) {
	
		if(fieldAttribute.getSearchable()){
			Float boost = (fieldAttribute.getBoost()!=null)?fieldAttribute.getBoost():1.0f;
			String currentFields = query.get(DisMaxParams.QF);
			query.set(DisMaxParams.QF, 
					((currentFields!=null && !currentFields.isEmpty())?currentFields+" ":"")+
							fieldAttribute.getKey()+"^"+boost);
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

