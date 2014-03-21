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
package com.searchbox.framework.model;

import javax.persistence.Entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.searchbox.core.SearchElement;

@Entity
public class SearchElementEntity<K extends SearchElement> extends
    BeanFactoryEntity<Long> implements ParametrizedBeanFactory<K>,
    Comparable<SearchElementEntity<K>> {

  // @NotNull
  // @ManyToOne(targetEntity = PresetDefinition.class)
  // private PresetDefinition preset;

  private Class<K> clazz;

  private String label;

  private Integer position;

  private SearchElement.Type type;

  private String process;

  public SearchElementEntity() {
    // TODO infer class from generic Interface
  }

  @Override
  public K build() {
    if (this.getClazz() == null) {
      throw new MissingClassAttributeException();
    }
    return (K) super.build(this.getClazz());
  }

  public String getProcess() {
    return process;
  }

  public void setProcess(String process) {
    this.process = process;
  }

  // public PresetDefinition getPreset() {
  // return preset;
  // }
  //
  // public void setPreset(PresetDefinition preset) {
  // this.preset = preset;
  // }

  public Class<K> getClazz() {
    return clazz;
  }

  public SearchElementEntity<K> setClazz(Class<K> clazz) {
    this.clazz = clazz;
    return this;
  }

  public String getLabel() {
    return label;
  }

  public SearchElementEntity<K> setLabel(String label) {
    this.label = label;
    return this;
  }

  public Integer getPosition() {
    return position;
  }

  public SearchElementEntity<K> setPosition(Integer position) {
    this.position = position;
    return this;
  }

  public SearchElement.Type getType() {
    return type;
  }

  public SearchElementEntity<K> setType(SearchElement.Type type) {
    this.type = type;
    return this;
  }

  @Override
  public int compareTo(SearchElementEntity<K> o) {
    return this.getPosition().compareTo(o.getPosition());
  }

  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this,
        ToStringStyle.SHORT_PREFIX_STYLE);
  }

  public SearchElementEntity<K> setAttribute(String name, Object value) {
    this.getAttributes().add(
        new AttributeEntity().setName(name).setValue(value));
    return this;
  }
}
