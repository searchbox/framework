package com.searchbox.domain.app;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import com.searchbox.domain.dm.Collection;
import com.searchbox.domain.dm.Field;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Preset implements Comparable<Preset> {

	/**
     */
	private String slug;

	/**
     */
	private String label;

	/**
     */
	private String description;

	/**
     */
	private Boolean global;

	/**
     */
	private Boolean visible;

	/**
     */
	private Integer position;

	/**
     */
	private String snippetTemplate;

	/**
     */
	private String viewTemplate;

	/**
     */
	private String metaTemplate;

	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Searchbox.class)
	private Searchbox searchbox;

	@OneToMany
	private List<FieldDefinition> fields;

	@OneToMany(cascade = CascadeType.ALL)
	@Sort(type = SortType.NATURAL)
	private List<SearchElementDefinition> searchElements = new ArrayList<SearchElementDefinition>();

	@ManyToOne
	private Collection collection;

	@ManyToMany
	private List<Field> spells;

	public Preset(String label, Collection collection) {
		this.label = label;
	}
	
	public void addSearchElement(SearchElementDefinition searchElements) {
		searchElements.setPosition(this.searchElements.size());
		this.searchElements.add(searchElements);
	}

	@Override
	public int compareTo(Preset o) {
		return o.getPosition().compareTo(this.getPosition());
	}
}
