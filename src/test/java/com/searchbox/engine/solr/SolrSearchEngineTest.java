package com.searchbox.engine.solr;

import java.util.Map;
import java.util.Set;

import org.apache.solr.client.solrj.SolrServer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.core.dm.Field;
import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.core.dm.FieldAttribute.USE;

public class SolrSearchEngineTest {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(SolrSearchEngineTest.class);

    private static final String FIELD_NAME_SEARCH = "title_txt";
    private static final String FIELD_NAME_DEFAULT = "title";

    private static final String FIELD_YEARS_DEFAULT = "years";
    private static final String FIELD_YEARS_SEARCH = "years_tis";

    SolrSearchEngine engine;

    FieldAttribute attr;
    FieldAttribute attr2;

    @Before
    public void setSearchEngine() {
        this.attr = new FieldAttribute();
        attr.setField(Field.stringField(FIELD_NAME_DEFAULT));
        attr.setHighlight(true);
        attr.setSearchable(true);

        this.attr2 = new FieldAttribute();
        attr2.setField(Field.intField(FIELD_YEARS_DEFAULT));
        attr2.setSearchable(true);

        this.engine = new SolrSearchEngine() {

            @Override
            protected SolrServer getSolrServer() {
                return null;
            }

            @Override
            public void reloadEngine() {
                // TODO Auto-generated method stub

            }

            @Override
            public void register() {
                // TODO Auto-generated method stub

            }

            @Override
            protected boolean updateDataModel(Map<Field, Set<String>> copyFields) {
                // TODO Auto-generated method stub
                return false;
            }
        };
    }

    @Test
    public void testKeysForAttribute() {

        Set<String> fieldNames = this.engine.getAllKeysForField(this.attr);
        for (String fieldName : fieldNames) {
            LOGGER.info("Got fieldName: " + fieldName);
        }

        Assert.assertTrue("Missing field in fieldNames from engine",
                fieldNames.size() == 2);
        Assert.assertTrue("Wrong field in fieldNames from engine",
                fieldNames.contains(FIELD_NAME_SEARCH));
    }

    @Test
    public void testKeyForAttributeByUSE() {

        String fieldName = this.engine.getKeyForField(this.attr, USE.SEARCH);
        LOGGER.info("Got fieldName: " + fieldName);

        Assert.assertTrue("Missing fieldName for USE.MATCH from engine",
                fieldName != null && !fieldName.isEmpty());
        Assert.assertTrue("Wrong fieldName for USE.MATCH from engine",
                fieldName.equals(FIELD_NAME_SEARCH));
    }

    @Test
    public void testKeyForIntegerAttributeByUSE() {

        String fieldName = this.engine.getKeyForField(this.attr2, USE.SEARCH);
        LOGGER.info("Got fieldName: " + fieldName);

        Assert.assertTrue("Missing fieldName for USE.MATCH from engine",
                fieldName != null && !fieldName.isEmpty());
        Assert.assertTrue("Wrong fieldName for USE.MATCH from engine",
                fieldName.equals(FIELD_YEARS_SEARCH));
    }

}
