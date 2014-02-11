package com.searchbox.core.search.result;

import java.util.Iterator;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;

import com.searchbox.anno.SearchAdaptor;
import com.searchbox.core.adaptor.SolrElementAdapter;
import com.searchbox.core.search.result.HitList.Hit;
import com.searchbox.domain.Collection;
import com.searchbox.domain.Preset;

@SearchAdaptor
public class HitListAdapter implements SolrElementAdapter<HitList> {

	@Override
	public SolrQuery doAdapt(Collection collection, HitList searchElement,
			SolrQuery query) {
		for(String field:searchElement.getFields()){
			query.addField(field);
		}
		if(!searchElement.getFields().contains(searchElement.getTitleField())){
			query.addField(searchElement.getTitleField());
		}
		if(!searchElement.getFields().contains(searchElement.getUrlField())){
			query.addField(searchElement.getUrlField());
		}
		query.addField("score");
		return query;
	}

	@Override
	public HitList doAdapt(Preset preset, HitList element,
			SolrQuery query, QueryResponse response) {
		
		Iterator<SolrDocument> documents = response.getResults().iterator();
		while(documents.hasNext()){
			SolrDocument document = documents.next();
			Hit hit = element.newHit((Float) document.get("score"));
			for(String field:document.getFieldNames()){
				Object value = document.get(field);
				hit.addFieldValue(field, document.get(field));
			}
		}
		return element;
	}

}
