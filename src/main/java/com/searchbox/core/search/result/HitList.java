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
package com.searchbox.core.search.result;

import java.util.ArrayList;
import java.util.List;

import com.searchbox.core.SearchAttribute;
import com.searchbox.core.SearchComponent;
import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.SearchElementWithValues;

@SearchComponent
public class HitList extends SearchElementWithValues<Hit> {

    @SearchAttribute
    protected List<String> fields;

    @SearchAttribute
    String titleField;

    @SearchAttribute
    String urlField;

    @SearchAttribute
    String idField;

    public HitList() {
        super("Result Set", SearchElement.Type.VIEW);
        this.fields = new ArrayList<String>();
    }

    public String getTitleField() {
        return titleField;
    }

    public void setTitleField(String titleField) {
        this.titleField = titleField;
    }

    public String getUrlField() {
        return urlField;
    }

    public void setUrlField(String urlField) {
        this.urlField = urlField;
    }

    public String getIdField() {
        return idField;
    }

    public void setIdField(String idField) {
        this.idField = idField;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public List<String> getFields() {
        return this.fields;
    }

    public void addHit(Hit hit) {
        this.values.add(hit);
    }

    public Hit newHit(Float score) {
        Hit hit = new Hit(score);
        hit.setIDFieldName(this.idField);
        hit.setTitleFieldName(this.titleField);
        hit.setURLFieldName(this.urlField);
        this.addHit(hit);
        return hit;
    }
}
