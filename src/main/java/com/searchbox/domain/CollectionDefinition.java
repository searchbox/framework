package com.searchbox.domain;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import com.searchbox.core.dm.Collection;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class CollectionDefinition extends Definition{

	public CollectionDefinition() {
		super();
	}
}
