package com.searchbox.app.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import com.searchbox.core.dm.Field;
import com.searchbox.core.dm.PresetFieldAttribute;
import com.searchbox.ref.ReflectionUtils;

@Entity
public class FieldDefinition {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
	
	@Version
	@Column(name="OPTLOCK")
	private long version;

	private Class clazz;
	
	private String key;

	public FieldDefinition(Class clazz, String key) {
		this.clazz = clazz;
		this.key = key;
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

	public Class getClazz() {
		return clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Field getElement() {
		return new Field(clazz, key);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clazz == null) ? 0 : clazz.getSimpleName().hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
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
		if (clazz == null) {
			if (other.clazz != null)
				return false;
		} else if (!clazz.getSimpleName().equals(other.clazz.getSimpleName()))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
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
