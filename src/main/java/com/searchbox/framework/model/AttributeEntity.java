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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.SerializationUtils;

@Entity
//TODO Paramtrized AttributeEntity to not save the serialized...
public class AttributeEntity extends BaseEntity<Long> 
  implements Comparable<AttributeEntity> {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(AttributeEntity.class);

  private Class<?> type;

  private String name;

  @Lob
  @Column(name = "value", length = Integer.MAX_VALUE - 1)
  private byte[] valueAsByteArray;

  public AttributeEntity() {

  }
  
  public Class<?> getType() {
    return type;
  }

  public AttributeEntity setType(Class<?> type) {
    this.type = type;
    return this;
  }

  public String getName() {
    return name;
  }

  public AttributeEntity setName(String name) {
    this.name = name;
    return this;
  }

  @Transient
  public Object getValue() {
    try {
      return SerializationUtils.deserialize(valueAsByteArray);
    } catch (Exception e) {
      LOGGER.error("Could not deserialize value: " + this, e);
      return null;
    }
  }

  public AttributeEntity setValue(Object value) {
    LOGGER.info("Adding object of type {}",value.getClass());
    try {
      this.valueAsByteArray = SerializationUtils.serialize(value);
    } catch (Exception e) {
      LOGGER.error("Could not serialize value: " + this, e);
    }
    return this;
  }

  @Override
  public int compareTo(AttributeEntity o) {
    return this.getName().compareTo(o.getName());
  }
}
