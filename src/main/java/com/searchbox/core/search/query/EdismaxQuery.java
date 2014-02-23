package com.searchbox.core.search.query;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.params.DisMaxParams;

import com.searchbox.anno.SearchAdapter;
import com.searchbox.anno.SearchAdapterMethod;
import com.searchbox.anno.SearchAdapterMethod.Target;
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

@SearchAdapter(target=EdismaxQuery.class)
class SimpleQuerySolrAdaptor {

	@SearchAdapterMethod(target=Target.PRE)
	public SolrQuery setQueryFields(EdismaxQuery SearchElement,
			SolrQuery query, List<FieldAttribute> fieldAttributes) {
		
		query.setQuery(SearchElement.getQuery());
		
		query.setRequestHandler("edismax");
		query.set(DisMaxParams.ALTQ, "*:*");
		
		//fetching all searchable fields
		List<String> qfs = new ArrayList<String>();
		for(FieldAttribute fieldAttr:fieldAttributes){
			if(fieldAttr.getSearchable()){
				Float boost = (fieldAttr.getBoost()!=null)?fieldAttr.getBoost():1.0f;
				qfs.add(fieldAttr.getKey()+"^"+boost);
			}
		}
		query.set(DisMaxParams.QF, StringUtils.join(qfs," "));
		return query;
	}

	@SearchAdapterMethod(target=Target.POST)
	public EdismaxQuery udpateElementQuery(EdismaxQuery searchElement, SolrQuery query) {
		searchElement.setQuery(query.getQuery());			
		return searchElement;
	}

	@SearchAdapterMethod(target=Target.PRE)
	public SolrQuery getQueryCondition(EdismaxQuery.Condition condition, SolrQuery query) {
		query.setQuery(condition.getQuery());
		return query;
	}

}

