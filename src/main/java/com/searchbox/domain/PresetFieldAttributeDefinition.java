package com.searchbox.domain;

import javax.persistence.ManyToOne;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import com.searchbox.core.dm.Preset;
import com.searchbox.core.dm.PresetFieldAttribute;
import com.searchbox.ref.ReflectionUtils;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class PresetFieldAttributeDefinition {
	
	@ManyToOne(targetEntity=PresetDefinition.class)
	private PresetDefinition preset;
	
	@ManyToOne
	private FieldDefinition field;
		
	private String label = "";
	
	private Boolean searchable = false;
	
	private Boolean highlight = false;
	
	private Boolean sortable = false;
	
	private Boolean spelling = false;
	
	private Boolean suggestion = false;
	
	private Float boost = 1.0f;
 
	
	public PresetFieldAttributeDefinition(FieldDefinition field){
		this.field = field;
	}


	public PresetFieldAttribute getElement() {
			PresetFieldAttribute element = new PresetFieldAttribute();
			ReflectionUtils.copyAllFields(this, element);
			element.setField(this.field.getElement());
			return element;
	}
}
