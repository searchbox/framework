package com.searchbox.framework.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Searchbox {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
	
	@Version
	@Column(name="OPTLOCK")
	private long version;
	
	/**
     */
	@Column(unique=true)
	private String slug;

	/**
     */
	private String name;
	
	private String alias;

	/**
     */
	private String description;

	@OneToMany(mappedBy = "searchbox", orphanRemoval = true, cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	private List<PresetDefinition> presets = new ArrayList<PresetDefinition>();
	
	
	@OneToMany(mappedBy = "searchbox", cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	private List<UserRole> userRoles = new ArrayList<UserRole>();
	
	public Searchbox() {
	}

	public Searchbox(String name, String description) {
		this.name = name;
		this.slug = name;
		this.description = description;
	}

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

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public void addPresetDefinition(PresetDefinition preset) {
		preset.setSearchbox(this);
		this.presets.add(preset);
	}
	
	public void addUser(UserRole userRole) {
		this.userRoles.add(userRole);
	}
	
	@Override
	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
