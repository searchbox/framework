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

import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.SortNatural;

@Entity
public class SearchboxEntity extends BaseEntity<Long> 
  implements Comparable<SearchboxEntity>{
  
  enum Privacy {
    PUBLIC("public"), 
    PRIVATE("private"),
    SOCIAL("registration");
    private final String stringValue;
    private Privacy(final String s) { stringValue = s; }
    public String toString() { return stringValue; }
  }

  @Column(unique = true)
  private String slug;
  
  private Privacy privacy= Privacy.PUBLIC;

  private String name;

  private String alias;

  private String description;

  @OneToMany(mappedBy = "searchbox",
      orphanRemoval = true,
      cascade = CascadeType.ALL,
      fetch=FetchType.LAZY)
  @LazyCollection(LazyCollectionOption.TRUE)
  @SortNatural
  private SortedSet<PresetEntity> presets;

//  @OneToMany(mappedBy = "searchbox", cascade = CascadeType.ALL)
//  @LazyCollection(LazyCollectionOption.FALSE)
//  @MapKey(name = "user")
//  private Map<UserEntity, UserRole> userRoles; 

  public SearchboxEntity() {
    this.presets = new TreeSet<>();
//    this.userRoles = new HashMap<>();
  }

  public Privacy getPrivacy() {
    return privacy;
  }

  public void setPrivacy(Privacy privacy) {
    this.privacy = privacy;
  }

  public String getSlug() {
    return slug;
  }

  public SearchboxEntity setSlug(String slug) {
    this.slug = slug;
    return this;
  }

  public String getName() {
    return name;
  }

  public SearchboxEntity setName(String name) {
    this.name = name;
    return this;
  }

  public String getAlias() {
    return alias;
  }

  public SearchboxEntity setAlias(String alias) {
    this.alias = alias;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public SearchboxEntity setDescription(String description) {
    this.description = description;
    return this;
  }

  public SortedSet<PresetEntity> getPresets() {
    return presets;
  }

  public void setPresets(SortedSet<PresetEntity> presets) {
    this.presets = presets;
  }

//  public Map<UserEntity, UserRole> getUserRoles() {
//    return userRoles;
//  }
//
//  public void setUserRoles(Map<UserEntity, UserRole> userRoles) {
//    this.userRoles = userRoles;
//  }
//
//
//  public SearchboxEntity addUserRole(UserRole userRole) {
//    userRole.setSearchbox(this);
//    this.userRoles.put(userRole.getUser(), userRole);
//    return this;
//  }
  
  @Override
  public int compareTo(SearchboxEntity o) {
    return this.getName().compareTo(o.getName());
  }
  
  @Override
  public String toString() {
    //FIXME. Do not reflection on toString (breaks Lazy INiti)
    return ReflectionToStringBuilder.toString(this,
        ToStringStyle.SHORT_PREFIX_STYLE);
  }

  public PresetEntity newPreset() {
    return new PresetEntity()
      .setSearchbox(this)
      .setPosition(this.getPresets().size()+1);
  }
}
