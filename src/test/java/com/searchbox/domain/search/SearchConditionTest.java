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

import com.searchbox.core.dm.Field;
import com.searchbox.core.search.filter.FieldValueCondition;
import com.searchbox.core.search.query.EdismaxQuery;




public class SearchConditionTest {
	
	
	@Test
	public void testConditionEquality(){
		
		FieldValueCondition c1 = new FieldValueCondition(Field.StringField("1"),"1");
		FieldValueCondition c2 = new FieldValueCondition(Field.StringField("1"),"1");
		FieldValueCondition c3 = new FieldValueCondition(Field.StringField("1"),"3");
		
		EdismaxQuery.Condition qc1 = new EdismaxQuery("Hello world").getSearchCondition();
		EdismaxQuery.Condition qc2 = new EdismaxQuery("Hello world").getSearchCondition();
		EdismaxQuery.Condition qc3 = new EdismaxQuery("Hello").getSearchCondition();

		Assert.assertTrue("FieldValueCondition equals itself", c1.equals(c1));
		Assert.assertTrue("FieldValueCondition same values", c1.equals(c2));
		Assert.assertFalse("FieldValueCondition different values", c3.equals(c1));
		Assert.assertFalse("FieldValueCondition different values reversed", c1.equals(c3));
		Assert.assertFalse("FieldValueCondition vs  SimpleQuery.Condition", c1.equals(qc1));
		Assert.assertTrue("SimpleQuery.Condition equals itsef", qc1.equals(qc1));
		Assert.assertTrue("SimpleQuery.Condition same values", qc1.equals(qc2));
		Assert.assertFalse("SimpleQuery.Condition different values", qc1.equals(qc3));
	}

}
