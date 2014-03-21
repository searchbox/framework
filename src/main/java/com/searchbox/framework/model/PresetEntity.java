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
public class PresetEntity 
  extends BeanFactoryEntity<Long> 
  implements ParametrizedBeanFactory<Preset>,
  Comparable<PresetEntity> {

  @SuppressWarnings("unused")
  private static final Logger LOGGER = LoggerFactory
      .getLogger(PresetEntity.class);

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

//  @PostLoad
//  public void postLoad() {
//    for (FieldDefinition fieldDef : collection.getFields()) {
//      if (this.getFieldAttributeByField(fieldDef) == null) {
//        this.addFieldAttribute(new FieldAttributeDefinition(fieldDef));
//      }
//    }
//  }

  public String getDefaultProcess() {
    return defaultProcess;
  }

  public void setDefaultProcess(String defaultProcess) {
    this.defaultProcess = defaultProcess;
  }

  public SearchboxEntity getSearchbox() {
    return searchbox;
  }

  public void setSearchbox(SearchboxEntity searchbox) {
    this.searchbox = searchbox;
  }

  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }

  public Integer getPosition() {
    return position;
  }

  public void setPosition(Integer position) {
    this.position = position;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public static final String DEFAULT_PROCESS = "defautlt";
//
//  public void addSearchElement(SearchElementEntity definition) {
//    this.addSearchElement(definition, DEFAULT_PROCESS);
//  }
//
//  public void addSearchElement(SearchElementEntity definition,
//      String process) {
//    definition.setPreset(this);
//    definition.setPosition(this.searchElements.size() + 1);
//    definition.setProcess(process);
//    this.searchElements.add(definition);
//    // definition.setProcess(process);
//    // if(!this.searchElements.containsKey(process)){
//    // this.searchElements.put(process, new TreeSet<SearchElementDefinition>());
//    // }
//    // this.searchElements.get(process).add(definition);
//  }
//
//  public void addFieldAttribute(FieldAttributeDefinition definition) {
//    boolean exists = false;
//    for (FieldAttributeDefinition attr : fieldAttributes) {
//      if (attr.getField().equals(definition.getField())) {
//        BeanUtils.copyProperties(definition, attr);
//        exists = true;
//      }
//    }
//    if (!exists) {
//      definition.setPreset(this);
//      this.fieldAttributes.add(definition);
//    }
//  }

  @Override
  public int compareTo(PresetEntity o) {
    return this.getPosition().compareTo(
        o.getPosition());
  }

  @Override
  public Preset build() {
    return super.build(Preset.class);
  }

//  public FieldAttributeDefinition getFieldAttributeByKey(String key) {
//    for (FieldAttributeDefinition adef : this.fieldAttributes) {
//      if (adef.getField().getKey().equals(key)) {
//        return adef;
//      }
//    }
//    return null;
//  }
//
//  public FieldAttributeDefinition getFieldAttributeByField(FieldDefinition field) {
//    for (FieldAttributeDefinition adef : this.fieldAttributes) {
//      if (adef.getField().equals(field)) {
//        return adef;
//      }
//    }
//    return null;
//  }

//  @PrePersist
//  public void checkPresetAttributes() {
//    // THis is for a SearchEngine Managed Collection!!!
//    for (FieldDefinition fdef : collection.getFields()) {
//      boolean exists = false;
//      for (FieldAttributeDefinition attr : fieldAttributes) {
//        if (attr.getField().equals(fdef)) {
//          exists = true;
//        }
//      }
//      if (!exists) {
//        this.addFieldAttribute(new FieldAttributeDefinition(fdef));
//      }
//    }
//  }


//  public Set<SearchElementEntity> getSearchElements(String process) {
//    Set<SearchElementEntity> definitions = new TreeSet<SearchElementEntity>();
//    for (SearchElementEntity definition : this.searchElements) {
//      if (definition.getProcess().equals(process)) {
//        definitions.add(definition);
//      }
//    }
//    return definitions;
//
//  }
}
