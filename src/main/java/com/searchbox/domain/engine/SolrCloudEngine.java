package com.searchbox.domain.engine;
import com.searchbox.domain.dm.Collection;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
public class SolrCloudEngine extends Collection {

    /**
     */
    private String zkHost;
}
