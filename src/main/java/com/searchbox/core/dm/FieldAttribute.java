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
package com.searchbox.core.dm;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.searchbox.core.SearchAttribute;

public class FieldAttribute {

    public enum USE {
        MATCH, SEARCH, VALUE, TF, SORT, SPELL, MULTILANG, SUGGEST, DEFAULT
    }

    protected Field field;

    @SearchAttribute
    protected String label = "";

    @SearchAttribute
    protected Boolean searchable = false;

    @SearchAttribute
    List<String> lang = new ArrayList<String>();

    @SearchAttribute
    protected Boolean highlight = false;

    @SearchAttribute
    protected Boolean sortable = false;

    @SearchAttribute
    protected Boolean spelling = false;

    @SearchAttribute
    protected Boolean suggestion = false;

    @SearchAttribute("1f")
    protected Float boost = 1f;

    public FieldAttribute() {

    }

    public FieldAttribute(Field field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this,
                ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getSearchable() {
        return this.searchable;
    }

    public void setSearchable(Boolean searchable) {
        this.searchable = searchable;
    }

    public Boolean getHighlight() {
        return this.highlight;
    }

    public void setHighlight(Boolean highlight) {
        this.highlight = highlight;
    }

    public Boolean getSortable() {
        return this.sortable;
    }

    public void setSortable(Boolean sortable) {
        this.sortable = sortable;
    }

    public Boolean getSpelling() {
        return this.spelling;
    }

    public void setSpelling(Boolean spelling) {
        this.spelling = spelling;
    }

    public Boolean getSuggestion() {
        return this.suggestion;
    }

    public void setSuggestion(Boolean suggestion) {
        this.suggestion = suggestion;
    }

    public Float getBoost() {
        return this.boost;
    }

    public void setBoost(Float boost) {
        this.boost = boost;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Field getField() {
        return this.field;
    }

    /**
     * @return the lang
     */
    public List<String> getLang() {
        return lang;
    }

    /**
     * @param lang
     *            the lang to set
     */
    public void setLang(List<String> lang) {
        this.lang = lang;
    }
}
