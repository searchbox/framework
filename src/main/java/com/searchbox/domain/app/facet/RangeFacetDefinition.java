package com.searchbox.domain.app.facet;
import javax.persistence.ManyToOne;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import com.searchbox.domain.app.FacetDefinition;
import com.searchbox.domain.dm.Field;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class RangeFacetDefinition extends FacetDefinition{

    /**
     */
    private Integer min;
    
    private Integer max;
}
