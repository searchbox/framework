package com.searchbox.core.dm;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
public class Field {

	private Class clazz;
	/**
     */
	private String key;

	/**
     */
		
	private Boolean multivalue = false;

	public Field(Class clazz, String key) {
		this.clazz = clazz;
		this.key = key;
	}

	public String getKey() {
        return this.key;
    }

	public void setKey(String key) {
        this.key = key;
    }

	public Boolean getMultivalue() {
        return this.multivalue;
    }

	public void setMultivalue(Boolean multivalue) {
        this.multivalue = multivalue;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
	
	public static Field StringField(String key){
		return new Field(String.class, key);
	}
	
	public static Field DateField(String key){
		return new Field(Date.class, key);
	}
	
	public static Field IntField(String key){
		return new Field(Integer.class, key);
	}
	
	public static Field FloatField(String key){
		return new Field(Float.class, key);
	}
}
