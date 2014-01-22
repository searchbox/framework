package com.searchbox.domain.app;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import com.searchbox.domain.dm.Collection;
import com.searchbox.domain.dm.Field;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Preset {

    /**
     */
    private String slug;

    /**
     */
    private String name;

    /**
     */
    private String description;
    
    private Integer position;
    
    @ManyToOne(cascade= CascadeType.ALL, targetEntity= Searchbox.class)
    private Searchbox searchbox;
    
    @OneToMany
    private List<PField> fields;
    
    @ManyToMany
    private List<Collection> collections;
    
    @ManyToMany
    private List<Field> spells;
    
    

}
