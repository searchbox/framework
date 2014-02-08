package com.searchbox.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Collection {

	/**
     */
	private String name;

//	@ManyToOne
//	private SearchEngine engine;
	
	@OneToMany(mappedBy = "collection", cascade = CascadeType.ALL)
	private List<Preset> presets = new ArrayList<Preset>();

	@OneToMany(mappedBy = "collection", cascade = CascadeType.ALL)
	private List<Field> fields = new ArrayList<Field>();

	public Collection(String name) {
		this.name = name;
	}

//	public CollectionDefinition(String name, SearchEngine engine) {
//		this.name = name;
//		this.engine = engine;
//	}

	public void addField(Field field) {
		field.setCollection(this);
		this.fields.add(field);
	}
}
