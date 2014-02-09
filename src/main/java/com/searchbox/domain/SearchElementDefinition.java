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
		for(Field field:searchElement.getDeclaredFields()){
			attributes.add(new SearchElementDefinitionAttribute(field.getType(), field.getName()));
		}
	}

	public SearchElement getSearchElement(){
		try {
			SearchElement element = (SearchElement) searchElementClass.newInstance();
			//TODO need to populate the elements here.
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
}
