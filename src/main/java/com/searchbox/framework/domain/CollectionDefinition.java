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

import java.lang.reflect.InvocationTargetException;
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

import com.searchbox.core.dm.Collection;
import com.searchbox.core.dm.Field;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class CollectionDefinition extends UnknownClassDefinition implements
		ElementFactory<Collection> {

	@ManyToOne
	private SearchEngineDefinition searchEngine;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<FieldDefinition> fieldDefinitions = new HashSet<FieldDefinition>();

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
					if(!this.fieldDefinitions.contains(fieldDef)){
						this.fieldDefinitions.add(fieldDef);
					}
				}
			}
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

	public Set<FieldDefinition> getFieldDefinitions() {
		return fieldDefinitions;
	}

	public void setFieldDefinitions(Set<FieldDefinition> fieldDefinitions) {
		this.fieldDefinitions = fieldDefinitions;
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
		for (FieldDefinition def : this.fieldDefinitions) {
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
		for (FieldDefinition fieldDef : this.fieldDefinitions) {
			collection.getFields().add(
					new Field(fieldDef.getClazz(), fieldDef.getKey()));
		}
		collection.setSearchEngine(searchEngine.getInstance());
		return collection;
	}
}
