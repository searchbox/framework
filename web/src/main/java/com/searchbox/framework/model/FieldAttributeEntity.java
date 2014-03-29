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

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.searchbox.core.dm.FieldAttribute;

@Entity
public class FieldAttributeEntity extends BeanFactoryEntity<Long> implements
    ParametrizedBeanFactory<FieldAttribute>, Comparable<FieldAttributeEntity> {

  @ManyToOne(targetEntity = PresetEntity.class)
  private PresetEntity preset;

  @ManyToOne(targetEntity = FieldEntity.class)
  protected FieldEntity field;

  public FieldAttributeEntity() {
  }

  public FieldAttributeEntity(FieldEntity field) {
    this.field = field;
  }

  public PresetEntity getPreset() {
    return preset;
  }

  public FieldAttributeEntity setPreset(PresetEntity preset) {
    this.preset = preset;
    return this;
  }

  public FieldEntity getField() {
    return field;
  }

  public FieldAttributeEntity setField(FieldEntity field) {
    this.field = field;
    return this;
  }

  public FieldAttributeEntity setAttribute(String name, Object value) {
    this.getAttributes().add(
        new AttributeEntity().setName(name).setValue(value)
            .setType(value.getClass()));
    return this;
  }

  public PresetEntity end() {
    this.preset.getFieldAttributes().add(this);
    return this.getPreset();
  }

  @Override
  public int compareTo(FieldAttributeEntity o) {
    return this.getField().getKey().compareTo(o.getField().getKey());
  }

  @Override
  public FieldAttribute build() {
    FieldAttribute attribute = super.build(FieldAttribute.class);
    attribute.setField(this.getField().build());
    return attribute;
  }

  public FieldAttributeEntity setSearchanble(Boolean searchable) {
    this.getAttributes().add(
        new AttributeEntity().setName("searchable").setValue(searchable));
    return this;
  }

  public FieldAttributeEntity setSuggestion(Boolean suggestion) {
    this.getAttributes().add(
        new AttributeEntity().setName("suggestion").setValue(suggestion));
    return this;
  }

  public FieldAttributeEntity setLanguages(List<String> lang) {
    this.getAttributes().add(
        new AttributeEntity().setName("lang").setValue(lang));
    return this;
  }

  public FieldAttributeEntity setSpelling(Boolean spelling) {
    this.getAttributes().add(
        new AttributeEntity().setName("spelling").setValue(spelling));
    return this;
  }

  public FieldAttributeEntity setHighlight(Boolean highlight) {
    this.getAttributes().add(
        new AttributeEntity().setName("highlight").setValue(highlight));
    return this;
  }

  public FieldAttributeEntity setSortable(Boolean sortable) {
    this.getAttributes().add(
        new AttributeEntity().setName("sortable").setValue(sortable));
    return this;
  }

  public FieldAttributeEntity setBoost(Float boost) {
    this.getAttributes().add(
        new AttributeEntity().setName("boost").setValue(boost));
    return this;
  }

  @Override
  public String toString() {
    return "FieldAttributeEntity [field=" + field + "]";
  }
}
