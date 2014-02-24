package com.searchbox.app.domain;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.ref.ReflectionUtils;

@MappedSuperclass
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class UnknownClassDefinition {
	
	private static Logger logger = LoggerFactory.getLogger(UnknownClassDefinition.class);

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
	
	@Version
	@Column(name="OPTLOCK")
	private long version;
	
	private Class<?> clazz;

	@OneToMany(cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<UnknownAttributeDefinition> attributes;
	
	public UnknownClassDefinition(){
		this.attributes = new ArrayList<UnknownAttributeDefinition>();
	}
	
	protected UnknownClassDefinition(Class<?> clazz){
		this.clazz = clazz;
		this.attributes = new ArrayList<UnknownAttributeDefinition>();
		ReflectionUtils.inspectAndSaveAttribute(clazz, attributes);
	}
	
	@Transient
	protected Object toObject(){
		Object element = null;
		try {
			element = this.getClazz().newInstance();
		} catch (Exception e) {
			logger.error("Could not create new instance of: " + this.getClazz(),e);
			throw new RuntimeException("Could not construct element for class: " + getClazz());
		}
		
		for(UnknownAttributeDefinition attribute:this.getAttributes()){
			if(attribute.getValue() != null){
				Method setter = null;
				try {
					setter = new PropertyDescriptor(attribute.getName(), element.getClass()).getWriteMethod();
					if(setter == null){
						logger.error("Could not find setter: " + element.getClass().getName()+"#"+attribute.getName());
						throw new RuntimeException("Could not construct element for class: " + getClazz());
					} else {
						setter.setAccessible(true);
						setter.invoke(element, attribute.getValue());
					}
				} catch (Exception e) {
					logger.error("Error in setter: " + element.getClass().getName()+
							"#"+setter.getName()+"["+attribute.getType().getName()+"]");
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
		ReflectionUtils.inspectAndSaveAttribute(clazz, attributes);
	}

	public List<UnknownAttributeDefinition> getAttributes(){
		return this.attributes;
	}

	public void setAttributes(List<UnknownAttributeDefinition> attributes) {
		this.attributes = attributes;
	}
	
	public UnknownAttributeDefinition getAttributeByName(String name){
		for(UnknownAttributeDefinition attr:this.attributes){
			if(attr.getName().equals(name)){
				return attr;
			}
		}
		return null;
	}
	
	public UnknownClassDefinition setAttributeValue(String name, Object value) {
		UnknownAttributeDefinition attr = this.getAttributeByName(name);
		if(attr != null){
			this.getAttributeByName(name).setValue(value);
		} else {
			logger.error("Could not set Attribute \""+name+"\" for element: " + this.clazz.getSimpleName());
		}
		return this;
	}
}
