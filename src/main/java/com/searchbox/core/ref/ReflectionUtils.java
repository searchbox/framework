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
package com.searchbox.core.ref;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;

import com.searchbox.core.SearchAttribute;
import com.searchbox.framework.model.AttributeEntity;

public class ReflectionUtils {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(ReflectionUtils.class);

  public static void copyAllFields(Object from, Object to) {
    for (Field fromField : findAllFields(from.getClass())) {
      try {
        Field toField = findUnderlying(to.getClass(), fromField.getName());
        if (toField != null) {
          toField.setAccessible(true);
          fromField.setAccessible(true);
          toField.set(to, fromField.get(from));
        }
      } catch (Exception e) {
        LOGGER.warn("Could not copye Fields.", e);
      }
    }
  }

  public static void inspectAndSaveAttribute(Class<?> searchElement,
      Collection<AttributeEntity> attributes) {
    if (searchElement != null) {
      for (Field field : searchElement.getDeclaredFields()) {
        if (field.isAnnotationPresent(SearchAttribute.class)) {
          AttributeEntity attrDef = new AttributeEntity()
          .setName(field.getName())
          .setType(field.getType());
          String value = field.getAnnotation(SearchAttribute.class).value();
          if (value != null && !value.isEmpty()) {
            try {
              Object ovalue = BeanUtils.instantiateClass(field.getType()
                  .getConstructor(String.class), value);
              attrDef.setValue(ovalue);
            } catch (BeanInstantiationException | NoSuchMethodException
                | SecurityException e) {
              LOGGER.trace(
                  "Could not construct default value (not much of a problem)",
                  e);
            }
          }
          attributes.add(attrDef);
        }
      }
      inspectAndSaveAttribute(searchElement.getSuperclass(), attributes);
    } else {
      return;
    }
  }

  public static List<Field> findAllFields(Class<?> element) {
    ArrayList<Field> fields = new ArrayList<Field>();
    if (element != null) {
      for (Field field : element.getDeclaredFields()) {
        fields.add(field);
      }
      fields.addAll(findAllFields(element.getSuperclass()));
      return fields;
    } else {
      return Collections.emptyList();
    }
  }

  public static Field findUnderlying(Class<?> element, String fieldName) {
    if (element != null) {
      Field field = null;
      try {
        field = element.getDeclaredField(fieldName);
      } catch (Exception e) {

      }
      if (field != null) {
        return field;
      } else {
        return findUnderlying(element.getSuperclass(), fieldName);
      }
    } else {
      return null;
    }
  }

  /**
   * Permute all possible parameters
   * 
   * @param caller
   * @param method
   * @param allArguments
   * @param offset
   * @param arguments
   */
  public static List<Object[]> findAllArgumentPermutations(
      Object[][] allArguments) {
    return findAllArgumentPermutations(allArguments, 0, 0,
        new Object[allArguments.length], new ArrayList<Object[]>());

  }

  public static List<Object[]> findAllArgumentPermutations(
      Object[][] allArguments, int depth, int offset, Object[] arguments,
      List<Object[]> results) {
    if (depth < allArguments.length) {
      for (int i = offset; i < allArguments[depth].length; i++) {
        arguments[depth] = allArguments[depth][i];
        // we got a bag here...
        if ((depth + 1) == arguments.length) {
          results.add(arguments.clone());
        }
        findAllArgumentPermutations(allArguments, depth + 1, offset, arguments,
            results);
      }
    }
    return results;
  }
}
