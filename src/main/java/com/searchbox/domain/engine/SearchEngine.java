package com.searchbox.domain.engine;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import com.searchbox.domain.dm.Collection;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public abstract class SearchEngine {

	@OneToMany(mappedBy="engine",cascade=CascadeType.ALL)
	private List<Collection> collections;
  
}
