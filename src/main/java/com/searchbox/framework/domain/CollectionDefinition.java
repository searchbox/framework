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

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.core.dm.Collection;
import com.searchbox.core.dm.Field;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class CollectionDefinition extends UnknownClassDefinition implements
		ElementFactory<Collection> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CollectionDefinition.class);

	@ManyToOne
	private SearchEngineDefinition searchEngine;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<FieldDefinition> fields = new HashSet<FieldDefinition>();
	
	@OneToMany(mappedBy="collection")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<PresetDefinition> presets = new HashSet<PresetDefinition>();

	protected String name;

	protected Boolean autoStart = false;

	public CollectionDefinition() {
		super();
	}

	@SuppressWarnings("unchecked")
	public CollectionDefinition(Class<?> clazz, String name) {
		super(clazz);
		this.name = name;
		try {
			Method method = clazz.getMethod("GET_FIELDS");
			if (method != null) {
				List<Field> fields = (List<Field>)method.invoke(null);
				for(Field field:fields){
					FieldDefinition fieldDef = new FieldDefinition(field.getClazz(), field.getKey());
					if(!this.fields.contains(fieldDef)){
						this.fields.add(fieldDef);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.warn("Could not use GET_FIELD method on collection: " + name,e);
		}

	}

	public Set<PresetDefinition> getPresets() {
		return presets;
	}

	public void setPresets(Set<PresetDefinition> presets) {
		this.presets = presets;
	}

	public SearchEngineDefinition getSearchEngine() {
		return searchEngine;
	}

	public void setSearchEngine(SearchEngineDefinition searchEngine) {
		this.searchEngine = searchEngine;
	}

	public SearchEngineDefinition getSearchEngineDefinition() {
		return searchEngine;
	}

	public void setSearchEngineDefinition(SearchEngineDefinition engine) {
		this.searchEngine = engine;
	}

	public Set<FieldDefinition> getFields() {
		return fields;
	}

	public void setFields(Set<FieldDefinition> fieldDefinitions) {
		this.fields = fieldDefinitions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getAutoStart() {
		return autoStart;
	}

	public void setAutoStart(Boolean autoStart) {
		this.autoStart = autoStart;
	}

	public FieldDefinition getFieldDefinition(String key) {
		for (FieldDefinition def : this.fields) {
			if (def.getKey().equals(key)) {
				return def;
			}
		}
		return null;
	}

	@Override
	public Collection getInstance() {
		Collection collection = (Collection) super.toObject();
		collection.setName(this.getName());
		for (FieldDefinition fieldDef : this.fields) {
			collection.getFields().add(
					new Field(fieldDef.getClazz(), fieldDef.getKey()));
		}
		collection.setSearchEngine(searchEngine.getInstance());
		return collection;
	}
}
