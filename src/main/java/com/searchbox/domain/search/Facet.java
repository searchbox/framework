package com.searchbox.domain.search;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import com.searchbox.domain.search.facet.FacetValue;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public abstract class Facet<K extends Serializable> implements Comparable<Facet> {

	public Facet(){
		
	}
	
	public Facet(String label){
		this.label = label;
	}
	
    /**
     */
    private String label;

    /**
     */
    private Integer position = 0;
    
    @Transient
    protected List<FacetValue<K>> facetValues = new ArrayList<FacetValue<K>>();
    
    public List<FacetValue<K>> getFacetValues(){
    	return this.facetValues;
    }
    
    public void addFacetValue(FacetValue<K> facetValue){
    	this.facetValues.add(facetValue);
    }
    
    @Override
   	public int compareTo(Facet facet) {
       	return this.position.compareTo(facet.getPosition());
   	}
}
