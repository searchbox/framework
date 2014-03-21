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

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.core.dm.Preset;

@Entity
public class PresetEntity extends BeanFactoryEntity<Long> implements
    ParametrizedBeanFactory<Preset>, Comparable<PresetEntity> {

  @SuppressWarnings("unused")
  private static final Logger LOGGER = LoggerFactory
      .getLogger(PresetEntity.class);

  public static final String DEFAULT_PROCESS = "defautlt";

  @ManyToOne
  private SearchboxEntity searchbox;

  @ManyToOne
  @LazyCollection(LazyCollectionOption.FALSE)
  private PresetEntity parent;

  @OneToMany(targetEntity = PresetEntity.class, mappedBy = "parent", cascade = CascadeType.ALL)
  @LazyCollection(LazyCollectionOption.FALSE)
  Set<PresetEntity> children;

  @ManyToOne
  @LazyCollection(LazyCollectionOption.FALSE)
  private CollectionEntity<?> collection;

  @OneToMany(targetEntity = SearchElementEntity.class, mappedBy = "preset", cascade = CascadeType.ALL)
  @LazyCollection(LazyCollectionOption.FALSE)
  private Set<SearchElementEntity<?>> searchElements;

  private String slug;

  private String label;

  private String description;

  private Integer position;

  private String defaultProcess = "search";

  @OneToMany(targetEntity = FieldAttributeEntity.class, cascade = CascadeType.ALL)
  @LazyCollection(LazyCollectionOption.FALSE)
  private Set<FieldAttributeEntity> fieldAttributes;

  public PresetEntity() {
    searchElements = new TreeSet<SearchElementEntity<?>>();
    fieldAttributes = new HashSet<FieldAttributeEntity>();
  }

  // @PostLoad
  // public void postLoad() {
  // for (FieldDefinition fieldDef : collection.getFields()) {
  // if (this.getFieldAttributeByField(fieldDef) == null) {
  // this.addFieldAttribute(new FieldAttributeDefinition(fieldDef));
  // }
  // }
  // }

  public String getDefaultProcess() {
    return defaultProcess;
  }

  public void setDefaultProcess(String defaultProcess) {
    this.defaultProcess = defaultProcess;
  }

  public SearchboxEntity getSearchbox() {
    return searchbox;
  }

  public PresetEntity setSearchbox(SearchboxEntity searchbox) {
    this.searchbox = searchbox;
    return this;
  }

  public String getSlug() {
    return slug;
  }

  public PresetEntity setSlug(String slug) {
    this.slug = slug;
    return this;
  }

  public Integer getPosition() {
    return position;
  }

  public PresetEntity setPosition(Integer position) {
    this.position = position;
    return this;
  }

  public String getLabel() {
    return label;
  }

  public PresetEntity setLabel(String label) {
    this.label = label;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public PresetEntity setDescription(String description) {
    this.description = description;
    return this;
  }

  public PresetEntity getParent() {
    return parent;
  }

  public void setParent(PresetEntity parent) {
    this.parent = parent;
  }

  public Set<PresetEntity> getChildren() {
    return children;
  }

  public void setChildren(Set<PresetEntity> children) {
    this.children = children;
  }

  public CollectionEntity<?> getCollection() {
    return collection;
  }

  public PresetEntity setCollection(CollectionEntity<?> collection) {
    this.collection = collection;
    return this;
  }

  public Set<SearchElementEntity<?>> getSearchElements() {
    return searchElements;
  }

  public Set<SearchElementEntity<?>> getSearchElements(String process) {
    Set<SearchElementEntity<?>> definitions = new TreeSet<SearchElementEntity<?>>();
    for (SearchElementEntity<?> definition : this.searchElements) {
      if (definition.getProcess().equals(process)) {
        definitions.add(definition);
      }
    }
    return definitions;

  }

  public PresetEntity setSearchElements(
      Set<SearchElementEntity<?>> searchElements) {
    this.searchElements = searchElements;
    return this;
  }

  public Set<FieldAttributeEntity> getFieldAttributes() {
    return fieldAttributes;
  }

  public PresetEntity setFieldAttributes(
      Set<FieldAttributeEntity> fieldAttributes) {
    this.fieldAttributes = fieldAttributes;
    return this;
  }

  //
  // public void addFieldAttribute(FieldAttributeDefinition definition) {
  // boolean exists = false;
  // for (FieldAttributeDefinition attr : fieldAttributes) {
  // if (attr.getField().equals(definition.getField())) {
  // BeanUtils.copyProperties(definition, attr);
  // exists = true;
  // }
  // }
  // if (!exists) {
  // definition.setPreset(this);
  // this.fieldAttributes.add(definition);
  // }
  // }

  @Override
  public int compareTo(PresetEntity o) {
    return this.getPosition().compareTo(o.getPosition());
  }

  @Override
  public Preset build() {
    return super.build(Preset.class);
  }

  public FieldAttributeEntity newFieldAttribute() {
    return new FieldAttributeEntity().setPreset(this);
  }

  public PresetEntity newFieldAttribute(FieldAttributeEntity attribute) {
    this.getFieldAttributes().add(attribute);
    return this;
  }

  public SearchElementEntity<?> newSearchElement() {
    return new SearchElementEntity<>()
        .setPosition(this.getSearchElements().size()).setPreset(this)
        .setProcess(DEFAULT_PROCESS);
  }

  public PresetEntity newSearchElement(SearchElementEntity<?> searchElement) {
    this.getSearchElements().add(searchElement);
    return this;
  }

  public CollectionEntity<?> newCollection() {
    CollectionEntity<?> collection = new CollectionEntity<>();
    collection.getPresets().add(this);
    return collection;
  }

  public PresetEntity newCollection(CollectionEntity<?> collection) {
    this.setCollection(collection);
    return this;
  }

  public SearchboxEntity end() {
    this.searchbox.getPresets().add(this);
    return this.getSearchbox();
  }

  // public FieldAttributeDefinition getFieldAttributeByKey(String key) {
  // for (FieldAttributeDefinition adef : this.fieldAttributes) {
  // if (adef.getField().getKey().equals(key)) {
  // return adef;
  // }
  // }
  // return null;
  // }
  //
  // public FieldAttributeDefinition getFieldAttributeByField(FieldDefinition
  // field) {
  // for (FieldAttributeDefinition adef : this.fieldAttributes) {
  // if (adef.getField().equals(field)) {
  // return adef;
  // }
  // }
  // return null;
  // }

  // @PrePersist
  // public void checkPresetAttributes() {
  // // THis is for a SearchEngine Managed Collection!!!
  // for (FieldDefinition fdef : collection.getFields()) {
  // boolean exists = false;
  // for (FieldAttributeDefinition attr : fieldAttributes) {
  // if (attr.getField().equals(fdef)) {
  // exists = true;
  // }
  // }
  // if (!exists) {
  // this.addFieldAttribute(new FieldAttributeDefinition(fdef));
  // }
  // }
  // }
}
