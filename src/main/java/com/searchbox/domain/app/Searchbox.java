package com.searchbox.domain.app;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
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
		this.description = description;
	}

	/**
     */
	private String slug;

	/**
     */
	private String name;

	/**
     */
	private String description;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "searchbox", orphanRemoval = true)
	@Sort(type = SortType.NATURAL)
	private List<Preset> presets = new ArrayList<Preset>();

	public void addPreset(Preset preset) {
		preset.setPosition(this.presets.size());
		this.presets.add(preset);
	}
}
