package com.searchbox.service;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrResponse;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.core.CoreContainer;
import org.apache.solr.core.CoreDescriptor;
import org.apache.solr.core.SolrConfig;
import org.apache.solr.core.SolrCore;
import org.apache.solr.schema.IndexSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@Service
public class SearchEngineService {

	private static Logger logger = LoggerFactory.getLogger(SearchEngineService.class);

	@Autowired
	ApplicationContext context;
	
	SolrServer server;
	
	private static boolean loaded = false;
	
	public SearchEngineService() {}
	
	@PostConstruct
	public void init() throws ParserConfigurationException, IOException, SAXException{ 

		
		Resource solrHomeResource = context.getResource("classpath:META-INF/solr/");
		System.setProperty("solr.solr.home", solrHomeResource.getURL().getPath());
		logger.info("Embedded solr.solr.home is: " + solrHomeResource.getURL().toString());
		//SolrResourceLoader loader = new SolrResourceLoader(solrHomeResource.getURL().getPath(), context.getClassLoader());
		//CoreContainer coreContainer = new CoreContainer(loader);
		CoreContainer coreContainer = new CoreContainer();
		coreContainer.load();

		String coreInstanceDir = context.getResource("classpath:META-INF/solr/").getURL().getPath();
		Resource solrConfigResource = context.getResource("classpath:META-INF/solr/conf/solrconfig.xml");
		SolrConfig config = new SolrConfig(solrConfigResource.getFilename(), new InputSource(solrConfigResource.getInputStream()));

		Resource schemaResource = context.getResource("classpath:META-INF/solr/conf/schema.xml");
		IndexSchema schema = new IndexSchema(config, schemaResource.getFilename(), new InputSource(schemaResource.getInputStream()));

		File dataDir = new File("target/data/example/");
		if(dataDir.exists()){			
			FileUtils.deleteDirectory(dataDir);
		}
		
		String dataDirName = dataDir.getPath();
		
		CoreDescriptor cd = new CoreDescriptor(coreContainer, "example", coreInstanceDir);
		SolrCore core = new SolrCore("example",dataDirName,config, schema, cd);
		logger.info("XOXOXOXO Core config: " + core.getConfigResource());
		logger.info("XOXOXOXO Instance dir: " + core.getIndexDir());
		logger.info("XOXOXOXO Data dir: " + core.getDataDir());
		coreContainer.register(core, false);
		
		this.server = new EmbeddedSolrServer(coreContainer, "example");
		
	}
	
	public SolrServer getServer(String collection){
		return this.server;
	}
	
	public SolrResponse getResponse(SolrQuery query) throws Exception{
		return this.server.query(query);
	}

//	public List<Collection> getCollections();
//
//	public List<Field> getFields(Collection collection);
//
//	public boolean setField(Collection collection, Field field);
//
//	public boolean addCollection(Collection collection);
//
//	public SearchResult search();
//
//	public List<String> spell();
}
