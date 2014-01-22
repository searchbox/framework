package com.searchbox.domain.dm;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ManyToOne;
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
    
    @ManyToOne(targetEntity= Collection.class)
    private SearchEngine engine;
    
    @OneToMany(mappedBy="collection")
    private List<Field> fields = new ArrayList<Field>();
    
    public Collection(String name){
    	this.name = name;
    }
    
    public Collection(String name, SearchEngine engine){
    	this.name = name;
    	this.engine = engine;
    }
    
    public void addField(Field field){
    	this.fields.add(field);
    }
}
