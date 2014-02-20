package com.searchbox.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@Entity
public class SearchEngineDefinition {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
	
	@Version
	@Column(name="OPTLOCK")
	private long version;
	
}
