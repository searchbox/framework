package com.searchbox.framework.core.service;

import java.lang.reflect.Method;

import org.apache.solr.client.solrj.SolrQuery;
import org.junit.Test;

import com.searchbox.core.PreSearchAdapter;
import com.searchbox.core.dm.Field;
import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.core.search.query.EdismaxQuery;
import com.searchbox.core.search.query.EdismaxQuerySolrAdaptor;
import com.searchbox.framework.service.SearchAdapterService;

public class SearchAdapterTest {

	
	public static void main(String... args){
		SearchAdapterService service = new SearchAdapterService();

	}
	
	
	@Test
	public void PreAdapterMethod(){
		
		EdismaxQuery q = new EdismaxQuery();
		
		EdismaxQuerySolrAdaptor adapter = new EdismaxQuerySolrAdaptor();
		
		SearchAdapterService service = new SearchAdapterService();
		
		for (Method method : adapter.getClass().getDeclaredMethods()) {
			if (method.isAnnotationPresent(PreSearchAdapter.class)) {
				service.addPreSearchMethod(method, adapter);
			}
		}	
		
		
		FieldAttribute fieldAttr = new FieldAttribute();
		fieldAttr.setField(Field.StringField("article-title"));
		fieldAttr.setSearchable(true);
		
		FieldAttribute fieldAttr1 = new FieldAttribute();
		fieldAttr1.setField(Field.StringField("journal-title"));
		
		FieldAttribute fieldAttr2 = new FieldAttribute();
		fieldAttr2.setField(Field.StringField("article-abstract"));
		fieldAttr2.setSearchable(true);
		
		service.doPreSearchAdapt(null, null, new SolrQuery(), fieldAttr, fieldAttr1, fieldAttr2, q);

		
	}
}
