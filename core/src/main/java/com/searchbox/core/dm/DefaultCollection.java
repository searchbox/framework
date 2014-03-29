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
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.searchbox.core.SearchAttribute;
import com.searchbox.core.engine.SearchEngine;

public class DefaultCollection implements Collection, CollectionWithFields,
    SearchableCollection {

  @SearchAttribute
  protected String name;

  @SearchAttribute
  protected String description;

  protected List<Field> fields = new ArrayList<Field>();

  @SearchAttribute
  protected SearchEngine<?, ?> searchEngine;

  @SearchAttribute
  Set<Preset> presets = new TreeSet<Preset>();

  @SearchAttribute
  String idFieldName;

  public DefaultCollection() {

  }

  public DefaultCollection(String name) {
    this.name = name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.searchbox.core.dm.Collection#getName()
   */
  @Override
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.searchbox.core.dm.Collection#getDescription()
   */
  @Override
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.searchbox.core.dm.Collection#getFields()
   */
  @Override
  public List<Field> getFields() {
    return fields;
  }

  public void setFields(List<Field> fields) {
    this.fields = fields;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.searchbox.core.dm.Collection#getSearchEngine()
   */
  @Override
  public SearchEngine<?, ?> getSearchEngine() {
    return searchEngine;
  }

  public SearchableCollection setSearchEngine(SearchEngine<?, ?> searchEngine) {
    this.searchEngine = searchEngine;
    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.searchbox.core.dm.Collection#getIdFieldName()
   */
  @Override
  public String getIdFieldName() {
    return idFieldName;
  }

  /**
   * @param idFieldName
   *          the idFieldName to set
   */
  public void setIdFieldName(String idFieldName) {
    this.idFieldName = idFieldName;
  }

  public Set<Preset> getPresets() {
    return presets;
  }

  public void setPresets(Set<Preset> presets) {
    this.presets = presets;
  }

  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this,
        ToStringStyle.SHORT_PREFIX_STYLE);
  }

}
