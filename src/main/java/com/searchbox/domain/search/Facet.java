package com.searchbox.domain.search;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public abstract class Facet {

    /**
     */
    private String label;

    /**
     */
    private int position;
}
