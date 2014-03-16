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
package com.searchbox.core.search;

import com.searchbox.core.SearchCondition;

public abstract class ConditionalSearchElementWithValues<K extends ValueElement, T extends AbstractSearchCondition>
    extends SearchElementWithValues<K> implements GenerateSearchCondition<T>,
    SearchConditionToElementMerger {

  public ConditionalSearchElementWithValues() {
    super(null, SearchElement.Type.UNKNOWN);
  }

  public ConditionalSearchElementWithValues(String label,
      SearchElement.Type type) {
    super(label, type);
  }

  @Override
  public abstract T getSearchCondition();

  @Override
  public abstract void mergeSearchCondition(AbstractSearchCondition condition);

  @Override
  public abstract Class<?> getConditionClass();

  public String getUrlParam() {
    Class<?> clazz = this.getConditionClass();
    if (clazz.isAnnotationPresent(SearchCondition.class)) {
      return clazz.getAnnotation(SearchCondition.class).urlParam();
    } else {
      return "missingAnnotationOnSearchConditionClass";
    }
  }
}
