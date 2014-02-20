package com.searchbox.app.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@Entity
public class Searchbox {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
	
	@Version
	@Column(name="OPTLOCK")
	private long version;
	
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
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<PresetDefinition> getPresets() {
		return presets;
	}

	public void setPresets(List<PresetDefinition> presets) {
		this.presets = presets;
	}

	public void addPresetDefinition(PresetDefinition preset) {
		preset.setSearchbox(this);
		this.presets.add(preset);
	}
}
