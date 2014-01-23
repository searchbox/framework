package com.searchbox.domain;
import com.searchbox.domain.facet.FieldFacet;
import org.springframework.roo.addon.jsf.managedbean.RooJsfManagedBean;
import org.springframework.roo.addon.serializable.RooSerializable;

@RooSerializable
@RooJsfManagedBean(entity = FieldFacet.class, beanName = "fieldFacetBean")
public class FieldFacetBean {
}
