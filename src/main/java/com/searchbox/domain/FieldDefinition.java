package com.searchbox.domain;

import java.util.Date;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class FieldDefinition {

	private Class clazz;
	
	private String key;

	public FieldDefinition(Class clazz, String key) {
		this.clazz = clazz;
		this.key = key;
	}
	
	public static FieldDefinition StringFieldDef(String key){
		return new FieldDefinition(String.class, key);
	}
	
	public static FieldDefinition DateFieldDef(String key){
		return new FieldDefinition(Date.class, key);
	}
	
	public static FieldDefinition IntFieldDef(String key){
		return new FieldDefinition(Integer.class, key);
	}
	
	public static FieldDefinition FloatFieldDef(String key){
		return new FieldDefinition(Float.class, key);
	}
}
