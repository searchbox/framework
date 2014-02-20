package com.searchbox.app.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.util.SerializationUtils;
import org.springframework.validation.annotation.Validated;

@Entity
public class DefinitionAttribute {
	
	private static Logger logger = LoggerFactory.getLogger(DefinitionAttribute.class);
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
	
	@Version
	@Column(name="OPTLOCK")
	private long version;
	
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
		try {
			return SerializationUtils.deserialize(valueAsByteArray);
		} catch (Exception e){
			logger.error("Could not deserialize value: " + this, e);
			return null;
		}
	}

	public void setValue(Object value) {
		this.valueAsByteArray = SerializationUtils
				.serialize((Serializable) value);
	}
}
