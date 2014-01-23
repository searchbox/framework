package com.searchbox.domain;
import com.searchbox.domain.collection.SolrCloudCollection;
import org.springframework.roo.addon.jsf.managedbean.RooJsfManagedBean;
import org.springframework.roo.addon.serializable.RooSerializable;

@RooSerializable
@RooJsfManagedBean(entity = SolrCloudCollection.class, beanName = "solrCloudCollectionBean")
public class SolrCloudCollectionBean {
}
