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
package com.searchbox.core.search.query;

import com.searchbox.core.SearchAttribute;
import com.searchbox.core.SearchComponent;
import com.searchbox.core.SearchCondition;
import com.searchbox.core.SearchConverter;
import com.searchbox.core.search.AbstractSearchCondition;
import com.searchbox.core.search.ConditionalSearchElement;
import com.searchbox.core.search.RetryElement;
import com.searchbox.core.search.SearchElement;

@SearchComponent
public class EdismaxQuery extends
        ConditionalSearchElement<EdismaxQuery.Condition> implements
        RetryElement {

    public enum Operator {
        OR, AND
    }

    private String query;
    private String collationQuery;
    private Long hitCount = 0l;

    @SearchAttribute
    private Operator operator = Operator.AND;

    public EdismaxQuery() {
        super("query component", SearchElement.Type.QUERY);
    }

    public EdismaxQuery(String query) {
        super("query component", SearchElement.Type.QUERY);
        this.query = query;
    }

    @Override
    public boolean shouldRetry() {
        return (hitCount == 0 && collationQuery != null);
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    public String geParamValue() {
        return query;
    }

    @Override
    public EdismaxQuery.Condition getSearchCondition() {
        return new EdismaxQuery.Condition(query);
    }

    /**
     * @return the collationQuery
     */
    public String getCollationQuery() {
        return collationQuery;
    }

    /**
     * @param collationQuery
     *            the collationQuery to set
     */
    public void setCollationQuery(String collationQuery) {
        this.collationQuery = collationQuery;
    }

    /**
     * @return the operator
     */
    public Operator getOperator() {
        return operator;
    }

    /**
     * @param operator
     *            the operator to set
     */
    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    /**
     * @param l
     *            the hitCount to set
     */
    public void setHitCount(Long l) {
        this.hitCount = l;
    }

    @SearchCondition(urlParam = "q")
    public static class Condition extends AbstractSearchCondition {

        String query;

        Condition(String query) {
            this.query = query;
        }

        public String getQuery() {
            return query;
        }
    }

    @SearchConverter
    public static class Converter
            implements
            org.springframework.core.convert.converter.Converter<String, EdismaxQuery.Condition> {

        @Override
        public EdismaxQuery.Condition convert(String source) {
            return new EdismaxQuery.Condition(source);
        }
    }

    @Override
    public void mergeSearchCondition(AbstractSearchCondition condition) {
        if (EdismaxQuery.Condition.class.equals(condition.getClass())) {
            this.query = ((EdismaxQuery.Condition) condition).getQuery();
        }
    }

    @Override
    public Class<?> getConditionClass() {
        return EdismaxQuery.Condition.class;
    }
}
