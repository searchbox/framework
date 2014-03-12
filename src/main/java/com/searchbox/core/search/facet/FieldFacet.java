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

import com.searchbox.core.SearchAttribute;
import com.searchbox.core.SearchComponent;
import com.searchbox.core.dm.Field;
import com.searchbox.core.ref.Order;
import com.searchbox.core.ref.Sort;
import com.searchbox.core.search.AbstractSearchCondition;
import com.searchbox.core.search.ConditionalValueElement;
import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.SearchElementWithConditionalValues;
import com.searchbox.core.search.ValueElement;
import com.searchbox.core.search.filter.FieldValueCondition;

@SearchComponent
public class FieldFacet
        extends
        SearchElementWithConditionalValues<FieldFacet.Value, FieldValueCondition> {

    @SearchAttribute
    private Field field;

    @SearchAttribute
    private Boolean sticky = true;

    public FieldFacet() {
        super("", SearchElement.Type.FACET);
    }

    public FieldFacet(String label, Field field) {
        super(label, SearchElement.Type.FACET);
        this.field = field;
    }

    public String getFieldName() {
        return field.getKey();
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Field getField() {
        return this.field;
    }

    public FieldFacet addValueElement(String label, Integer count) {
        return this.addValueElement(label, label, count);
    }

    public FieldFacet addValueElement(String label, String value, Integer count) {
        this.addValueElement(new FieldFacet.Value(label, value, count));
        return this;
    }

    public class Value extends ConditionalValueElement<FieldValueCondition> {

        /**
		 * 
		 */
        private static final long serialVersionUID = -5020007167116586645L;

        private String value;
        private Integer count;
        private Boolean selected = false;

        public Value(String label, String value, Integer count) {
            super(label);
            this.value = value;
            this.count = count;
        }

        public Value(String label, String value) {
            super(label);
            this.value = value;
        }

        public Integer getCount() {
            return this.count;
        }

        public Boolean getSelected() {
            return this.selected;
        }

        public String getValue() {
            return this.value;
        }

        public Value setSelected(Boolean selected) {
            this.selected = selected;
            return this;
        }

        @Override
        public FieldValueCondition getSearchCondition() {
            return new FieldValueCondition(field.getKey(), this.value, sticky);
        }

        @Override
        public String geParamValue() {
            return field.getKey() + "[" + this.value + "]";
        }

        @Override
        public int compareTo(ValueElement other) {
            FieldFacet.Value o = (FieldFacet.Value) other;
            int diff = 0;
            if (order.equals(Order.BY_KEY)) {
                diff = this.getLabel().compareTo(o.getLabel()) * 10;
            } else if (!this.getCount().equals(o.getCount())) {
                diff = this.getCount().compareTo(o.getCount()) * 10;
            } else {
                diff = this.getCount().compareTo(o.getCount())
                        * 10
                        + (this.getLabel().compareTo(o.getLabel()) * ((sort
                                .equals(Sort.ASC)) ? 1 : -1));
            }
            return diff * ((sort.equals(Sort.ASC)) ? 1 : -1);
        }

        @Override
        public Class<?> getConditionClass() {
            return FieldValueCondition.class;
        }
    }

    @Override
    public void mergeSearchCondition(AbstractSearchCondition condition) {
        if (FieldValueCondition.class.equals(condition.getClass())) {
            if (this.getFieldName().equals(
                    ((FieldValueCondition) condition).getFieldName())) {
                for (FieldFacet.Value value : this.getValues()) {
                    if (value.value.equals(((FieldValueCondition) condition)
                            .getValue())) {
                        value.setSelected(true);
                    }
                }
            }
        }
    }

    public boolean getSticky() {
        return this.sticky;
    }

    public void setSticky(boolean sticky) {
        this.sticky = sticky;
    }

}
