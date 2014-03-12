package com.searchbox.framework.core.service;

import java.lang.reflect.Method;

import org.apache.solr.client.solrj.SolrQuery;
import org.junit.Test;

import com.searchbox.core.SearchAdapter.Time;
import com.searchbox.core.SearchAdapterMethod;
import com.searchbox.core.dm.Field;
import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.core.search.query.EdismaxQuery;
import com.searchbox.core.search.query.EdismaxQuerySolrAdaptor;
import com.searchbox.framework.service.SearchAdapterService;

public class SearchAdapterTest {

    @Test
    public void PreAdapterMethod() {

        EdismaxQuery q = new EdismaxQuery();

        EdismaxQuerySolrAdaptor adapter = new EdismaxQuerySolrAdaptor();

        SearchAdapterService service = new SearchAdapterService();

        for (Method method : adapter.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(SearchAdapterMethod.class)) {
                service.addSearchAdapterMethod(Time.PRE, method, adapter);
            }
        }

        FieldAttribute fieldAttr = new FieldAttribute();
        fieldAttr.setField(Field.stringField("article-title"));
        fieldAttr.setSearchable(true);

        FieldAttribute fieldAttr1 = new FieldAttribute();
        fieldAttr1.setField(Field.stringField("journal-title"));

        FieldAttribute fieldAttr2 = new FieldAttribute();
        fieldAttr2.setField(Field.stringField("article-abstract"));
        fieldAttr2.setSearchable(true);

        service.doAdapt(Time.PRE, null, new SolrQuery(), fieldAttr, fieldAttr1,
                fieldAttr2, q);
    }
}
