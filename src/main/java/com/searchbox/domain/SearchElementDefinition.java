package com.searchbox.domain;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.facet.FieldFacet;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class SearchElementDefinition {
	
	@ManyToOne
	private Preset preset;
	
	private Integer position;
	
	private Class<?> searchElementClass;
	
	@OneToMany(targetEntity=SearchElementDefinitionAttribute.class, cascade=CascadeType.ALL)
	private List<SearchElementDefinitionAttribute> attributes;
	
	public SearchElementDefinition(Class<?> searchElement){
		this.searchElementClass = searchElement;
		attributes = new ArrayList<SearchElementDefinitionAttribute>();
		inspectAndSaveAttribute(searchElement, attributes);
	}

	private static void inspectAndSaveAttribute(Class<?> searchElement, List<SearchElementDefinitionAttribute> attributes){
		if(SearchElement.class.isAssignableFrom(searchElement)){
			for(Field field:searchElement.getDeclaredFields()){
				attributes.add(new SearchElementDefinitionAttribute(field.getType(), field.getName()));
			}
			inspectAndSaveAttribute(searchElement.getSuperclass(), attributes);
		} else{
			return;
		}
	}
	
	public static Field findUnderlying(Class<?> searchElement, String fieldName) {
		if(SearchElement.class.isAssignableFrom(searchElement)){
			Field field = null;
			try {
				field = searchElement.getDeclaredField(fieldName);
			} catch (Exception e) {
				
			}
			if(field!=null) {
				return field;
			} else {
				return findUnderlying(searchElement.getSuperclass(), fieldName);
			}
		} else {
			return null;
		}
	}
	
	public SearchElement getSearchElement(){
		try {
			SearchElement element = (SearchElement) searchElementClass.newInstance();
			for(SearchElementDefinitionAttribute attribute:attributes){
				if(attribute.getValue() != null){
					Field field = findUnderlying(searchElementClass, attribute.getName());
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
	
	public List<SearchElementDefinitionAttribute> getAttributes(){
		return this.attributes;
	}
	
	public SearchElementDefinitionAttribute getAttributeByName(String name){
		for(SearchElementDefinitionAttribute attr:this.attributes){
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
		
		for(SearchElementDefinitionAttribute attr:fdef.getAttributes()){
			System.out.println("Field["+attr.getType().getSimpleName()+"]\t" + attr.getName()+"\t"+attr.getValue());
		}
		
		SearchElement elem = fdef.getSearchElement();
		System.out.println("element Label: " + elem.getLabel());
	}
}
