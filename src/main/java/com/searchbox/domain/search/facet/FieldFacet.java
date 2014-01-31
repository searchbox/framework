package com.searchbox.domain.search.facet;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import com.searchbox.domain.search.Facet;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class FieldFacet extends Facet<String> {

    public FieldFacet(String label, String fieldName) {
		super(label);
		this.fieldName = fieldName;
	}

	/**
     */
    private String fieldName;    
}
