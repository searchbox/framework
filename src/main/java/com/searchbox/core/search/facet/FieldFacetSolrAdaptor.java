package com.searchbox.core.search.facet;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;

import com.searchbox.anno.SearchAdaptor;
import com.searchbox.core.adaptor.SolrConditionAdapter;
import com.searchbox.core.adaptor.SolrElementAdapter;
import com.searchbox.core.dm.Collection;
import com.searchbox.core.dm.Preset;
import com.searchbox.core.search.facet.FieldFacet.ValueCondition;

@SearchAdaptor
public class FieldFacetSolrAdaptor 
	implements SolrElementAdapter<FieldFacet>,
	SolrConditionAdapter<FieldFacet.ValueCondition> {
	
	@Override
	public SolrQuery doAdapt(Collection collection,
			ValueCondition condition, SolrQuery query) {
		String conditionValue = ClientUtils.escapeQueryChars(condition.value);
		boolean isnew = true;
		List<String> fqs = new ArrayList<String>();
		if(query.getFilterQueries() != null){
			for(String fq:query.getFilterQueries()){
				if(fq.contains("{!tag="+condition.fieldName+"}"+condition.fieldName+":")){
					isnew = false;
					fq = fq+" OR " + conditionValue;
				}
				fqs.add(fq);
			}
		}
		if(isnew){
			fqs.add("{!tag="+condition.fieldName+"}"+condition.fieldName+":"+conditionValue);			
		}
		query.setFilterQueries(fqs.toArray(new String[fqs.size()]));
		return query;
	}

	@Override
	public SolrQuery doAdapt(Collection collection, FieldFacet element, SolrQuery query) {
		query.addFacetField("{!ex="+element.getFieldName()+"}"+element.getFieldName());
		return query;
	}

	@Override
	public FieldFacet doAdapt(Preset preset, FieldFacet searchElement,
			SolrQuery query, QueryResponse response) {
		for(FacetField facet:response.getFacetFields()){
			System.out.println(facet);
			if(facet.getName().equals(searchElement.getFieldName())){
				for(Count value:facet.getValues()){
					searchElement.addValueElement(value.getName(), (int)value.getCount());
				}
			}
		}
		return searchElement;
	}
}