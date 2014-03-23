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

import java.util.Date;

import javax.persistence.Entity;

import com.searchbox.core.dm.Field;

@Entity
public class FieldEntity  extends BeanFactoryEntity<Long> 
implements ParametrizedBeanFactory<Field>,
Comparable<FieldEntity> {

  private Class<?> clazz;

  private String key;

  public FieldEntity() {
  }

  public FieldEntity(Class<?> clazz, String key) {
    this.clazz = clazz;
    this.key = key;
  }

  public Class<?> getClazz() {
    return clazz;
  }

  public void setClazz(Class<?> clazz) {
    this.clazz = clazz;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public static FieldEntity StringFieldDef(String key) {
    return new FieldEntity(String.class, key);
  }

  public static FieldEntity DateFieldDef(String key) {
    return new FieldEntity(Date.class, key);
  }

  public static FieldEntity IntFieldDef(String key) {
    return new FieldEntity(Integer.class, key);
  }

  public static FieldEntity FloatFieldDef(String key) {
    return new FieldEntity(Float.class, key);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((getClazz() == null) ? 0 : getClazz().getSimpleName().hashCode());
    result = prime * result + ((getKey() == null) ? 0 : getKey().hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    FieldEntity other = (FieldEntity) obj;
    if (getClazz() == null) {
      if (other.getClazz() != null)
        return false;
    } else if (!getClazz().getSimpleName().equals(
        other.getClazz().getSimpleName()))
      return false;
    if (getKey() == null) {
      if (other.getKey() != null)
        return false;
    } else if (!getKey().equals(other.getKey()))
      return false;
    return true;
  }

  @Override
  public int compareTo(FieldEntity o) {
    return this.getKey()
        .compareTo(o.getKey());
  }

  @Override
  public Field build() {
    return super.build(Field.class);
  }
}
