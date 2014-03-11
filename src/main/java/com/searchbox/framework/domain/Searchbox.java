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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Searchbox {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Version
    @Column(name = "OPTLOCK")
    private long version;

    /**
     */
    @Column(unique = true)
    private String slug;

    /**
     */
    private String name;

    private String alias;

    /**
     */
    private String description;

    @OneToMany(mappedBy = "searchbox", orphanRemoval = true, cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<PresetDefinition> presets = new ArrayList<PresetDefinition>();

    @OneToMany(mappedBy = "searchbox", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @MapKey(name = "user")
    private Map<User, UserRole> userRoles = new HashMap<User, UserRole>();

    public Searchbox() {
    }

    public Searchbox(String name, String description) {
        this.name = name;
        this.slug = name;
        this.description = description;
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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PresetDefinition> getPresets() {
        return presets;
    }

    public void setPresets(List<PresetDefinition> presets) {
        this.presets = presets;
    }

    public Map<User, UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Map<User, UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public void addPresetDefinition(PresetDefinition preset) {
        preset.setSearchbox(this);
        this.presets.add(preset);
    }

    public void addUserRole(UserRole userRole) {
        userRole.setSearchbox(this);
        this.userRoles.put(userRole.getUser(), userRole);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this,
                ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
