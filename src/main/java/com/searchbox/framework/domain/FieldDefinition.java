package com.searchbox.framework.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import com.searchbox.core.dm.Field;

@Entity
public class FieldDefinition extends Field{

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
	
	@Version
	@Column(name="OPTLOCK")
	private long version;
	
	public FieldDefinition() {
		super();
	}
	
	public FieldDefinition(Class<?> clazz, String key) {
		super(clazz, key);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getClazz() == null) ? 0 : getClazz().getSimpleName().hashCode());
		result = prime * result + ((getKey() == null) ? 0 : getKey().hashCode());
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
		if (getKey() == null) {
			if (other.getKey() != null)
				return false;
		} else if (!getKey().equals(other.getKey()))
			return false;
		return true;
	}
}
