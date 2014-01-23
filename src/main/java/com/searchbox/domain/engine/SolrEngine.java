package com.searchbox.domain.engine;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.searchbox.domain.dm.Collection;

@RooJavaBean
@RooToString
public class SolrEngine extends SearchEngine {

    /**
     */
    private String solrHost;
}
