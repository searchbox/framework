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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import com.searchbox.core.dm.Field;
import com.searchbox.core.search.AbstractSearchCondition;
import com.searchbox.core.search.facet.FieldFacet;
import com.searchbox.core.search.filter.FieldValueCondition;

public class FieldValueConditionTest {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(FieldValueConditionTest.class);

  public static final String FIELD_NAME = "field";
  public static final String FIELD_VALUE = "value";
  public static final String URL_PARAM = "field[value]s";

  @Test
  public void testConverter() {

    Converter<String, FieldValueCondition> vv = new FieldValueCondition.FieldValueConditionConverter();
    FieldValueCondition vc = vv.convert(URL_PARAM);
    Assert.assertEquals("FieldName value", FIELD_NAME, vc.getFieldName());
    Assert.assertEquals("Value value", FIELD_VALUE, vc.getValue());
  }

  @Test
  public void testLoopConverter() {
    FieldFacet facet = new FieldFacet("test Facet", Field.stringField("athor"));

    FieldFacet.Value value = facet.new Value("Stephane", "stephane", 3);

    FieldValueCondition orig = new FieldValueCondition("athor", "stephane",
        facet.getSticky());

    AbstractSearchCondition condition = value.getSearchCondition();

    Assert.assertEquals("Original and from Facet", orig, condition);

    // TODO Fix fails because of tag issue in Adapter...
    Converter<String, FieldValueCondition> vv = new FieldValueCondition.FieldValueConditionConverter();
    FieldValueCondition vc = vv.convert(value.geParamValue());
    LOGGER.info("ORIG:\t" + orig);
    LOGGER.info("URL: \t" + vc);
    Assert.assertEquals("Transformed and original", vc, orig);
  }

}
