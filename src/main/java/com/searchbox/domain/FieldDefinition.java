package com.searchbox.domain;

import javax.persistence.ManyToOne;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import com.searchbox.core.dm.Field;
import com.searchbox.core.dm.Preset;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class FieldDefinition extends Definition<Field> {

	public FieldDefinition() {
		super(Field.class);
		// TODO Auto-generated constructor stub
	}
}
