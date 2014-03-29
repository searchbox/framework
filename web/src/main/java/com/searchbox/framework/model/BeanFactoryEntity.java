package com.searchbox.framework.model;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.searchbox.core.SearchElement;
import com.searchbox.framework.util.ContextUtil;

@MappedSuperclass
public abstract class BeanFactoryEntity<K extends Serializable> extends
    BaseEntity<K> {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(BeanFactoryEntity.class);

  @OneToMany(orphanRemoval = true, fetch = FetchType.LAZY, targetEntity = AttributeEntity.class, cascade = CascadeType.ALL)
  @Type(type = "com.searchbox.framework.model.AttributeEntity")
  private Set<AttributeEntity> attributes;

  public BeanFactoryEntity() {
    attributes = new TreeSet<AttributeEntity>();
  }

  public Set<AttributeEntity> getAttributes() {
    return attributes;
  }

  public AttributeEntity getAttributeByName(String name) {
    for (AttributeEntity attribute : this.getAttributes()) {
      if (attribute.getName().equalsIgnoreCase(name)) {
        return attribute;
      }
    }
    return null;
  }

  public void setAttributes(Set<AttributeEntity> attributes) {
    this.attributes = attributes;
  }

  public <T> T build(Class<T> clazz) {
    LOGGER.debug("BeanFactory for {}", clazz);
    try {
      //T target = (T) clazz.newInstance();
      //FIXME this will only work in Spring! 
      T target = ContextUtil.context.getAutowireCapableBeanFactory().createBean(clazz);
      SearchElement t;
      BeanUtils.copyProperties(this, target);
      for (AttributeEntity attribute : this.attributes) {
        if (attribute.getValue() != null) {
          try {
            LOGGER.debug("Setting attr {} to value: {} for clazz {}",
                attribute.getName(), attribute.getValue(),
                clazz.getSimpleName());
            String attrName = attribute.getName();
            PropertyDescriptor prop = BeanUtils.getPropertyDescriptor(clazz,
                attrName);
            prop.getWriteMethod().setAccessible(true);
            prop.getWriteMethod().invoke(target, attribute.getValue());
          } catch (Exception e) {
            LOGGER.error("Could not set {} to value: {}", attribute.getName(),
                attribute.getValue());
          }
        }
      }
      return target;
    } catch (Exception exception) {
      LOGGER.error("Could not build Bean for class {}", clazz);
      LOGGER.error("Error: {}", exception.getMessage());
    }
    return null;
  }

  @Override
  public String toString() {
    return "BeanFactoryEntity [attributes=" + attributes + "]";
  }
}
