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

import com.searchbox.core.dm.FieldAttribute;

@Entity
public class FieldAttributeEntity  extends BeanFactoryEntity<Long> 
implements ParametrizedBeanFactory<FieldAttribute>,
Comparable<FieldAttributeEntity> {

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

  public void setPreset(PresetEntity preset) {
    this.preset = preset;
  }

  public FieldEntity getField() {
    return field;
  }

  public void setField(FieldEntity field) {
    this.field = field;
  }

  @Override
  public int compareTo(FieldAttributeEntity o) {
    return this.getField().getKey()
        .compareTo(o.getField().getKey());
  }

  @Override
  public FieldAttribute build() {
    return super.build(FieldAttribute.class);
  }
}
