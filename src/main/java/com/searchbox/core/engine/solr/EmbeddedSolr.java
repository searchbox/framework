package com.searchbox.core.engine.solr;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;

import com.searchbox.core.dm.Preset;
import com.searchbox.core.engine.AbstractSearchEngine;
import com.searchbox.core.search.SearchCondition;
import com.searchbox.core.search.SearchElement;

public class EmbeddedSolr extends AbstractSearchEngine<SolrQuery> {

	private String solrHome;
	
	private String dataDir;
	
	private String solrConfig;
	
	private String solrSchema;
	
	private String coreName;
	
	EmbeddedSolr(String name, String solrHome) {
		super(name, SolrQuery.class);
		this.solrHome = solrHome;
	}

	@Override
	public List<SearchElement> executeSearch(Preset preset,
			List<SearchCondition> conditions) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSolrHome() {
		return solrHome;
	}

	public void setSolrHome(String solrHome) {
		this.solrHome = solrHome;
	}
}
