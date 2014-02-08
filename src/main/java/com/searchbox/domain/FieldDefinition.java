package com.searchbox.domain;

import javax.persistence.ManyToOne;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class FieldDefinition {

	/**
     */
	private String label;

	private Double weight;

	private boolean searchable;

	private boolean sortable;

	/**
     */
	@ManyToOne
	private Field field;

	@ManyToOne
	private Preset preset;
	/**
     */
}
