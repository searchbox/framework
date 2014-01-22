package com.searchbox.domain.dm;
import javax.persistence.OneToMany;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import com.searchbox.domain.engine.SearchEngine;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Collection {

    /**
     */
    private String name;
    
    @OneToMany
    private SearchEngine engine;
    
    public Collection(String name){
    	this.name = name;
    }
    
    public Collection(String name, SearchEngine engine){
    	this.name = name;
    	this.engine = engine;
    }
}
