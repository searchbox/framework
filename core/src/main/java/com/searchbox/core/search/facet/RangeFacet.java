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
package com.searchbox.core.search.facet;

import com.searchbox.core.SearchComponent;
import com.searchbox.core.SearchCondition;
import com.searchbox.core.SearchElement;
import com.searchbox.core.search.AbstractSearchCondition;
import com.searchbox.core.search.ConditionalSearchElementWithValues;
import com.searchbox.core.search.ValueElement;

@SearchComponent
public class RangeFacet extends
    ConditionalSearchElementWithValues<RangeFacet.Value, RangeFacet.Condition> {

  private final String fieldName;
  private final String lowerElement = null;
  private final String upperElement = null;

  public RangeFacet(String label, String fieldName) {
    super(label, SearchElement.Type.FACET);
    this.fieldName = fieldName;
  }

  public RangeFacet addValueElement(String label, Integer count) {
    return this.addValueElement(label, label, count);
  }

  public RangeFacet addValueElement(String label, String value, Integer count) {
    this.addValueElement(new RangeFacet.Value(label, value, count));
    return this;
  }

  @Override
  public RangeFacet.Condition getSearchCondition() {
    return new RangeFacet.Condition(this.fieldName, this.lowerElement,
        this.upperElement);
  }

  public class Value extends ValueElement {

    /**
		 * 
		 */
    private static final long serialVersionUID = 9161435550332928573L;

    private String value;
    private Integer count;

    public Value(String label, String value, Integer count) {
      super(label);
      this.setValue(value);
      this.count = count;
    }

    public Value(String label, String value) {
      super(label);
      this.setValue(value);
    }

    public Integer getCount() {
      return this.count;
    }

    @Override
    public int compareTo(ValueElement other) {
      // TODO Auto-generated method stub
      return 0;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }
  }

  @SearchCondition(urlParam = "fr")
  public class Condition extends AbstractSearchCondition {

    String fieldName;
    String lowerElement;
    String upperElement;

    Condition(String field, String from, String to) {
      this.fieldName = field;
      this.lowerElement = from;
      this.upperElement = to;
    }

    @Override
    public String getParamValue() {
      return this.fieldName + "[" + this.lowerElement + "##"
          + this.upperElement + "]";
    }
  }

  @Override
  public void mergeSearchCondition(AbstractSearchCondition condition) {
    // TODO Auto-generated method stub

  }

  @Override
  public Class<?> getConditionClass() {
    return RangeFacet.Condition.class;
  }
}
