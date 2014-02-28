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
package com.searchbox.domain.search;

import org.junit.Assert;
import org.junit.Test;

import com.searchbox.core.search.facet.FieldFacet;
import com.searchbox.core.search.facet.FieldFacet.Converter;
import com.searchbox.core.search.filter.FieldValueCondition;

public class FieldFacetTest {
	
	public static final String FIELD_NAME =  "field";
	public static final String FIELD_VALUE =  "value";
	public static final String URL_PARAM =  "field[value]";
	
	@Test
	public void testConverter(){
		
		Converter vv = new FieldFacet.Converter();
		FieldValueCondition vc = vv.convert(URL_PARAM);
		Assert.assertEquals("FieldName value", FIELD_NAME, vc.getFieldName());
		Assert.assertEquals("Value value", FIELD_VALUE, vc.getValue());
	}

}
