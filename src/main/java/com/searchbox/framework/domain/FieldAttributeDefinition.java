package com.searchbox.framework.domain;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import com.searchbox.core.dm.FieldAttribute;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class FieldAttributeDefinition extends UnknownClassDefinition implements ElementFactory<FieldAttribute> {
	
	@ManyToOne(targetEntity=PresetDefinition.class)
	private PresetDefinition preset;
	
	@ManyToOne(targetEntity=FieldDefinition.class)
	protected FieldDefinition field;
		
	public FieldAttributeDefinition(){
		super(FieldAttribute.class);
	}
	
	public FieldAttributeDefinition(FieldDefinition field){
		super(FieldAttribute.class);
		this.field = field;
	}
	
	public PresetDefinition getPreset() {
		return preset;
	}

	public void setPreset(PresetDefinition preset) {
		this.preset = preset;
	}

	public FieldDefinition getField() {
		return field;
	}

	public void setField(FieldDefinition field) {
		this.field = field;
	}

	@Override
	public FieldAttribute getInstance() {
		FieldAttribute attribute = (FieldAttribute) super.toObject();
		attribute.setKey(this.field.getKey());
		return attribute;
	}
}
