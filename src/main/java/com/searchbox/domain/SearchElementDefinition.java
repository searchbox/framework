package com.searchbox.domain;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.facet.FieldFacet;
import com.searchbox.ref.ReflectionUtils;
import com.searchbox.service.SearchService;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class SearchElementDefinition implements ApplicationContextAware, Comparable<SearchElementDefinition>{
	
	private static Logger logger = LoggerFactory.getLogger(SearchElementDefinition.class);
	
	private Class<?> clazz;
	
	private Integer position;

	@ManyToOne(targetEntity=PresetDefinition.class)
	private PresetDefinition preset;
	
	@OneToMany(targetEntity=DefinitionAttribute.class, cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<DefinitionAttribute> attributes;
	
	@Transient
	ApplicationContext context;
	
	public SearchElementDefinition(Class<?> searchElementClass){
		this.clazz = searchElementClass;
		this.attributes = new ArrayList<DefinitionAttribute>();
		ReflectionUtils.inspectAndSaveAttribute(clazz, attributes);
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.context = applicationContext;
	}

	public SearchElement getElement(){
		try {
			AutowireCapableBeanFactory beanFactory = context.getAutowireCapableBeanFactory();
			SearchElement element = (SearchElement) beanFactory.createBean(clazz,AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, false);
			element.setPosition(this.getPosition());
			for(DefinitionAttribute attribute:attributes){
				if(attribute.getValue() != null){
					Field field = ReflectionUtils.findUnderlying(clazz, attribute.getName());
					field.setAccessible(true);
					field.set(element, attribute.getValue());
				}
			}
			return element;
		} catch (Exception e){
			logger.error("Could not get Element for class: " + clazz, e);
		}
		throw new RuntimeException("Could not construct element for class: " + clazz);
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
	
	public SearchElementDefinition setAttributeValue(String name, Object value) {
		this.getAttributeByName(name).setValue(value);
		return this;
	}
	
	//TODO put that in a JUNIT
	public static void main(String... args){
		SearchElementDefinition fdef = new SearchElementDefinition(FieldFacet.class);
		
		fdef.setAttributeValue("fieldName", "MyField");
		fdef.setAttributeValue("label", "Hello World");
		
		for(DefinitionAttribute attr:fdef.getAttributes()){
			System.out.println("Field["+attr.getType().getSimpleName()+"]\t" + attr.getName()+"\t"+attr.getValue());
		}
		
		SearchElement elem = fdef.getElement();
		System.out.println("element Label: " + elem.getLabel());
	}

	@Override
	public int compareTo(SearchElementDefinition o) {
		return this.position.compareTo(o.getPosition());
	}
}
