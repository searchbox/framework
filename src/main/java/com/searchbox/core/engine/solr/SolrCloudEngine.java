package com.searchbox.core.engine.solr;

import java.net.URL;

import com.searchbox.core.engine.SearchEngine;

public class SolrCloudEngine extends SearchEngine {

	/**
     */
	private URL zkHost;

	/**
     */
	private String name;

	public URL getZkHost() {
		return zkHost;
	}

	public void setZkHost(URL zkHost) {
		this.zkHost = zkHost;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
