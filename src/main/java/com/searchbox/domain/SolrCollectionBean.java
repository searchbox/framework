package com.searchbox.domain;
import com.searchbox.domain.collection.SolrCollection;
import org.springframework.roo.addon.jsf.managedbean.RooJsfManagedBean;
import org.springframework.roo.addon.serializable.RooSerializable;

@RooSerializable
@RooJsfManagedBean(entity = SolrCollection.class, beanName = "solrCollectionBean")
public class SolrCollectionBean {
}
