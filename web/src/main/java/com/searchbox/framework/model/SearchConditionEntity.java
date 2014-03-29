package com.searchbox.framework.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.searchbox.core.search.AbstractSearchCondition;

@Entity
public class SearchConditionEntity<K extends AbstractSearchCondition> extends
    BeanFactoryEntity<Long> implements ParametrizedBeanFactory<K>,
    Comparable<SearchConditionEntity<K>> {

  @ManyToOne(fetch = FetchType.LAZY)
  PresetEntity preset;

  private Class<?> clazz;

  private String label;

  public PresetEntity getPreset() {
    return preset;
  }

  public SearchConditionEntity<K> setPreset(PresetEntity preset) {
    this.preset = preset;
    return this;
  }

  public String getLabel() {
    return label;
  }

  public SearchConditionEntity<K> setLabel(String label) {
    this.label = label;
    return this;
  }

  public Class<?> getClazz() {
    return clazz;
  }

  public SearchConditionEntity<K> setClazz(Class<?> clazz) {
    this.clazz = clazz;
    ReflectionUtils.inspectAndSaveAttribute(clazz, this.getAttributes());
    return this;
  }

  @Override
  public int compareTo(SearchConditionEntity<K> o) {
    return this.getLabel().compareTo(o.getLabel());
  }

  @Override
  @SuppressWarnings("unchecked")
  public K build() {
    if (this.getClazz() == null) {
      throw new MissingClassAttributeException();
    }
    return (K) super.build(this.getClazz());
  }

  public PresetEntity end() {
    this.getPreset().getConditions().add(this);
    return this.getPreset();
  }

  public SearchConditionEntity<K> setAttribute(String name, Object value) {

    AttributeEntity attribute = this.getAttributeByName(name);
    if (attribute == null) {

    } else {
      attribute.setValue(value).setType(value.getClass());
    }
    return this;
  }

  @Override
  public String toString() {
    return "SearchConditionEntity [clazz=" + clazz.getSimpleName() + ", label="
        + label + ", getAttributes()=" + getAttributes() + "]";
  }
}
