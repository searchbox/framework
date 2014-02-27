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
import java.util.Collections;
import java.util.List;

import com.searchbox.core.SearchAttribute;
import com.searchbox.framework.domain.UnknownAttributeDefinition;

public class ReflectionUtils {

	
	public static void copyAllFields(Object from, Object to){
		for(Field fromField:findAllFields(from.getClass())){
			try {
				Field toField = findUnderlying(to.getClass(), fromField.getName());
				if(toField != null){
					toField.setAccessible(true);
					fromField.setAccessible(true);
					toField.set(to, fromField.get(from));
				}
			} catch (Exception e) {
				;
			}			
		}
	}
	
	public static void inspectAndSaveAttribute(Class<?> searchElement, List<UnknownAttributeDefinition> attributes){
		if(searchElement != null){
			for(Field field:searchElement.getDeclaredFields()){
				if(field.isAnnotationPresent(SearchAttribute.class)){
					attributes.add(new UnknownAttributeDefinition(field.getType(), field.getName()));
				}
			}
			inspectAndSaveAttribute(searchElement.getSuperclass(), attributes);
		} else{
			return;
		}
	}
	
	public static List<Field> findAllFields(Class<?> element){
		ArrayList<Field> fields = new ArrayList<Field>();
		if(element != null){
			for(Field field:element.getDeclaredFields()){
				fields.add(field);
			}
			fields.addAll(findAllFields(element.getSuperclass()));
			return fields;
		} else {
			return Collections.emptyList();
		}
	}
	
	public static Field findUnderlying(Class<?> element, String fieldName) {
		if(element != null){
			Field field = null;
			try {
				field = element.getDeclaredField(fieldName);
			} catch (Exception e) {
				
			}
			if(field!=null) {
				return field;
			} else {
				return findUnderlying(element.getSuperclass(), fieldName);
			}
		} else {
			return null;
		}
	}
}
