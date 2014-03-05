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
package com.searchbox.framework.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.SerializationUtils;

@Entity
public class UnknownAttributeDefinition {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UnknownAttributeDefinition.class);
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
	
	@Version
	@Column(name="OPTLOCK")
	private long version;
	
	private Class<?> type;
	
	private String name;

	@Lob
	@Column(name = "value", length = Integer.MAX_VALUE - 1)
	private byte[] valueAsByteArray;
	
	public UnknownAttributeDefinition(){
		
	}
	
	public UnknownAttributeDefinition(Class<?> type, String name) {
		this.type = type;
		this.name = name;
	}

	public UnknownAttributeDefinition(String name, Object value) {
		this.name = name;
		this.setValue(value);
		this.type = value.getClass();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Transient
	public Object getValue() {
		try {
			return SerializationUtils.deserialize(valueAsByteArray);
		} catch (Exception e){
			LOGGER.error("Could not deserialize value: " + this, e);
			return null;
		}
	}

	public void setValue(Object value) {
		try {
		this.valueAsByteArray = SerializationUtils
				.serialize((Serializable) value);
		} catch (Exception e){
			LOGGER.error("Could not serialize value: " + this, e);
		}
	}
}
