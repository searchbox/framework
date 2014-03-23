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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.SortNatural;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.core.dm.Preset;
import com.searchbox.core.ref.Order;
import com.searchbox.core.ref.Sort;
import com.searchbox.core.search.debug.SolrToString;
import com.searchbox.core.search.facet.FieldFacet;
import com.searchbox.core.search.paging.BasicPagination;
import com.searchbox.core.search.query.EdismaxQuery;
import com.searchbox.core.search.result.TemplateElement;
import com.searchbox.core.search.stat.BasicSearchStats;

@Entity
public class PresetEntity extends BeanFactoryEntity<Long> implements
    ParametrizedBeanFactory<Preset>, Comparable<PresetEntity> {

  @SuppressWarnings("unused")
  private static final Logger LOGGER = LoggerFactory
      .getLogger(PresetEntity.class);

  public static final String DEFAULT_PROCESS = "defautlt";

  @ManyToOne
  private SearchboxEntity searchbox;

  @ManyToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @LazyCollection(LazyCollectionOption.FALSE)
  private PresetEntity parent;

  @OneToMany(targetEntity = PresetEntity.class, mappedBy = "parent", cascade = CascadeType.ALL)
  @LazyCollection(LazyCollectionOption.FALSE)
  @SortNatural
  private SortedSet<PresetEntity> children;

  @ManyToOne(cascade={CascadeType.MERGE, CascadeType.REFRESH})
  @LazyCollection(LazyCollectionOption.FALSE)
  private CollectionEntity<?> collection;

  @OneToMany(targetEntity = SearchElementEntity.class, mappedBy = "preset", cascade = CascadeType.ALL)
  @LazyCollection(LazyCollectionOption.FALSE)
  private Set<SearchElementEntity<?>> searchElements;
  
  @ElementCollection(fetch=FetchType.EAGER)
  private List<Class<?>> inheritedTypes;
  
  private Boolean inheritFieldAttributes;

  private String slug;

  private String label;

  private String description;

  private Integer position;

  private String defaultProcess = "search";

  @OneToMany(targetEntity = FieldAttributeEntity.class, cascade = CascadeType.ALL)
  @LazyCollection(LazyCollectionOption.FALSE)
  private Set<FieldAttributeEntity> fieldAttributes;

  public PresetEntity() {
    this.searchElements = new TreeSet<>();
    this.fieldAttributes = new HashSet<>();
    this.children = new TreeSet<>();
    this.inheritedTypes = new ArrayList<>();
  }
  

  // @PostLoad
  // public void postLoad() {
  // for (FieldDefinition fieldDef : collection.getFields()) {
  // if (this.getFieldAttributeByField(fieldDef) == null) {
  // this.addFieldAttribute(new FieldAttributeDefinition(fieldDef));
  // }
  // }
  // }

  public List<Class<?>> getInheritedTypes() {
    return inheritedTypes;
  }

  public PresetEntity setInheritedTypes(List<Class<?>> inheritedTypes) {
    this.inheritedTypes = inheritedTypes;
    return this;
  }

  public Boolean getInheritFieldAttributes() {
    return inheritFieldAttributes;
  }

  public PresetEntity setInheritFieldAttributes(Boolean inheritFieldAttributes) {
    this.inheritFieldAttributes = inheritFieldAttributes;
    return this;
  }


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

  public PresetEntity setParent(PresetEntity parent) {
    this.parent = parent;
    return this;
  }

  public SortedSet<PresetEntity> getChildren() {
    return children;
  }

  public void setChildren(SortedSet<PresetEntity> children) {
    this.children = children;
  }

  public CollectionEntity<?> getCollection() {
    return collection;
  }

  public PresetEntity setCollection(CollectionEntity<?> collection) {
    this.collection = collection;
    collection.getPresets().add(this);
    // TODO might not want to do this when preset has an ID (existing preset)
    for (FieldEntity field : collection.getFields()) {
      if (getFieldAttributeByField(field) == null) {
        FieldAttributeEntity fieldAttributeEntity = new FieldAttributeEntity()
            .setField(field).setPreset(this);
        this.getFieldAttributes().add(fieldAttributeEntity);
      }
    }
    return this;
  }

  public FieldAttributeEntity getFieldAttributeByKey(String key) {
    for (FieldAttributeEntity fieldAttribute : this.getFieldAttributes()) {
      if (fieldAttribute.getField().getKey().equals(key)) {
        return fieldAttribute;
      }
    }
    return null;
  }
  
  public FieldAttributeEntity getFieldAttributeByField(FieldEntity field) {
    for (FieldAttributeEntity fieldAttribute : this.getFieldAttributes()) {
      if (fieldAttribute.getField().equals(field)) {
        return fieldAttribute;
      }
    }
    return null;
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
  
//  public Set<SearchElementEntity<?>> getSearchElements(SearchElement.Type type, 
//      String process) {
//    Set<SearchElementEntity<?>> definitions = new TreeSet<SearchElementEntity<?>>();
//    for (SearchElementEntity<?> definition : this.searchElements) {
//      if (definition.getType().equals(type) &&
//          definition.getProcess().equals(process)) {
//        definitions.add(definition);
//      }
//    }
//    return definitions;
//  }

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
  
  public PresetEntity addQueryElement(){
    this.searchElements.add(newSearchElement()
        .setClazz(EdismaxQuery.class));
    return this;
  }
  
  public PresetEntity addPagingElement() {
    this.searchElements.add(newSearchElement()
        .setClazz(BasicPagination.class));
    return this;
  }
  
  
  public PresetEntity addDebugElement() {
    this.searchElements.add(newSearchElement()
        .setClazz(SolrToString.class));
    return this;    
  }

  public PresetEntity addStatElement() {
    this.searchElements.add(newSearchElement()
        .setClazz(BasicSearchStats.class));
    return this;
  }
  
  public PresetEntity addFieldFacet(String label, String key){
    this.searchElements.add(newFieldFacet(label, key));
    return this;
  }
  
  public SearchElementEntity<?> newFieldFacet(String label, String key) {
    return newSearchElement()
        .setClazz(FieldFacet.class)
        .setLabel(label)
        .setAttribute("fieldName", key)
        .setAttribute("order", Order.BY_VALUE)
        .setAttribute("sort", Sort.DESC);
  }
  
  public PresetEntity addTemplateElement(String titleFieldName, String templateFile) {
    this.searchElements.add(newTemplateElement(titleFieldName, templateFile));
    return this;
  }

  public SearchElementEntity<?> newTemplateElement(String titleFieldName, String templateFile) {
    return newSearchElement()
        .setClazz(TemplateElement.class)
        .setAttribute("titleField", titleFieldName)
        .setAttribute("idField", collection.getIdFieldName())
        .setAttribute("templateFile", templateFile);
  }

  
  public PresetEntity addSortableFieldAttribute(String label, String field) {
    this.fieldAttributes.add(newFieldAttribute(label, field).setSortable(true));
    return this;    
  }
  
  public FieldAttributeEntity newFieldAttribute(String key){
    return newFieldAttribute("", key);
  }
  
  public FieldAttributeEntity newFieldAttribute(String label, String key){
    FieldAttributeEntity attribute = this.getFieldAttributeByKey(key);
    if(attribute==null){
      LOGGER.warn("MISSING FieldAttribute for {}. Is collection properly set?",key);
      return new FieldAttributeEntity().setPreset(this)
          .setField(this.getCollection().getField(key))
          .setAttribute("label", label);
    } else {
      return attribute.setAttribute("label", label);
    }
  }

//  public PresetEntity newFieldAttribute(FieldAttributeEntity attribute) {
//    this.getFieldAttributes().add(attribute);
//    return this;
//  }

  public SearchElementEntity<?> newSearchElement() {
    return new SearchElementEntity<>()
        .setPosition(this.getSearchElements().size()).setPreset(this)
        .setProcess(DEFAULT_PROCESS);
  }

  
  public PresetEntity endChild(){
    this.searchbox.getPresets().add(this);
    this.getParent().getChildren().add(this);
    return this.getParent();
  }
  
  public SearchboxEntity end() {
    this.searchbox.getPresets().add(this);
    return this.getSearchbox();
  }

  public PresetEntity newChildPreset(boolean inheritFieldAttributes, Class<?>... inheritedTypes) {
    this.setInheritFieldAttributes(inheritFieldAttributes);
    this.setInheritedTypes(Arrays.asList(inheritedTypes));
    return new PresetEntity()
      .setParent(this)
      .setSearchbox(this.getSearchbox())
      //Position the preset per parent.
      .setPosition(this.getPosition()*10+(this.getChildren().size()+1));
  }


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
