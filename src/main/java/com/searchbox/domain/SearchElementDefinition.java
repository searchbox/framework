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
public class SearchElementDefinition extends Definition<SearchElement> {
	
	@ManyToOne(targetEntity=PresetDefinition.class)
	private PresetDefinition preset;
	
	public SearchElementDefinition(){
		super(SearchElement.class);
	}
				
	public SearchElementDefinition(Class<?> searchElement){
		super(searchElement);
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
