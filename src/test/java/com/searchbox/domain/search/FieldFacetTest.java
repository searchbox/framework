package com.searchbox.domain.search;

import org.junit.Assert;
import org.junit.Test;

import com.searchbox.core.search.facet.FieldFacet;
import com.searchbox.core.search.facet.FieldFacet.Converter;

public class FieldFacetTest {
	
	public static final String FIELD_NAME =  "field";
	public static final String FIELD_VALUE =  "value";
	public static final String URL_PARAM =  "field[value]";
	
	@Test
	public void testConverter(){
		
		Converter vv = new FieldFacet.Converter();
		FieldFacet.ValueCondition vc = vv.convert(URL_PARAM);
		Assert.assertEquals("FieldName value", FIELD_NAME, vc.getFieldName());
		Assert.assertEquals("Value value", FIELD_VALUE, vc.getValue());
	}

}
