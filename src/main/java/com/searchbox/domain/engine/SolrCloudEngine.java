package com.searchbox.domain.engine;

import java.net.URL;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class SolrCloudEngine extends SearchEngine {

	/**
     */
	private URL zkHost;

	/**
     */
	private String name;
}
