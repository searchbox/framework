package com.searchbox.domain.app;
import javax.persistence.ManyToOne;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import com.searchbox.domain.dm.Field;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public abstract class FacetDefinition implements Comparable<FacetDefinition>{

    /**
     */
    private String label;
    
    private Integer position;

    /**
     */
    @ManyToOne
    private Field field;
    
    @ManyToOne
    private Preset preset;
    /**
     */
    
    @Override
   	public int compareTo(FacetDefinition facet) {
   		return facet.getPosition().compareTo(this.getPosition());
   	}
 }
