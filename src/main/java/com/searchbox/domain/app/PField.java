package com.searchbox.domain.app;
import javax.persistence.ManyToOne;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import com.searchbox.domain.dm.Field;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class PField {

    /**
     */
    private String label;

    /**
     */
    @ManyToOne
    private Field field;
    
    @ManyToOne
    private Field preset;
    /**
     */
    private Double weight;
    
    private boolean searchable;
    
    private boolean sortable;
}
