package com.searchbox.core.search.result;

import java.util.Iterator;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;

import com.searchbox.anno.SearchAdaptor;
import com.searchbox.anno.SearchComponent;
import com.searchbox.core.adaptor.SolrElementAdapter;
import com.searchbox.core.dm.Preset;
import com.searchbox.core.search.result.HitList.Hit;

@SearchComponent
public class TemplatedHitList extends HitList {

	private String template;
	
	public String getTemplate(){
		return this.template;
	}
	
}

@SearchAdaptor
class TemplatedHitListAdapter implements SolrElementAdapter<TemplatedHitList> {

	@Override
	public SolrQuery doAdapt(Preset preset, TemplatedHitList searchElement,
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
		if(!searchElement.getFields().contains(searchElement.getIdField())){
			query.addField(searchElement.getIdField());
		}
		query.addField("score");
		return query;
	}

	@Override
	public TemplatedHitList doAdapt(Preset preset, TemplatedHitList element,
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