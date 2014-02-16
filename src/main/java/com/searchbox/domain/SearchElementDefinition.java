package com.searchbox.domain;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.facet.FieldFacet;
import com.searchbox.ref.ReflectionUtils;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class SearchElementDefinition {
	
	private Class<?> clazz;

	@ManyToOne(targetEntity=PresetDefinition.class)
	private PresetDefinition preset;
	
	@OneToMany(targetEntity=DefinitionAttribute.class, cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<DefinitionAttribute> attributes;
	
	public SearchElementDefinition(Class<?> searchElementClass){
		this.clazz = searchElementClass;
		this.attributes = new ArrayList<DefinitionAttribute>();
		ReflectionUtils.inspectAndSaveAttribute(clazz, attributes);
	}
	

	public SearchElement getElement(){
		try {
			SearchElement element = (SearchElement) clazz.newInstance();
			for(DefinitionAttribute attribute:attributes){
				if(attribute.getValue() != null){
					Field field = ReflectionUtils.findUnderlying(clazz, attribute.getName());
					field.setAccessible(true);
					field.set(element, attribute.getValue());
				}
			}
			return element;
		} catch (Exception e){
			e.printStackTrace(System.out);
		}
		return null;
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

}
