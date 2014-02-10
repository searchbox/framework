package com.searchbox.service;

import java.util.List;

import org.apache.solr.core.CoreContainer;
import org.springframework.stereotype.Service;

import com.searchbox.core.search.SearchResult;
import com.searchbox.domain.Collection;
import com.searchbox.domain.Field;

@Service
public class SearchEngineService {
	
	public SearchEngineService(){
//		CoreContainer coreContainer = new CoreContainer(solrHome.toString(), configFile);
//		SolrServer solrServer = new EmbeddedSolrServer(coreContainer, "Your-Core-Name-in-solr.xml");
//		SolrQuery query = new SolrQuery("Your Solr Query");
//		QueryResponse rsp = solrServer.query(query);
//		SolrDocumentList docs = rsp.getResults();
//		Iterator<SolrDocument> i = docs.iterator();
//		while (i.hasNext()) {
//		      System.out.println(i.next().toString());
//		}
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
