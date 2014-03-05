/*******************************************************************************
 * Copyright Searchbox - http://www.searchbox.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.searchbox.framework.bootstrap;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.searchbox.collection.pubmed.PubmedCollection;
import com.searchbox.core.ref.Order;
import com.searchbox.core.ref.Sort;
import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.debug.SolrToString;
import com.searchbox.core.search.facet.FieldFacet;
import com.searchbox.core.search.paging.BasicPagination;
import com.searchbox.core.search.query.EdismaxQuery;
import com.searchbox.core.search.result.TemplatedHitList;
import com.searchbox.core.search.sort.FieldSort;
import com.searchbox.core.search.stat.BasicSearchStats;
import com.searchbox.engine.solr.EmbeddedSolr;
import com.searchbox.framework.domain.CollectionDefinition;
import com.searchbox.framework.domain.FieldAttributeDefinition;
import com.searchbox.framework.domain.PresetDefinition;
import com.searchbox.framework.domain.SearchElementDefinition;
import com.searchbox.framework.domain.SearchEngineDefinition;
import com.searchbox.framework.domain.Searchbox;
import com.searchbox.framework.domain.User;
import com.searchbox.framework.domain.UserRole;
import com.searchbox.framework.domain.UserRole.Role;
import com.searchbox.framework.event.SearchboxReady;
import com.searchbox.framework.repository.CollectionRepository;
import com.searchbox.framework.repository.SearchEngineRepository;
import com.searchbox.framework.repository.SearchboxRepository;
import com.searchbox.framework.service.UserService;

@Component
@org.springframework.core.annotation.Order(value=10000)
public class BootStrap implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger LOGGER = LoggerFactory.getLogger(BootStrap.class);
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private SearchboxRepository repository;
	
	@Autowired
	private SearchEngineRepository engineRepository;
	
	@Autowired
	private CollectionRepository collectionRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ApplicationEventPublisher publisher;
	
	
	
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
			
		LOGGER.info("Creating Default Users...");
		User system = userService.registerNewUserAccount("system", "password");
		User admin = userService.registerNewUserAccount("admin", "password");
		User user = userService.registerNewUserAccount("user", "password");
		
		LOGGER.info("Bootstraping application with default data...");
		
		/** The base Searchbox. */
		LOGGER.info("++ Creating pubmed searchbox");
		Searchbox searchbox = new Searchbox("pubmed","Embeded pubmed Demo");
		
		
		/** The embedded Solr SearchEngine */
		LOGGER.info("++ Creating Embedded Solr Engine");
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
			LOGGER.error("Could not set definition for SolrEmbededServer",e);
		}
		
		/** The base collection for searchbox */
		LOGGER.info("++ Creating pubmed Collection");
		CollectionDefinition collection = new CollectionDefinition(PubmedCollection.class,"pubmed");
		collection.setAutoStart(true);
		collection.setSearchEngine(engine);	
		collection = collectionRepository.save(collection);
		
		/** SearchAll preset */
		LOGGER.info("++ Creating Search All preset");
		PresetDefinition preset = new PresetDefinition(collection);
		preset.setAttributeValue("label","Search All");
		preset.setSlug("all");
		
		FieldAttributeDefinition idFieldAttr = new FieldAttributeDefinition(collection.getFieldDefinition("id"));
		idFieldAttr.setAttributeValue("id",true);
		preset.addFieldAttribute(idFieldAttr);
		
		FieldAttributeDefinition fieldAttr = new FieldAttributeDefinition(collection.getFieldDefinition("article-title"));
		fieldAttr.setAttributeValue("searchable",true);
		fieldAttr.setAttributeValue("label", "title");
		preset.addFieldAttribute(fieldAttr);
		
		FieldAttributeDefinition fieldAttr2 = new FieldAttributeDefinition(collection.getFieldDefinition("article-abstract"));
		fieldAttr2.setAttributeValue("searchable",true);
		preset.addFieldAttribute(fieldAttr2);
		
		/** Create & add a querydebug SearchComponent to the preset; */
		SearchElementDefinition querydebug = new SearchElementDefinition(SolrToString.class);
		preset.addSearchElement(querydebug);
		
		/** Create & add a query SearchComponent to the preset; */
		SearchElementDefinition query = new SearchElementDefinition(EdismaxQuery.class);
		preset.addSearchElement(query);

		/** Create & add a TemplatedHitLIst SearchComponent to the preset; */
		SearchElementDefinition templatedHitList = new SearchElementDefinition(TemplatedHitList.class);
		templatedHitList.setAttributeValue("titleField", "article-title");
		templatedHitList.setAttributeValue("idField", "id");
		templatedHitList.setAttributeValue("urlField", "article-title");
		templatedHitList.setAttributeValue("template", "<sbx:title hit=\"${hit}\" link=\"http://www.ncbi.nlm.nih.gov/pubmed/${hit.getId()}\"/>"+
														"<sbx:snippet value=\"${hit.fieldValues['article-abstract']}\"/>" +
														"<sbx:tagAttribute limit=\"3\" label=\"Author(s)\" values=\"${hit.fieldValues['author']}\"/>"
														);
		preset.addSearchElement(templatedHitList);

		/** Create & add another TemplatedHitLIst SearchComponent to the preset; 
		 * 	SearchElementType can be overriden
		 */
		SearchElementDefinition viewHit = new SearchElementDefinition(TemplatedHitList.class);
		viewHit.setType(SearchElement.Type.INSPECT);
		viewHit.setAttributeValue("titleField", "article-title");
		viewHit.setAttributeValue("idField", "id");
		viewHit.setAttributeValue("urlField", "article-title");
		viewHit.setAttributeValue("template", "Now we have a template dedicated to the view here...");
		preset.addSearchElement(viewHit);
		
		/** Create & add a FieldSort SearchComponent to the preset; */
		SearchElementDefinition fieldSort = new SearchElementDefinition(FieldSort.class);
		SortedSet<FieldSort.Value> sortFields = new TreeSet<FieldSort.Value>();
		sortFields.add(FieldSort.getRelevancySort());
		sortFields.add(new FieldSort.Value("Latest Article", "article-completion-date", Sort.DESC));
		sortFields.add(new FieldSort.Value("Latest Reviewed", "article-revision-date", Sort.DESC));
		fieldSort.setAttributeValue("values", sortFields);
		preset.addSearchElement(fieldSort);
				
		/** this is a simple hitlist with no templates	
		Create & add a HitLIst SearchComponent to the preset;
		SearchElementDefinition hitList = new SearchElementDefinition(HitList.class);
		hitList.setAttributeValue("titleField", "article-title");
		hitList.setAttributeValue("idField", "id");
		hitList.setAttributeValue("urlField", "article-title");
		ArrayList<String> fields = new ArrayList<String>();
		fields.add("article-abstract");
		fields.add("author");
		fields.add("publication-type");
		fields.add("article-completion-date");
		fields.add("article-revision-date");
		hitList.setAttributeValue("fields", fields);
		preset.addSearchElement(hitList);
		*/
		
		/** Create & add a basicSearchStat SearchComponent to the preset;*/
		SearchElementDefinition basicStatus = new SearchElementDefinition(BasicSearchStats.class);
		preset.addSearchElement(basicStatus);
		
		/** Create & add a facet to the preset. */
		SearchElementDefinition fieldFacet = new SearchElementDefinition(FieldFacet.class);
		fieldFacet.setAttributeValue("fieldName", "publication-type");
		fieldFacet.setLabel("Type");
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
		
		searchbox.addUserRole(new UserRole(system, Role.SYSTEM));
		searchbox.addUserRole(new UserRole(admin, Role.ADMIN));
		searchbox.addUserRole(new UserRole(user, Role.USER));
		repository.save(searchbox);
				
		/** Making another Searchbox for testing and UI. */
		Searchbox anotherSearchbox = new Searchbox("custom","My Searchbox");
		repository.save(anotherSearchbox);

		LOGGER.info("Bootstraping application with default data... done");
		
		
		}
		
		LOGGER.info("Starting all your engine");
		Iterator<SearchEngineDefinition> engineDefinitions = engineRepository.findAll().iterator();
		
		while(engineDefinitions.hasNext()){
			SearchEngineDefinition engineDefinition = engineDefinitions.next();
			LOGGER.info("++ Starting SearchEngine: " + engineDefinition.getName());
			engineDefinition.getInstance().init();
		}
		
		LOGGER.info("****************************************************");
		LOGGER.info("*                  Welcome                         *");
		LOGGER.info("****************************************************");
		LOGGER.info("*                                                  *");
		LOGGER.info("*   __                     _     _                 *");
		LOGGER.info("*  / _\\ ___  __ _ _ __ ___| |__ | |__   _____  __  *");
		LOGGER.info("*  \\ \\ / _ \\/ _` | '__/ __| '_ \\| '_ \\ / _ \\ \\/ /  *");
		LOGGER.info("*  _\\ \\  __/ (_| | | | (__| | | | |_) | (_) >  <   *");
		LOGGER.info("*  \\__/\\___|\\__,_|_|  \\___|_| |_|_.__/ \\___/_/\\_\\  *");
		LOGGER.info("*                                                  *");
		
		LOGGER.info("                               __ _");
		LOGGER.info("             ___  _ __  _ __  / _(_)_ __");
		LOGGER.info("            / _ \\| '_ \\| '_ \\| |_| | '_ \\");
		LOGGER.info("           | (_) | |_) | |_) |  _| | | | |");
		LOGGER.info("            \\___/| .__/| .__/|_| |_|_| |_|");
		LOGGER.info("                 |_|   |_|");
		LOGGER.info("*                                                  *");
		LOGGER.info("****************************************************");
		LOGGER.info("*                                                  *");
		LOGGER.info("****************************************************");
		LOGGER.info("*                                                  *");
		LOGGER.info("*  Your searchbox is running in DEMO mode and      *");
		LOGGER.info("*  sample data from the PUBMED directory has been  *");
		LOGGER.info("*  automatically added.                            *");
		LOGGER.info("*                                                  *");
		LOGGER.info("*  visit: http://localhost:8080/searchbox          *");
		LOGGER.info("*  admin: http://localhost:8080/searchbox/admin    *");
		LOGGER.info("*                                                  *");
		LOGGER.info("****************************************************");
		
		publisher.publishEvent(new SearchboxReady(this));
		
	}
}
