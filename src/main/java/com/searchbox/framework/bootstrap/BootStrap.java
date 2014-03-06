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

import com.searchbox.collection.oppfin.OppfinTopicCollection;
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
		
		LOGGER.info("Bootstraping application with oppfin data...");
		
		/** The base Searchbox. */
		LOGGER.info("++ Creating oppfin searchbox");
		Searchbox searchbox = new Searchbox("oppfin","Opportunity Finder Searchbox");
		
		
		/** The embedded Solr SearchEngine */
		LOGGER.info("++ Creating Embedded Solr Engine");
		SearchEngineDefinition engine = null;
		try {
			engine = new SearchEngineDefinition(EmbeddedSolr.class,"embedded Solr");
			engine.setAttributeValue("coreName", "oppfin");
			engine.setAttributeValue("solrHome",context.getResource("classpath:solr/").getURL().getPath());
			engine.setAttributeValue("solrConfig",context.getResource("classpath:solr/conf/solrconfig.xml").getURL().getPath());
			engine.setAttributeValue("solrSchema",context.getResource("classpath:solr/conf/schema.xml").getURL().getPath());
			engine.setAttributeValue("dataDir", "target/data/oppfin/");
			engine = engineRepository.save(engine);
		} catch (Exception e){
			LOGGER.error("Could not set definition for SolrEmbededServer",e);
		}
		
		/** The base collection for searchbox */
		LOGGER.info("++ Creating oppfin Collection");
		CollectionDefinition collection = new CollectionDefinition(OppfinTopicCollection.class,"oppfin");
		collection.setAutoStart(false);
		collection.setSearchEngine(engine);	
		collection = collectionRepository.save(collection);
		
		/**
		 * - Search All
			- Project Funding (Topics)
			- Cooperations
			- Funded projects (Mêmes données qu'avant, même layout)
			
			
			
			Search All
			- Facets
				- Programme (H2020, ...)
				- Opportunity Type (docType)
			- Hitlist
				- Filtres: All but funded projects
				- Pas besoin de mettre "Contact information"
				- Normalement on a plus de mouse over
				
				
			Project Funding (Topics)
			- Facets
				- Call Identifier
				- Deadline (list of months)
				- Flags
				
			- Hitlist
				- Filtres: docType=topic & callDeadline >= NOW
				- HitList
					- Title
					- Description
					- Tags: callDeadline, callIdentifier, callBudget (à confirmer par Francesco)
				- DetailView
					- Title
					- FullDescription (HTML si possible)
					- Left panel
						- Topic Identifier: topicIdentifier
						- Call Identifier: callIdentifier
						- Call Deadline: callDeadline (MMM DD, YYYY)
						- Further information:
							- Call (link CallIdentifier)
							- Topic (link topicIdentifier)
							
			Cooperations (EEN connector / même layout, même template)
				- Mettre crawler à jour et faire layout en fonction
		 */
		
		
		/**
		 *  Topic preset 
		 */
		
		/**
		 * From the crawler
		 * doc.addField("id", topicIdentifier );
				doc.addField("title", (String)topicObject.get("title"));
				doc.addField("descriptionRaw", topicDetailRaw );
				doc.addField("descriptionHtml", topicDetailHtml );
				doc.addField("docType", "Topic H2020");
				doc.addField("programme", "H2020");
				
				doc.addField("tags", topicObject.get("tags").toString());
				doc.addField("flags", topicObject.get("flags").toString());

				doc.addField("callTitle", (String)topicObject.get("callTitle"));
				doc.addField("callIdentifier", (String)topicObject.get("callIdentifier"));
				doc.addField("callDeadline", (Long)topicObject.get("callDeadline"));
				doc.addField("callStatus", (String)topicObject.get("callStatus"));
		 */
		LOGGER.info("++ Creating Topic preset");
		PresetDefinition presetTopic = new PresetDefinition(collection);
		presetTopic.setAttributeValue("label","Topic");
		presetTopic.setSlug("topic");
		
		FieldAttributeDefinition idFieldAttr = new FieldAttributeDefinition(collection.getFieldDefinition("id"));
		idFieldAttr.setAttributeValue("id",true);
		presetTopic.addFieldAttribute(idFieldAttr);
		
		FieldAttributeDefinition fieldAttr = new FieldAttributeDefinition(collection.getFieldDefinition("title"));
		fieldAttr.setAttributeValue("searchable",true);
		fieldAttr.setAttributeValue("label", "title");
		presetTopic.addFieldAttribute(fieldAttr);
		
		FieldAttributeDefinition fieldAttr2 = new FieldAttributeDefinition(collection.getFieldDefinition("descriptionRaw"));
		fieldAttr2.setAttributeValue("searchable",true);
		fieldAttr.setAttributeValue("label", "description");
		presetTopic.addFieldAttribute(fieldAttr2);
		
		/** Create & add a querydebug SearchComponent to the preset; */
		SearchElementDefinition querydebug = new SearchElementDefinition(SolrToString.class);
		presetTopic.addSearchElement(querydebug);
		
		/** Create & add a query SearchComponent to the preset; */
		SearchElementDefinition query = new SearchElementDefinition(EdismaxQuery.class);
		presetTopic.addSearchElement(query);

		/** Create & add a TemplatedHitLIst SearchComponent to the preset; */
		SearchElementDefinition templatedHitList = new SearchElementDefinition(TemplatedHitList.class);
		templatedHitList.setAttributeValue("titleField", "title");
		templatedHitList.setAttributeValue("idField", "id");
		templatedHitList.setAttributeValue("urlField", "title");
		templatedHitList.setAttributeValue("template", "<sbx:title hit=\"${hit}\"/>"+
														"<sbx:snippet value=\"${hit.fieldValues['descriptionRaw']}\"/>" +
														"<sbx:tagAttribute limit=\"1\" label=\"Deadline\" values=\"${hit.fieldValues['callDeadline']}\"/>" +
														"<sbx:tagAttribute limit=\"1\" label=\"Call\" values=\"${hit.fieldValues['callIdentifier']}\"/>"
														);
		presetTopic.addSearchElement(templatedHitList);

		/** Create & add another TemplatedHitLIst SearchComponent to the preset; 
		 * 	SearchElementType can be overriden
		 */
		SearchElementDefinition viewHit = new SearchElementDefinition(TemplatedHitList.class);
		viewHit.setType(SearchElement.Type.INSPECT);
		viewHit.setAttributeValue("titleField", "title");
		viewHit.setAttributeValue("idField", "id");
		viewHit.setAttributeValue("urlField", "title");
		viewHit.setAttributeValue("template", "Here we should display Title "+
					"- FullDescription (HTML si possible)"+
					"- Left panel"+
					"	- Topic Identifier: topicIdentifier"+
					"	- Call Identifier: callIdentifier"+
					"	- Call Deadline: callDeadline (MMM DD, YYYY)"+
					"	- Further information:"+
					"		- Call (link CallIdentifier)"+
					"		- Topic (link topicIdentifier)");
		presetTopic.addSearchElement(viewHit);
		
		/** Create & add a FieldSort SearchComponent to the preset; */
		SearchElementDefinition fieldSort = new SearchElementDefinition(FieldSort.class);
		SortedSet<FieldSort.Value> sortFields = new TreeSet<FieldSort.Value>();
		sortFields.add(FieldSort.getRelevancySort());
		sortFields.add(new FieldSort.Value("By Deadline <span class=\"pull-right glyphicon glyphicon-chevron-down\"></span>", "callDeadline", Sort.DESC));
		sortFields.add(new FieldSort.Value("Deadline ASC", "callDeadline", Sort.ASC));
		fieldSort.setAttributeValue("values", sortFields);
		presetTopic.addSearchElement(fieldSort);
				
		
		/** Create & add a basicSearchStat SearchComponent to the preset;*/
		SearchElementDefinition basicStatus = new SearchElementDefinition(BasicSearchStats.class);
		presetTopic.addSearchElement(basicStatus);
		
		/** Create & add a facet to the presetTopic. */
		SearchElementDefinition callFacet = new SearchElementDefinition(FieldFacet.class);
		callFacet.setAttributeValue("field", collection.getFieldDefinition("callIdentifier").getInstance());
		callFacet.setLabel("Call");
		callFacet.setAttributeValue("order", Order.BY_VALUE);
		callFacet.setAttributeValue("sort", Sort.DESC);
		presetTopic.addSearchElement(callFacet);
		
		/** Ideally this is a range facet. We agreed that for now it will be a list of months 
		 *  For instance(March 14, April 14, May 14, June 14, ...) */
		SearchElementDefinition deadlineFacet = new SearchElementDefinition(FieldFacet.class);
		deadlineFacet.setAttributeValue("fieldName", collection.getFieldDefinition("callDeadline").getInstance());
		deadlineFacet.setLabel("Deadline");
		deadlineFacet.setAttributeValue("order", Order.BY_VALUE);
		deadlineFacet.setAttributeValue("sort", Sort.DESC);
		presetTopic.addSearchElement(deadlineFacet);
		
		SearchElementDefinition flagFacet = new SearchElementDefinition(FieldFacet.class);
		flagFacet.setAttributeValue("fieldName", collection.getFieldDefinition("flags").getInstance());
		flagFacet.setLabel("Flags");
		flagFacet.setAttributeValue("order", Order.BY_VALUE);
		flagFacet.setAttributeValue("sort", Sort.DESC);
		presetTopic.addSearchElement(flagFacet);
		
		
		SearchElementDefinition pagination = new SearchElementDefinition(BasicPagination.class);
		presetTopic.addSearchElement(pagination);
		
		searchbox.addPresetDefinition(presetTopic);		
		
		searchbox.addUserRole(new UserRole(system, Role.SYSTEM));
		searchbox.addUserRole(new UserRole(admin, Role.ADMIN));
		searchbox.addUserRole(new UserRole(user, Role.USER));
		repository.save(searchbox);
				
		

		LOGGER.info("Bootstraping application with oppfin data... done");
		
		
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
		LOGGER.info("*                             __ _");
		LOGGER.info("*           ___  _ __  _ __  / _(_)_ __");
		LOGGER.info("*          / _ \\| '_ \\| '_ \\| |_| | '_ \\");
		LOGGER.info("*         | (_) | |_) | |_) |  _| | | | |");
		LOGGER.info("*          \\___/| .__/| .__/|_| |_|_| |_|");
		LOGGER.info("*               |_|   |_|");
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
