package com.searchbox.domain;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
public abstract class Definition<K> {
	
	private Class<?> clazz;
	
	@OneToMany(targetEntity=DefinitionAttribute.class, cascade=CascadeType.ALL)
	private List<DefinitionAttribute> attributes;

	public Definition(){}

	public Definition(Class<?> clazz) {
		this.clazz = clazz;
		attributes = new ArrayList<DefinitionAttribute>();
		inspectAndSaveAttribute(clazz, attributes);
	}

	protected static void inspectAndSaveAttribute(Class<?> searchElement, List<DefinitionAttribute> attributes){
		if(searchElement != null){
			for(Field field:searchElement.getDeclaredFields()){
				System.out.println("Adding attr: " + field.getName() + " as type: " + field.getType().getSimpleName());
				//TODO might wanna type as DefinitionAttribute<Integer>(...)
				attributes.add(new DefinitionAttribute(field.getType(), field.getName()));
			}
			inspectAndSaveAttribute(searchElement.getSuperclass(), attributes);
		} else{
			return;
		}
	}
	
	protected static Field findUnderlying(Class<?> element, String fieldName) {
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
	
	public K getElement(){
		try {
			K element = (K) clazz.newInstance();
			for(DefinitionAttribute attribute:attributes){
				if(attribute.getValue() != null){
					System.out.println("Setting field: " +  attribute.getName());
					Field field = findUnderlying(clazz, attribute.getName());
					field.setAccessible(true);
					field.set(element, attribute.getValue());
				}
			}
			return (K) element;
		} catch (Exception e){
			e.printStackTrace(System.out);
		}
		return null;
	}
	
	public List<DefinitionAttribute> getAttributes(){
		return this.attributes;
	}
	
	public DefinitionAttribute getAttributeByName(String name){
		for(DefinitionAttribute attr:this.attributes){
			if(attr.getName().equals(name)){
				return attr;
			}
		}
		return null;
	}
	
	public Definition<K> setAttributeValue(String name, Object value) {
		this.getAttributeByName(name).setValue(value);
		return this;
	}
	
	
}
