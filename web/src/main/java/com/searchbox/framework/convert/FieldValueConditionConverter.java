/*
 * Copyright 2014 gamars.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.searchbox.framework.convert;

import org.springframework.core.convert.converter.Converter;

import com.searchbox.core.search.filter.FieldValueCondition;

/**
 *
 * @author gamars
 */
/** Format of FieldValueFacet is key[value]s where s makes it stick */
@SearchConverter //(urlParam = "ff")
public class FieldValueConditionConverter implements
    Converter<String, FieldValueCondition> {
  @Override
  public FieldValueCondition convert(String source) {
    String cfield = source.split("\\[")[0];
    String cvalue = source.split("\\[")[1].split("]")[0];
    // FIXME Problem here, the facet will not be sticky if not forced...
    // :/
    return new FieldValueCondition(cfield, cvalue, true);
  }
}
