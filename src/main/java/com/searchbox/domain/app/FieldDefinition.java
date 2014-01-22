package com.searchbox.domain.app;
import javax.persistence.ManyToOne;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import com.searchbox.domain.dm.Field;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class FieldDefinition {

    /**
     */
    private String label;

    private Double weight;
    
    private boolean searchable;
    
    private boolean sortable;

    /**
     */
    @ManyToOne
    private Field field;
    
    @ManyToOne
    private Field preset;
    /**
     */
 }
