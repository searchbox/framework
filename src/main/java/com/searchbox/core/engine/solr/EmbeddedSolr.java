package com.searchbox.core.engine.solr;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;

import com.searchbox.anno.SearchAttribute;
import com.searchbox.core.dm.Preset;
import com.searchbox.core.engine.AbstractSearchEngine;
import com.searchbox.core.search.SearchCondition;
import com.searchbox.core.search.SearchElement;

public class EmbeddedSolr extends AbstractSearchEngine<SolrQuery> {

	@SearchAttribute
	private String solrHome;
	
	@SearchAttribute
	private String dataDir;
	
	@SearchAttribute
	private String solrConfig;

	@SearchAttribute
	private String solrSchema;

	@SearchAttribute
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
	
	public String getDataDir() {
		return dataDir;
	}

	public void setDataDir(String dataDir) {
		this.dataDir = dataDir;
	}

	public String getSolrConfig() {
		return solrConfig;
	}

	public void setSolrConfig(String solrConfig) {
		this.solrConfig = solrConfig;
	}

	public String getSolrSchema() {
		return solrSchema;
	}

	public void setSolrSchema(String solrSchema) {
		this.solrSchema = solrSchema;
	}

	public String getCoreName() {
		return coreName;
	}

	public void setCoreName(String coreName) {
		this.coreName = coreName;
	}

	public String getSolrHome() {
		return solrHome;
	}

	public void setSolrHome(String solrHome) {
		this.solrHome = solrHome;
	}
}
