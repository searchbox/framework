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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.core.dm.Collection;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "clazz", discriminatorType = DiscriminatorType.STRING)
public class CollectionEntity<K extends Collection>
  extends BeanFactoryEntity<Long>
  implements ParametrizedBeanFactory<K>{

  
  private static final Logger LOGGER = LoggerFactory
      .getLogger(CollectionEntity.class);
  
  private Class<?> clazz;

  private String name;

  private String description;
  
  private Boolean autoStart;
  
  @ManyToOne
  @LazyCollection(LazyCollectionOption.FALSE)
  private SearchEngineEntity<?> searchEngine;
  
  public String getName() {
    return name;
  }
  
  public CollectionEntity(){
    //TODO infer clazz thru reflection
  }

  public CollectionEntity<K> setName(String name) {
    this.name = name;
    return this;
  }  
  
  @SuppressWarnings("unchecked")
  public K build(){
    if(this.getClazz() == null){
      throw new MissingClassAttributeException();
    }
    LOGGER.info("Building Class for {}",this.getClazz());
    return (K) super.build(this.getClazz());
  }

  public Class<?> getClazz() {
    return clazz;
  }

  public CollectionEntity<K> setClazz(Class<?> clazz) {
    this.clazz = clazz;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public CollectionEntity<K> setDescription(String description) {
    this.description = description;
    return this;
  }

  public CollectionEntity<K> setAttribute(String name, Object value) {
    this.getAttributes().add(new AttributeEntity().setName(name).setValue(value));
    return this;
  }

  public Boolean getAutoStart() {
    return autoStart;
  }

  public void setAutoStart(Boolean autoStart) {
    this.autoStart = autoStart;
  }

  public SearchEngineEntity<?> getSearchEngine() {
    return searchEngine;
  }

  public void setSearchEngine(SearchEngineEntity<?> searchEngine) {
    this.searchEngine = searchEngine;
  }
  
//  public static class CollectionBuilder {
//    
//    private CollectionBuilder(Class<?> clazz){
//      
//    }
//    
//    public static CollectionBuilder create(Class<?> clazz){
//      return new CollectionBuilder(clazz);
//    }
//    
//    public CollectionBuilder addParam(String name, Object value){
//      return this;
//    }
//
//    public CollectionBuilder setSearchEngine(Object object) {
//      return this;
//    }
//
//    public Collection build() {
//      return new Collection();
//    }
//  }

  // @ManyToOne
  // private SearchEngineDefinition searchEngine;
  //
  // @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  // @LazyCollection(LazyCollectionOption.FALSE)
  // private Set<FieldDefinition> fields = new HashSet<FieldDefinition>();
  //
  // @OneToMany(mappedBy = "collection")
  // @LazyCollection(LazyCollectionOption.FALSE)
  // private Set<PresetDefinition> presets = new HashSet<PresetDefinition>();
  //
  // protected String description;
  //
  // @Column(nullable = false)
  // protected String idFieldName;
  //
  // protected Boolean autoStart = false;
  //
  // public Collection(String name) {
  // super(name);
  // // TODO Auto-generated constructor stub
  // }
  //
  // @SuppressWarnings("unchecked")
  // public CollectionDefinition(Class<?> clazz, String name) {
  // super(clazz);
  // this.name = name;
  // try {
  // Method method = clazz.getMethod("GET_FIELDS");
  // if (method != null) {
  // List<Field> fields = (List<Field>) method.invoke(null);
  // for (Field field : fields) {
  // FieldDefinition fieldDef = new FieldDefinition(field.getClazz(),
  // field.getKey());
  // if (!this.fields.contains(fieldDef)) {
  // this.fields.add(fieldDef);
  // }
  // }
  // }
  // } catch (Exception e) {
  // LOGGER.warn("Could not use GET_FIELD method on collection: " + name, e);
  // }
  //
  // }
  //

  //
  // public Set<PresetDefinition> getPresets() {
  // return presets;
  // }
  //
  // public void setPresets(Set<PresetDefinition> presets) {
  // this.presets = presets;
  // }
  //
  // public SearchEngineDefinition getSearchEngine() {
  // return searchEngine;
  // }
  //
  // public void setSearchEngine(SearchEngineDefinition searchEngine) {
  // this.searchEngine = searchEngine;
  // }
  //
  // public SearchEngineDefinition getSearchEngineDefinition() {
  // return searchEngine;
  // }
  //
  // public void setSearchEngineDefinition(SearchEngineDefinition engine) {
  // this.searchEngine = engine;
  // }
  //
  // public Set<FieldDefinition> getFields() {
  // return fields;
  // }
  //
  // public void setFields(Set<FieldDefinition> fieldDefinitions) {
  // this.fields = fieldDefinitions;
  // }
  //
  // public String getName() {
  // return name;
  // }
  //
  // public void setName(String name) {
  // this.name = name;
  // }
  //
  // public Boolean getAutoStart() {
  // return autoStart;
  // }
  //
  // public void setAutoStart(Boolean autoStart) {
  // this.autoStart = autoStart;
  // }
  //
  // /**
  // * @return the description
  // */
  // public String getDescription() {
  // return description;
  // }
  //
  // /**
  // * @param description
  // * the description to set
  // */
  // public void setDescription(String description) {
  // this.description = description;
  // }
  //
  // /**
  // * @return the idFieldName
  // */
  // public String getIdFieldName() {
  // return idFieldName;
  // }
  //
  // /**
  // * @param idFieldName
  // * the idFieldName to set
  // */
  // public void setIdFieldName(String idFieldName) {
  // this.idFieldName = idFieldName;
  // }
  //
  // public FieldDefinition getFieldDefinition(String key) {
  // for (FieldDefinition def : this.fields) {
  // if (def.getKey().equals(key)) {
  // return def;
  // }
  // }
  // return null;
  // }
  //
  // @Override
  // public DefaultCollection getInstance() {
  // DefaultCollection collection = (DefaultCollection) super.toObject();
  // BeanUtils.copyProperties(this, collection);
  // for (FieldDefinition fieldDef : this.fields) {
  // collection.getFields().add(
  // new Field(fieldDef.getClazz(), fieldDef.getKey()));
  // }
  // collection.setSearchEngine(searchEngine.getInstance());
  // return collection;
  // }
  //
  //
}
