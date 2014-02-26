package com.searchbox.framework;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.searchbox.core.ref.Order;
import com.searchbox.core.ref.Sort;
import com.searchbox.core.search.debug.SolrToString;
import com.searchbox.core.search.facet.FieldFacet;
import com.searchbox.core.search.paging.BasicPagination;
import com.searchbox.core.search.query.EdismaxQuery;
import com.searchbox.core.search.result.TemplatedHitList;
import com.searchbox.core.search.sort.FieldSort;
import com.searchbox.core.search.stat.BasicSearchStats;
import com.searchbox.engine.solr.EmbeddedSolr;
import com.searchbox.framework.config.RootConfiguration;
import com.searchbox.framework.domain.CollectionDefinition;
import com.searchbox.framework.domain.FieldAttributeDefinition;
import com.searchbox.framework.domain.FieldDefinition;
import com.searchbox.framework.domain.PresetDefinition;
import com.searchbox.framework.domain.SearchElementDefinition;
import com.searchbox.framework.domain.SearchEngineDefinition;
import com.searchbox.framework.domain.Searchbox;
import com.searchbox.framework.repository.CollectionRepository;
import com.searchbox.framework.repository.SearchEngineRepository;
import com.searchbox.framework.repository.SearchboxRepository;
import com.searchbox.framework.service.SearchEngineService;

@Component
@org.springframework.core.annotation.Order(value=10000)
public class BootStrap implements ApplicationListener<ContextRefreshedEvent> {

	private static Logger logger = LoggerFactory.getLogger(BootStrap.class);
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private SearchboxRepository repository;
	
	@Autowired
	private SearchEngineRepository engineRepository;
	
	@Autowired
	private CollectionRepository collectionRepository;
	
	@Autowired
	private SearchEngineService searchEngineService;
	
	private static boolean BOOTSTRAPED = false;
	
	private static boolean defaultData = true;

	@Override
	@Transactional
	synchronized public void onApplicationEvent(ContextRefreshedEvent event) {

		if(BOOTSTRAPED){
			return;
		} 
		
		BOOTSTRAPED = true;
		
		if(defaultData){
		logger.info("Bootstraping application with default data...");
		
		//The base Searchbox.
		logger.info("++ Creating pubmed searchbox");
		Searchbox searchbox = new Searchbox("pubmed","Embeded pubmed Demo");
		
		
		//The embedded Solr SearchEngine
		logger.info("++ Creating Embedded Solr Engine");
		SearchEngineDefinition engine = null;
		try {
			engine = new SearchEngineDefinition(EmbeddedSolr.class,"embedded Solr");
			engine.setAttributeValue("coreName", "pubmed");
			engine.setAttributeValue("solrHome",context.getResource("classpath:solr/").getURL().getPath());
			engine.setAttributeValue("solrConfig",context.getResource("classpath:solr/conf/solrconfig.xml").getURL().getPath());
			engine.setAttributeValue("solrSchema",context.getResource("classpath:solr/conf/schema.xml").getURL().getPath());
			engine.setAttributeValue("dataDir", "target/data/pubmed/");
			engine = engineRepository.save(engine);
		} catch (Exception e){
			logger.error("Could not set definition for SolrEmbededServer",e);
		}
		
		//The base collection for searchbox
		logger.info("++ Creating pubmed Collection");
		CollectionDefinition collection = new CollectionDefinition("pubmed", engine);	
		Set<FieldDefinition> collectionFields = new HashSet<FieldDefinition>();
		collectionFields.add(FieldDefinition.StringFieldDef("id"));
		collectionFields.add(FieldDefinition.StringFieldDef("article-title"));
		collectionFields.add(FieldDefinition.StringFieldDef("article-abstract"));
		collection.setFieldDefinitions(collectionFields);		
		collection = collectionRepository.save(collection);
		
		//SearchAll preset
		logger.info("++ Creating Search All preset");
		PresetDefinition preset = new PresetDefinition(collection);
		preset.setAttributeValue("label","Search All");
		preset.setSlug("all");
		FieldAttributeDefinition fieldAttr = new FieldAttributeDefinition(collection.getFieldDefinition("article-title"));
		fieldAttr.setAttributeValue("searchable",true);
		preset.addFieldAttribute(fieldAttr);
		FieldAttributeDefinition fieldAttr2 = new FieldAttributeDefinition(collection.getFieldDefinition("article-abstract"));
		fieldAttr2.setAttributeValue("searchable",true);
		preset.addFieldAttribute(fieldAttr2);
		
		//Create & add a querydebug SearchComponent to the preset;
		SearchElementDefinition querydebug = new SearchElementDefinition(SolrToString.class);
		preset.addSearchElement(querydebug);
		
		//Create & add a query SearchComponent to the preset;
		SearchElementDefinition query = new SearchElementDefinition(EdismaxQuery.class);
		preset.addSearchElement(query);

		//Create & add a TemplatedHitLIst SearchComponent to the preset;
		SearchElementDefinition templatedHitList = new SearchElementDefinition(TemplatedHitList.class);
		templatedHitList.setAttributeValue("titleField", "article-title");
		templatedHitList.setAttributeValue("idField", "id");
		templatedHitList.setAttributeValue("urlField", "article-title");
		templatedHitList.setAttributeValue("template", "<a href=\"http://www.ncbi.nlm.nih.gov/pubmed/${hit.getId()}\"><h5 class=\"result-title\">${hit.getTitle()}</h5></a>"+
														"<div>${hit.fieldValues['article-abstract']}</div>");
		preset.addSearchElement(templatedHitList);

		//Create & add a FieldSort SearchComponent to the preset;
		SearchElementDefinition fieldSort = new SearchElementDefinition(FieldSort.class);
		SortedSet<FieldSort.Value> sortFields = new TreeSet<FieldSort.Value>();
		sortFields.add(FieldSort.getRelevancySort());
		sortFields.add(new FieldSort.Value("Latest Article", "article-completion-date", Sort.DESC));
		sortFields.add(new FieldSort.Value("Latest Reviewed", "article-revision-date", Sort.DESC));
		fieldSort.setAttributeValue("values", sortFields);
		preset.addSearchElement(fieldSort);
				
				
		//Create & add a HitLIst SearchComponent to the preset;
//		SearchElementDefinition hitList = new SearchElementDefinition(HitList.class);
//		hitList.setAttributeValue("titleField", "article-title");
//		hitList.setAttributeValue("idField", "id");
//		hitList.setAttributeValue("urlField", "article-title");
//		ArrayList<String> fields = new ArrayList<String>();
//		fields.add("article-abstract");
//		fields.add("author");
//		fields.add("publication-type");
//		fields.add("article-completion-date");
//		fields.add("article-revision-date");
//		hitList.setAttributeValue("fields", fields);
//		preset.addSearchElement(hitList);
		
		//Create & add a basicSearchStat SearchComponent to the preset;
		SearchElementDefinition basicStatus = new SearchElementDefinition(BasicSearchStats.class);
		preset.addSearchElement(basicStatus);
		
		//Create & add a facet to the preset.
		SearchElementDefinition fieldFacet = new SearchElementDefinition(FieldFacet.class);
		fieldFacet.setAttributeValue("fieldName", "publication-type");
		fieldFacet.setAttributeValue("label", "Type");
		fieldFacet.setAttributeValue("order", Order.BY_VALUE);
		fieldFacet.setAttributeValue("sort", Sort.DESC);
		preset.addSearchElement(fieldFacet);
		
		
		SearchElementDefinition pagination = new SearchElementDefinition(BasicPagination.class);
		preset.addSearchElement(pagination);
		
		searchbox.addPresetDefinition(preset);
		
		PresetDefinition articles = new PresetDefinition(collection);
		articles.setAttributeValue("label","Articles");
		articles.setSlug("articles");
		searchbox.addPresetDefinition(articles);

		PresetDefinition press = new PresetDefinition(collection);
		press.setAttributeValue("label","Press");
		press.setSlug("press");
		searchbox.addPresetDefinition(press);
		
		repository.save(searchbox);
		
		//Making another Searchbox for testing and UI.
		Searchbox anotherSearchbox = new Searchbox("custom","My Searchbox");
		repository.save(anotherSearchbox);
		
		logger.info("Bootstraping application with default data... done");

		}
		
		logger.info("****************************************************");
		logger.info("*                  Welcome                         *");
		logger.info("****************************************************");
		logger.info("*                                                  *");
		logger.info("*   __                     _     _                 *");
		logger.info("*  / _\\ ___  __ _ _ __ ___| |__ | |__   _____  __  *");
		logger.info("*  \\ \\ / _ \\/ _` | '__/ __| '_ \\| '_ \\ / _ \\ \\/ /  *");
		logger.info("*  _\\ \\  __/ (_| | | | (__| | | | |_) | (_) >  <   *");
		logger.info("*  \\__/\\___|\\__,_|_|  \\___|_| |_|_.__/ \\___/_/\\_\\  *");
		logger.info("*                                                  *");
		logger.info("*                                                  *");
		logger.info("****************************************************");
		logger.info("*                                                  *");
		logger.info("****************************************************");
		logger.info("*                                                  *");
		logger.info("*  Your searchbox is running in DEMO mode and      *");
		logger.info("*  sample data from the PUBMED directory has been  *");
		logger.info("*  automatically added.                            *");
		logger.info("*                                                  *");
		logger.info("*  visit: http://localhost:8080/searchbox          *");
		logger.info("*  admin: http://localhost:8080/searchbox/admin    *");
		logger.info("*                                                  *");
		logger.info("****************************************************");
		
		logger.info("Starting all your engine");
		Iterator<SearchEngineDefinition> engineDefinitions = engineRepository.findAll().iterator();
		
		while(engineDefinitions.hasNext()){
			SearchEngineDefinition engineDefinition = engineDefinitions.next();
			logger.info("++ Starting SearchEngine: " + engineDefinition.getName());
			searchEngineService.load(engineDefinition.getInstance());
		}
	}

	@SuppressWarnings("resource")
	public static void main(String... args){
		@SuppressWarnings("unused")
		AnnotationConfigApplicationContext context = 
				new AnnotationConfigApplicationContext(RootConfiguration.class);
		
	}
}
