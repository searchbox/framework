package com.searchbox.domain;

import javax.persistence.ManyToOne;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class PresetFieldAttributeDefinition {
	
	@ManyToOne(targetEntity=PresetDefinition.class)
	private PresetDefinition preset;
	
	@ManyToOne
	private FieldDefinition field;
		
	private String label;
	
	private Boolean searchable;
	
	private Boolean highlight;
	
	private Boolean sortable;
	
	private Boolean spelling;
	
	private Boolean suggestion;
	
	private Float boost;
 
	
	public PresetFieldAttributeDefinition(FieldDefinition field){
		this.field = field;
	}
}
