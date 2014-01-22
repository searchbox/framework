package com.searchbox.domain.app;
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
    private Field field;

    /**
     */
    private Double weight;
    
    private boolean searchable;
    
    private boolean sortable;
}
