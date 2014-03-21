package com.searchbox.framework.model;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

@MappedSuperclass
public abstract class BeanFactoryEntity<K extends Serializable> 
  extends BaseEntity<K> implements BeanFactory {
  
  private static final Logger LOGGER = LoggerFactory
      .getLogger(BeanFactoryEntity.class);

  @OneToMany(orphanRemoval=true, targetEntity=AttributeEntity.class, cascade=CascadeType.ALL)
  @Type(type="com.searchbox.framework.model.AttributeEntity")
  private Set<AttributeEntity> attributes;
  
  public BeanFactoryEntity(){
    attributes = new TreeSet<AttributeEntity>();
  }

  public Set<AttributeEntity> getAttributes() {
    return attributes;
  }

  public void setAttributes(Set<AttributeEntity> attributes) {
    this.attributes = attributes;
  }

  public <T> T build(Class<T> clazz) {
    LOGGER.info("BeanFactory for {}",clazz);
    try {
      T target =  (T) clazz.newInstance();
      BeanUtils.copyProperties(this, target);
      for(AttributeEntity attribute:this.attributes){
        try {
        String attrName = attribute.getName();
        PropertyDescriptor prop = BeanUtils.getPropertyDescriptor(clazz, attrName);
        prop.getWriteMethod().setAccessible(true);
        prop.getWriteMethod().invoke(target, attribute.getValue());
        } catch (Exception e){
          LOGGER.warn("Could not set {} to value: {}",attribute.getName(), attribute.getValue());
        }
      }
      return target;
    } catch (Exception exception) {
      LOGGER.error("Could not build Bean for class {}",clazz);
      LOGGER.error("Error: {}", exception.getMessage());
      exception.printStackTrace();
    }
    return null;
  }
}
