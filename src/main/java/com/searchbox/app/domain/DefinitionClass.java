package com.searchbox.app.domain;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.ref.ReflectionUtils;

@Entity
public class DefinitionClass {
	
	private static Logger logger = LoggerFactory.getLogger(DefinitionClass.class);

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
	
	@Version
	@Column(name="OPTLOCK")
	private long version;
	
	private Class<?> clazz;

	private String name;

	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<DefinitionAttribute> attributes = new ArrayList<DefinitionAttribute>();

	public DefinitionClass(){
		this.attributes = new ArrayList<DefinitionAttribute>();
	}
	
	protected DefinitionClass(String name, Class<?> clazz){
		this.name = name;
		this.clazz = clazz;
		this.attributes = new ArrayList<DefinitionAttribute>();
		ReflectionUtils.inspectAndSaveAttribute(clazz, attributes);
	}
	
	protected Object toObject(){
		Object element = null;
		try {
			element = this.getClazz().newInstance();
		} catch (Exception e) {
			logger.error("Could not create new instance of: " + this.getClazz(),e);
			throw new RuntimeException("Could not construct element for class: " + getClazz());
		}
		
		for(DefinitionAttribute attribute:this.getAttributes()){
			if(attribute.getValue() != null){
				Method setter = null;
				try {
					setter = new PropertyDescriptor(attribute.getName(), element.getClass()).getWriteMethod();
					if(setter == null){
						logger.error("Could not find setter: " + element.getClass().getName()+"#"+attribute.getName());
						throw new RuntimeException("Could not construct element for class: " + getClazz());
					} else {
						setter.invoke(element, attribute.getValue());
					}
				} catch (Exception e) {
					logger.error("Could not find setter: " + element.getClass().getName()+
							"#"+attribute.getName()+"["+attribute.getType().getName()+"]");
					logger.error("Attribute Value is: " + attribute.getValue());
					logger.error("Attribute Value Class is: " + attribute.getValue().getClass().getName());
					if(setter != null){
						logger.error("\tsetter args: " + setter.getParameterTypes()[0].getName());
					}
					throw new RuntimeException("Could not construct element for class: " + getClazz());
				}
			}
		}
		return element;
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

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<DefinitionAttribute> getAttributes(){
		return this.attributes;
	}

	public void setAttributes(List<DefinitionAttribute> attributes) {
		this.attributes = attributes;
	}
	
	public DefinitionAttribute getAttributeByName(String name){
		for(DefinitionAttribute attr:this.attributes){
			if(attr.getName().equals(name)){
				return attr;
			}
		}
		return null;
	}
	
	public DefinitionClass setAttributeValue(String name, Object value) {
		DefinitionAttribute attr = this.getAttributeByName(name);
		if(attr != null){
			this.getAttributeByName(name).setValue(value);
		} else {
			logger.error("Could not set Attribute \""+name+"\" for element: " + this.clazz.getSimpleName());
		}
		return this;
	}
}
