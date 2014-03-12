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

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.Order;

import com.searchbox.core.dm.Preset;
import com.searchbox.core.search.query.EdismaxQuery;
import com.searchbox.core.search.result.HitList;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PresetDefinition extends UnknownClassDefinition implements
        ElementFactory<Preset> {

    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory
            .getLogger(PresetDefinition.class);

    @ManyToOne
    private Searchbox searchbox;

    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    private CollectionDefinition collection;

    @OneToMany(mappedBy = "preset", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @Order
    private Set<SearchElementDefinition> searchElements;

    private String slug;

    private String label;

    private String description;

    private Integer position;

    @OneToMany(targetEntity = FieldAttributeDefinition.class, cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<FieldAttributeDefinition> fieldAttributes;

    public PresetDefinition() {
        super(Preset.class);
        searchElements = new HashSet<SearchElementDefinition>();
        fieldAttributes = new HashSet<FieldAttributeDefinition>();
    }

    public PresetDefinition(CollectionDefinition collection) {
        super(Preset.class);
        this.collection = collection;
        searchElements = new HashSet<SearchElementDefinition>();
        fieldAttributes = new HashSet<FieldAttributeDefinition>();
    }

    @PostLoad
    public void postLoad() {
        for (FieldDefinition fieldDef : collection.getFields()) {
            if (this.getFieldAttributeByField(fieldDef) == null) {
                this.addFieldAttribute(new FieldAttributeDefinition(fieldDef));
            }
        }
    }

    public Searchbox getSearchbox() {
        return searchbox;
    }

    public void setSearchbox(Searchbox searchbox) {
        this.searchbox = searchbox;
    }

    public CollectionDefinition getCollection() {
        return collection;
    }

    public void setCollection(CollectionDefinition collection) {
        this.collection = collection;
    }

    public Set<SearchElementDefinition> getSearchElements() {
        return searchElements;
    }

    public void setSearchElements(Set<SearchElementDefinition> searchElements) {
        this.searchElements = searchElements;
    }

    public Set<FieldAttributeDefinition> getFieldAttributes() {
        return fieldAttributes;
    }

    public void setFieldAttributes(Set<FieldAttributeDefinition> fieldAttributes) {
        this.fieldAttributes = fieldAttributes;
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

    public void addSearchElement(SearchElementDefinition definition) {
        definition.setPreset(this);
        definition.setPosition(this.searchElements.size() + 1);
        this.searchElements.add(definition);

    }

    public void addFieldAttribute(FieldAttributeDefinition definition) {
        boolean exists = false;
        for (FieldAttributeDefinition attr : fieldAttributes) {
            if (attr.getField().equals(definition.getField())) {
                BeanUtils.copyProperties(definition, attr);
                exists = true;
            }
        }
        if (!exists) {
            definition.setPreset(this);
            this.fieldAttributes.add(definition);
        }
    }

    public FieldAttributeDefinition getFieldAttributeByKey(String key) {
        for (FieldAttributeDefinition adef : this.fieldAttributes) {
            if (adef.getField().getKey().equals(key)) {
                return adef;
            }
        }
        return null;
    }

    public FieldAttributeDefinition getFieldAttributeByField(
            FieldDefinition field) {
        for (FieldAttributeDefinition adef : this.fieldAttributes) {
            if (adef.getField().equals(field)) {
                return adef;
            }
        }
        return null;
    }

    @Override
    public void setClazz(Class<?> clazz) {
        super.setClazz(Preset.class);
    }

    public static PresetDefinition BasicPreset(Searchbox sb,
            CollectionDefinition collection) {
        PresetDefinition pdef = new PresetDefinition(collection);
        pdef.setSlug("all");
        pdef.setLabel("Basic Preset");

        SearchElementDefinition query = new SearchElementDefinition(
                "EdismaxQuery", EdismaxQuery.class);
        pdef.addSearchElement(query);

        SearchElementDefinition result = new SearchElementDefinition("HitList",
                HitList.class);
        pdef.addSearchElement(result);

        return pdef;
    }

    @PrePersist
    public void checkPresetAttributes() {
        // THis is for a SearchEngine Managed Collection!!!
        for (FieldDefinition fdef : collection.getFields()) {
            boolean exists = false;
            for (FieldAttributeDefinition attr : fieldAttributes) {
                if (attr.getField().equals(fdef)) {
                    exists = true;
                }
            }
            if (!exists) {
                this.addFieldAttribute(new FieldAttributeDefinition(fdef));
            }
        }
    }

    @Override
    public Preset getInstance() {
        Preset preset = (Preset) super.toObject();
        BeanUtils.copyProperties(this, preset);
        return preset;
    }
}
