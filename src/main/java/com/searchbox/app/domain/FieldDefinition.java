package com.searchbox.app.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import com.searchbox.core.dm.Field;

@Entity
public class FieldDefinition extends DefinitionClass{
	
	public FieldDefinition() {
	}
		
	public FieldDefinition(Class clazz, String key) {
		super(key, clazz);
	}
	
	public String getKey() {
		return this.getName();
	}

	public void setKey(String key) {
		this.setName(key);
	}

	public Field toField() {
		return new Field(getClazz(), getName());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getClazz() == null) ? 0 : getClazz().getSimpleName().hashCode());
		result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FieldDefinition other = (FieldDefinition) obj;
		if (getClazz() == null) {
			if (other.getClazz() != null)
				return false;
		} else if (!getClazz().getSimpleName().equals(other.getClazz().getSimpleName()))
			return false;
		if (getName() == null) {
			if (other.getName() != null)
				return false;
		} else if (!getName().equals(other.getName()))
			return false;
		return true;
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
