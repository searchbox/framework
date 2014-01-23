package com.searchbox.domain.search;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public abstract class Facet implements Comparable<Facet> {

    /**
     */
    private String label;

    /**
     */
    private Integer position;
    
    @Override
	public int compareTo(Facet facet) {
    	return this.position.compareTo(facet.getPosition());
	}
}
