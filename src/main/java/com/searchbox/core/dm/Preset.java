package com.searchbox.core.dm;

import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@MappedSuperclass
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
	
	public Preset(){
		
	}
	
	public Preset(String slug, String label){
		this.slug = slug;
		this.label = label;
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

	@Override
	public int compareTo(Preset o) {
		return o.getPosition().compareTo((this.getPosition()));
	}
	
	@Override
	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
