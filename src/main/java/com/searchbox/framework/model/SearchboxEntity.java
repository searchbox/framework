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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.searchbox.framework.domain.UserRole;

@Entity
public class SearchboxEntity extends BaseEntity<Long> 
  implements Comparable<SearchboxEntity>{

  @Column(unique = true)
  private String slug;

  private String name;

  private String alias;

  private String description;

  @OneToMany(mappedBy = "searchbox", orphanRemoval = true, cascade = CascadeType.ALL)
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<PresetEntity> presets = new ArrayList<PresetEntity>();

  @OneToMany(mappedBy = "searchbox", cascade = CascadeType.ALL)
  @LazyCollection(LazyCollectionOption.FALSE)
  @MapKey(name = "user")
  private Map<UserEntity, UserRole> userRoles = new HashMap<UserEntity, UserRole>();

  public SearchboxEntity() {
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

  public List<PresetEntity> getPresets() {
    return presets;
  }

  public void setPresets(List<PresetEntity> presets) {
    this.presets = presets;
  }

  public Map<UserEntity, UserRole> getUserRoles() {
    return userRoles;
  }

  public void setUserRoles(Map<UserEntity, UserRole> userRoles) {
    this.userRoles = userRoles;
  }


  public SearchboxEntity addUserRole(UserRole userRole) {
    userRole.setSearchbox(this);
    this.userRoles.put(userRole.getUser(), userRole);
    return this;
  }
  
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
