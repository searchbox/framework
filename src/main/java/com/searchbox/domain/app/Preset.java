package com.searchbox.domain.app;
import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

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

}
