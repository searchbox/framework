package com.searchbox.framework.util;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.BeanInstantiationException;

import com.searchbox.core.SearchAttribute;
import com.searchbox.framework.model.AttributeEntity;

public class SearchBeanUtil {

  public static void inspectAndSaveAttribute(Class<?> searchElement,
      Collection<AttributeEntity> attributes) {
    if (searchElement != null) {
      for (Field field : searchElement.getDeclaredFields()) {
        if (field.isAnnotationPresent(SearchAttribute.class)) {
          AttributeEntity attrDef = new AttributeEntity()
            .setName(field.getName())
            .setType(field.getType());
          String value = field.getAnnotation(SearchAttribute.class).value();
          //TODO fix getting default value for the attribute
//          if (value != null && !value.isEmpty()) {
//            try {
//              
//              String ovalue = BeanUtils.getProperty(bean, name)
//                  BeanUtils.instantiateClass(field.getType()
//                  .getConstructor(String.class), value);
//              attrDef.setValue(ovalue);
//            } catch (BeanInstantiationException | NoSuchMethodException
//                | SecurityException e) {
//              LOGGER.trace(
//                  "Could not construct default value (not much of a problem)",
//                  e);
//            }
//          }
          attributes.add(attrDef);
        }
      }
      inspectAndSaveAttribute(searchElement.getSuperclass(), attributes);
    } else {
      return;
    }
  }
}
