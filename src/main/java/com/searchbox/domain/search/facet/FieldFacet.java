package com.searchbox.domain.search.facet;
import com.searchbox.domain.search.Facet;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class FieldFacet extends Facet {

    /**
     */
    private String fieldName;
}
