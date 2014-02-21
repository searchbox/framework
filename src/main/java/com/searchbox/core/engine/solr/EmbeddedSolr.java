package com.searchbox.core.engine.solr;

import java.io.File;
import java.io.FileReader;

import org.apache.commons.io.FileUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrResponse;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.core.CoreContainer;
import org.apache.solr.core.CoreDescriptor;
import org.apache.solr.core.SolrConfig;
import org.apache.solr.core.SolrCore;
import org.apache.solr.schema.IndexSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.xml.sax.InputSource;

import com.searchbox.anno.SearchAttribute;
import com.searchbox.core.engine.AbstractSearchEngine;

@Configurable
public class EmbeddedSolr extends AbstractSearchEngine<SolrQuery, SolrResponse> {
	
	private static Logger logger = LoggerFactory.getLogger(EmbeddedSolr.class);
	
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
	
	private SolrServer server; 
	
	public EmbeddedSolr(){
		super(SolrQuery.class, SolrResponse.class);
	}
	
	public EmbeddedSolr(String name, String solrHome) {
		super(name, SolrQuery.class, SolrResponse.class);
		this.solrHome = solrHome;
	}
	
	@Override
	public SolrResponse execute(SolrQuery query) {
		try {
			return this.server.query(query);
		} catch (SolrServerException e) {
			throw new RuntimeException("Could nexecute Query on  engine",e);
		}
	}

	@Override
	public void run() {
		try {
			logger.info("Embedded solr.solr.home is: " + this.solrHome);
		System.setProperty("solr.solr.home", this.solrHome);

		CoreContainer coreContainer = new CoreContainer();
		coreContainer.load();

		String coreInstanceDir = this.solrHome;
		SolrConfig config = new SolrConfig("solrconfig.xml", 
				new InputSource( new FileReader(new File(this.solrConfig))));

		IndexSchema schema = new IndexSchema(config, "schema.xml",
				new InputSource( new FileReader(new File(this.solrSchema))));

		File dataDir = new File(this.dataDir);
		if(dataDir.exists()){			
			FileUtils.deleteDirectory(dataDir);
		}
		
		String dataDirName = dataDir.getPath();
		
		CoreDescriptor cd = new CoreDescriptor(coreContainer, coreName, coreInstanceDir);
		SolrCore core = new SolrCore(coreName,dataDirName,config, schema, cd);
		logger.info("Solr Core config: " + core.getConfigResource());
		logger.info("Solr Instance dir: " + core.getIndexDir());
		logger.info("Solr Data dir: " + core.getDataDir());
		coreContainer.register(core, false);
		
		this.server = new EmbeddedSolrServer(coreContainer, "pubmed");
		
		} catch (Exception e){
			throw new RuntimeException("Could not start search engine",e);
		}
		
		logger.info("SolrEmdeddedServer is loaded");
		// TODO Get the searchEngine started here.
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
	
	@Deprecated
	public SolrServer getServer(){
		return this.server;
	}
}
