/*******************************************************************************
 * Copyright Searchbox - http://www.searchbox.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
