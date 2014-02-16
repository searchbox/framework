package com.searchbox.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(finders = { "findSearchboxesBySlugEquals",
		"findSearchboxesBySlugLike" })
public class Searchbox {

	public Searchbox(String name, String description) {
		this.name = name;
		this.slug = name;
		this.description = description;
	}

	/**
     */
	private String slug;

	/**
     */
	private String name;
	
	private String alias;

	/**
     */
	private String description;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "searchbox", orphanRemoval = true, cascade=CascadeType.ALL)
	private List<PresetDefinition> presets = new ArrayList<PresetDefinition>();

	public void addPresetDefinition(PresetDefinition preset) {
		preset.setSearchbox(this);
		this.presets.add(preset);
	}
}
