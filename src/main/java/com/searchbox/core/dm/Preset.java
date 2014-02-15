package com.searchbox.core.dm;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import com.searchbox.core.search.SearchElement;

@RooJavaBean
@RooToString
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


	private List<SearchElement> searchElements = new ArrayList<SearchElement>();

	private Collection collection;

	private Field<?> idField;
	
	private List<PresetFieldAttribute> fieldAttributes = new ArrayList<PresetFieldAttribute>();;
	
	public Preset(){
		
	}
	
	public Preset(String label, Collection collection) {
		this.label = label;
	}
	
	public void addSearchElement(SearchElement element) {
		element.setPosition(this.searchElements.size());
		this.searchElements.add(element);
	}

	@Override
	public int compareTo(Preset o) {
		return o.getPosition().compareTo((this.getPosition()));
	}

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String getSlug() {
        return this.slug;
    }

	public void setSlug(String slug) {
        this.slug = slug;
    }

	public String getLabel() {
        return this.label;
    }

	public void setLabel(String label) {
        this.label = label;
    }

	public String getDescription() {
        return this.description;
    }

	public void setDescription(String description) {
        this.description = description;
    }

	public Boolean getGlobal() {
        return this.global;
    }

	public void setGlobal(Boolean global) {
        this.global = global;
    }

	public Boolean getVisible() {
        return this.visible;
    }

	public void setVisible(Boolean visible) {
        this.visible = visible;
    }

	public Integer getPosition() {
        return this.position;
    }

	public void setPosition(Integer position) {
        this.position = position;
    }

	public List<SearchElement> getSearchElements() {
        return this.searchElements;
    }

	public void setSearchElements(List<SearchElement> searchElements) {
        this.searchElements = searchElements;
    }

	public Collection getCollection() {
        return this.collection;
    }

	public void setCollection(Collection collection) {
        this.collection = collection;
    }

	public Field<?> getIdField() {
        return this.idField;
    }

	public void setIdField(Field<?> idField) {
        this.idField = idField;
    }

	public List<PresetFieldAttribute> getFieldAttributes() {
        return this.fieldAttributes;
    }

	public void setFieldAttributes(List<PresetFieldAttribute> fieldAttributes) {
        this.fieldAttributes = fieldAttributes;
    }

	public void addFieldAttribute(PresetFieldAttribute fieldAttribute) {
		this.fieldAttributes.add(fieldAttribute);
	}
}
