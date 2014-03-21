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
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.core.SearchElement;
import com.searchbox.core.SearchElement.Type;
import com.searchbox.core.SearchElementBean;

@Entity
public class SearchElementEntity<K extends SearchElementBean> extends
    BeanFactoryEntity<Long> implements ParametrizedBeanFactory<K>,
    Comparable<SearchElementEntity<K>> {
  
  private static final Logger LOGGER = LoggerFactory
      .getLogger(SearchElementEntity.class);

  @ManyToOne(targetEntity = PresetEntity.class)
  private PresetEntity preset;

  private Class<?> clazz;

  private String label;

  private Integer position;

  private String process;

  public SearchElementEntity() {
    // TODO infer class from generic Interface
  }
  
  public PresetEntity getPreset() {
    return preset;
  }

  public SearchElementEntity<?> setPreset(PresetEntity preset) {
    this.preset = preset;
    return this;
  }

  public PresetEntity end(){
    this.getPreset().getSearchElements().add(this);
    return this.getPreset();
  }
  
  @Override
  @SuppressWarnings("unchecked")
  public K build(){
    if(this.getClazz() == null){
      throw new MissingClassAttributeException();
    }
    LOGGER.debug("Building Class for {}",this.getClazz());
    return (K) super.build(this.getClazz());
  }

  public String getProcess() {
    return process;
  }

  public SearchElementEntity<?> setProcess(String process) {
    this.process = process;
    return this;
  }

  public Class<?> getClazz() {
    return clazz;
  }

  public SearchElementEntity<?> setClazz(Class<?> clazz) {
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

//  public SearchElement.Type getType() {
//    return (Type) super.getAttributeByName("type");
//  }
//
//  public SearchElementEntity<K> setType(SearchElement.Type type) {
//    this.setAttribute("type", type);
//    return this;
//  }

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
