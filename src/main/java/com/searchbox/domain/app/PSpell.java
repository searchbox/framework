package com.searchbox.domain.app;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import com.searchbox.domain.dm.Field;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class PSpell {

    /**
     */
    private String name;

    /**
     */
    private List<Field> fields;
    
    @ManyToOne(cascade= CascadeType.ALL, targetEntity= Searchbox.class)
    private Searchbox searchbox;

}
