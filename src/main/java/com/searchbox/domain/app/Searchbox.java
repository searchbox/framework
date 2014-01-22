package com.searchbox.domain.app;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
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

    @OneToMany(fetch=FetchType.LAZY, mappedBy="searchbox", orphanRemoval=true)
    private List<Preset> presets;
}
