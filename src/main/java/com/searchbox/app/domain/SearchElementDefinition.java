package com.searchbox.app.domain;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;

import com.searchbox.core.dm.Preset;
import com.searchbox.core.search.SearchElement;

@Entity
@Configurable
public class SearchElementDefinition extends DefinitionClass
 implements Comparable<SearchElementDefinition> {

	private static Logger logger = LoggerFactory.getLogger(SearchElementDefinition.class);
	
	private Integer position;

	@NotNull
	@ManyToOne(targetEntity=PresetDefinition.class)
	private PresetDefinition preset;
	
	public SearchElementDefinition(){
		
	}
	
	public SearchElementDefinition(Class<?> clazz){
		super("", clazz);
	}
	
	public SearchElementDefinition(String name, Class<?> clazz){
		super(name, clazz);
	}
	
	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public PresetDefinition getPreset() {
		return preset;
	}

	public void setPreset(PresetDefinition preset) {
		this.preset = preset;
	}

	public SearchElement toElement(){
		SearchElement element = (SearchElement) super.toObject();
		element.setPosition(this.getPosition());
		element.setDefinitionId(this.getId());
		return element;
	}
	
	
	@Override
	public int compareTo(SearchElementDefinition o) {
		return this.position.compareTo(o.getPosition());
	}
	
	//TODO put that in a JUNIT
		public static void main(String... args){
//			SearchElementDefinition fdef = new SearchElementDefinition(FieldFacet.class);
//			
//			fdef.setAttributeValue("fieldName", "MyField");
//			fdef.setAttributeValue("label", "Hello World");
//			
//			for(DefinitionAttribute attr:fdef.getAttributes()){
//				System.out.println("Field["+attr.getType().getSimpleName()+"]\t" + attr.getName()+"\t"+attr.getValue());
//			}
//			
//			SearchElement elem;
//			try {
//				elem = (FieldFacet) fdef.toElement((SearchElement) fdef.getClazz().newInstance());
//			} catch (InstantiationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			//System.out.println("element Label: " + ((FieldFacet)elem.getLabel()));
		}
		
		@Override
		public String toString() {
	        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	    }
}
