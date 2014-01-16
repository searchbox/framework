package com.searchbox.domain;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Field {

    /**
     */
    private String key;

    /**
     */
    private String label;

    /**
     */
    private Boolean searchable;

    /**
     */
    private Boolean sortable;

    /**
     */
    private Boolean spellable;

    /**
     */
    private String type;

    /**
     */
    private float weight;
}
