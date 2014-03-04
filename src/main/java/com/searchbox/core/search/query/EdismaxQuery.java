/*******************************************************************************
 * Copyright Searchbox - http://www.searchbox.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.searchbox.core.search.query;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.params.DisMaxParams;

import com.searchbox.core.PostSearchAdapter;
import com.searchbox.core.PreSearchAdapter;
import com.searchbox.core.SearchAdapter;
import com.searchbox.core.SearchComponent;
import com.searchbox.core.SearchCondition;
import com.searchbox.core.SearchConverter;
import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.core.search.AbstractSearchCondition;
import com.searchbox.core.search.ConditionalSearchElement;
import com.searchbox.core.search.SearchElement;

@SearchComponent
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
	
	@SearchCondition(urlParam="q")
	public static class Condition extends AbstractSearchCondition {

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
	public void mergeSearchCondition(AbstractSearchCondition condition) {
		if(EdismaxQuery.Condition.class.equals(condition.getClass())){
			this.query = ((EdismaxQuery.Condition)condition).getQuery();
		}
	}

	@Override
	public Class<?> getConditionClass() {
		return EdismaxQuery.Condition.class;
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

