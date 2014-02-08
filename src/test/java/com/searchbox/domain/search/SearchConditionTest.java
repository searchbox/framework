package com.searchbox.domain.search;


import org.junit.Test;
import org.junit.Assert;

import com.searchbox.core.search.facet.FieldFacet;
import com.searchbox.core.search.query.SimpleQuery;

public class SearchConditionTest {
	
	
	@Test
	public void testConditionEquality(){
		FieldFacet.ValueCondition c1 = new FieldFacet.ValueCondition("1","1");
		FieldFacet.ValueCondition c2 = new FieldFacet.ValueCondition("1","1");
		FieldFacet.ValueCondition c3 = new FieldFacet.ValueCondition("1","3");
		
		SimpleQuery.Condition qc1 = new SimpleQuery("Hello world").getSearchCondition();
		SimpleQuery.Condition qc2 = new SimpleQuery("Hello world").getSearchCondition();
		SimpleQuery.Condition qc3 = new SimpleQuery("Hello").getSearchCondition();

		Assert.assertTrue("FieldFacet.ValueCondition equals itself", c1.equals(c1));
		Assert.assertTrue("FieldFacet.ValueCondition same values", c1.equals(c2));
		Assert.assertFalse("FieldFacet.ValueCondition different values", c3.equals(c1));
		Assert.assertFalse("FieldFacet.ValueCondition different values reversed", c1.equals(c3));
		Assert.assertFalse("FieldFacet.ValueCondition vs  SimpleQuery.Condition", c1.equals(qc1));
		Assert.assertTrue("SimpleQuery.Condition equals itsef", qc1.equals(qc1));
		Assert.assertTrue("SimpleQuery.Condition same values", qc1.equals(qc2));
		Assert.assertFalse("SimpleQuery.Condition different values", qc1.equals(qc3));
	}

}
