package com.searchbox.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.Transient;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.util.SerializationUtils;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class DefinitionAttribute {

	private Class<?> type;
	private String name;

	@Lob
	@Column(name = "value", length = Integer.MAX_VALUE - 1)
	private byte[] valueAsByteArray;
	
	public DefinitionAttribute(Class<?> type, String name) {
		super();
		this.type = type;
		this.name = name;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Transient
	public Object getValue() {
		return (Object) SerializationUtils.deserialize(valueAsByteArray);
	}

	public void setValue(Object value) {
		this.valueAsByteArray = SerializationUtils
				.serialize((Serializable) value);
	}
}
