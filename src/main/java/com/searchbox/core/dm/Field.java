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

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Field implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = -7579715557196299051L;

  protected Class<?> clazz;
  /**
     */
  protected String key;

  /**
     */

  public Field() {

  }

  public Field(Class<?> clazz, String key) {
    this.clazz = clazz;
    this.key = key;
  }

  public String getKey() {
    return this.key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public Class<?> getClazz() {
    return clazz;
  }

  public void setClazz(Class<?> clazz) {
    this.clazz = clazz;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((clazz == null) ? 0 : clazz.getSimpleName().hashCode());
    result = prime * result + ((key == null) ? 0 : key.hashCode());
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Field)) {
      return false;
    }

    Field other = (Field) obj;
    if (clazz == null) {
      if (other.clazz != null) {
        return false;
      }
    } else if (!clazz.getSimpleName().equals(other.clazz.getSimpleName())) {
      return false;
    }
    if (key == null) {
      if (other.key != null) {
        return false;
      }
    } else if (!key.equals(other.key)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this,
        ToStringStyle.SHORT_PREFIX_STYLE);
  }

  public static Field stringField(String key) {
    return new Field(String.class, key);
  }

  public static Field dateField(String key) {
    return new Field(Date.class, key);
  }

  public static Field intField(String key) {
    return new Field(Integer.class, key);
  }

  public static Field floatField(String key) {
    return new Field(Float.class, key);
  }
}
