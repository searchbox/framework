package com.searchbox.domain.facet;
import com.searchbox.domain.Facet;
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
