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

import java.util.Date;

import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@MappedSuperclass
public class Field {

	protected Class<?> clazz;
	/**
     */
	protected String key;

	/**
     */
		
	protected Boolean multivalue = false;

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

	public Boolean getMultivalue() {
        return this.multivalue;
    }

	public void setMultivalue(Boolean multivalue) {
        this.multivalue = multivalue;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
	
	public static Field StringField(String key){
		return new Field(String.class, key);
	}
	
	public static Field DateField(String key){
		return new Field(Date.class, key);
	}
	
	public static Field IntField(String key){
		return new Field(Integer.class, key);
	}
	
	public static Field FloatField(String key){
		return new Field(Float.class, key);
	}
}
