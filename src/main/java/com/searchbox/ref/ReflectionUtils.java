package com.searchbox.ref;

import java.lang.reflect.Field;
import java.util.List;

import com.searchbox.domain.DefinitionAttribute;

public class ReflectionUtils {

	public static void inspectAndSaveAttribute(Class<?> searchElement, List<DefinitionAttribute> attributes){
		if(searchElement != null){
			for(Field field:searchElement.getDeclaredFields()){
				//TODO might wanna type as DefinitionAttribute<Integer>(...)
				attributes.add(new DefinitionAttribute(field.getType(), field.getName()));
			}
			inspectAndSaveAttribute(searchElement.getSuperclass(), attributes);
		} else{
			return;
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
