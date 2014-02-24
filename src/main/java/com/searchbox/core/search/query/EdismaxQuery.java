package com.searchbox.core.search.query;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.params.DisMaxParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.anno.PostSearchAdapter;
import com.searchbox.anno.PreSearchAdapter;
import com.searchbox.anno.SearchAdapter;
import com.searchbox.anno.SearchComponent;
import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.core.search.ConditionalSearchElement;
import com.searchbox.core.search.SearchCondition;
import com.searchbox.core.search.SearchElement;

@SearchComponent(prefix = "q", condition = EdismaxQuery.Condition.class, converter=EdismaxQuery.Converter.class)
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

	private static Logger logger = LoggerFactory
			.getLogger(EdismaxQuerySolrAdaptor.class);
	
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
		logger.info("Post query adapter. Setting query to: " + query.getQuery());
		searchElement.setQuery(query.getQuery());			
		return searchElement;
	}

	@PreSearchAdapter
	public SolrQuery getQueryCondition(EdismaxQuery.Condition condition, SolrQuery query) {
		logger.info("Pre adaptor setting query to: " + condition.getQuery());
		query.setQuery(condition.getQuery());
		return query;
	}
}

