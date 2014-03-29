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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.searchbox.collection.oppfin.CordisCollection;
import com.searchbox.collection.oppfin.EENCollection;
import com.searchbox.collection.oppfin.IdealISTCollection;
import com.searchbox.collection.oppfin.TopicCollection;
import com.searchbox.core.dm.MultiCollection;
import com.searchbox.core.engine.SearchEngine;
import com.searchbox.core.ref.Order;
import com.searchbox.core.ref.Sort;
import com.searchbox.core.search.debug.SolrToString;
import com.searchbox.core.search.facet.FieldFacet;
import com.searchbox.core.search.paging.BasicPagination;
import com.searchbox.core.search.query.EdismaxQuery;
import com.searchbox.core.search.result.TemplateElement;
import com.searchbox.core.search.sort.FieldSort;
import com.searchbox.core.search.stat.BasicSearchStats;
import com.searchbox.engine.solr.EmbeddedSolr;
import com.searchbox.framework.domain.Role;
import com.searchbox.framework.event.SearchboxReady;
import com.searchbox.framework.model.CollectionEntity;
import com.searchbox.framework.model.SearchEngineEntity;
import com.searchbox.framework.model.SearchboxEntity;
import com.searchbox.framework.model.UserEntity;
import com.searchbox.framework.repository.CollectionRepository;
import com.searchbox.framework.repository.SearchEngineRepository;
import com.searchbox.framework.repository.SearchboxRepository;
import com.searchbox.framework.repository.UserRepository;
import com.searchbox.framework.service.UserService;

@Component
@Configuration
@org.springframework.core.annotation.Order(value = 10000)
@Transactional
public class BootStrap implements ApplicationListener<ContextRefreshedEvent> {

  private static final Logger LOGGER = LoggerFactory.getLogger(BootStrap.class);
  
  @Resource
  Environment env;

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
  UserRepository userRepository;

  @Autowired
  ApplicationEventPublisher publisher;

  private static boolean BOOTSTRAPED = false;

  private static boolean defaultData = true;

  @Override
  synchronized public void onApplicationEvent(ContextRefreshedEvent event) {
    doBootStrap();
  }

  public void doBootStrap() {

    if (BOOTSTRAPED) {
      return;
    }

    BOOTSTRAPED = true;

    if (defaultData) {
      
      /** 
       * The embedded Solr SearchEngine
       */
      LOGGER.info("++ Creating Embedded Solr Engine");
      SearchEngineEntity<?> engine = null;
      try {
        
        String className = env.getProperty("searchengine.class", EmbeddedSolr.class.getName());
        Class<SearchEngine<?,?>> clazz = (Class<SearchEngine<?, ?>>) Class.forName(className);
        engine = new SearchEngineEntity<>()
            .setClazz(clazz)
            .setAttribute(env.getProperty("searchengine.prop","solrHome"),
                env.getProperty("searchengine.prop.value",
                    context.getResource("classpath:solr/").getURL().getPath()));

        engine = engineRepository.save(engine);
      } catch (Exception e) {
        LOGGER.error("Could not set definition for SolrEmbededServer", e);
      }

      LOGGER.info("Creating Default Users...");
      UserEntity system = userService.registerNewUserAccount("system@searchbox.com", "password");
      UserEntity admin = userService.registerNewUserAccount("admin@searchbox.com", "password");
      UserEntity user = userService.registerNewUserAccount("user@searchbox.com", "password");
      
      system = userService.addRole(system, Role.SYSTEM, Role.ADMIN, Role.USER);
      admin = userService.addRole(admin, Role.ADMIN, Role.USER);
      user = userService.addRole(user, Role.USER);
      
      
      userRepository.save(
          new UserEntity()
            .setEmail("jonathan@xtremsoft.com")
            .setFirstName("Jonathan")
            .setRoles(Arrays.asList(new Role[]{Role.ADMIN, Role.SYSTEM, Role.USER})));
      
      userRepository.save(
          new UserEntity()
            .setEmail("stephane@gamard.net")
            .setFirstName("stephane")
            .setRoles(Arrays.asList(new Role[]{Role.ADMIN, Role.SYSTEM, Role.USER})));
      

      LOGGER.info("Bootstraping application with oppfin data...");

      /** 
       * The base Searchbox.
       */
      LOGGER.info("++ Creating oppfin searchbox");
      SearchboxEntity searchbox = new SearchboxEntity()
        .setSlug("oppfin")
        .setName("Opportunity Finder Searchbox")
        .setLogo("/assets/images/oppfin-logo.png");


      List<String> lang = new ArrayList<String>();
      lang.add("en");

      /** 
       * 
       * Oppfin Collections
       *  
       */
      LOGGER.info("++ Creating oppfin Topic Collection");
      CollectionEntity<?> topicsCollection = new CollectionEntity<>()
        .setClazz(TopicCollection.class)
        .setName("H2020Topics")
        .setAutoStart(false)
        .setIdFieldName("topicIdentifier")
        .setSearchEngine(engine);
      topicsCollection = collectionRepository.save(topicsCollection);
      
      
      LOGGER.info("++ Creating oppfin EEN Collection");
      CollectionEntity<?> eenCollection = new CollectionEntity<>()
          .setClazz(EENCollection.class)
          .setName("eenCooperations")
          .setAutoStart(false)
          .setIdFieldName("eenReferenceExternal")
          .setSearchEngine(engine);
      eenCollection = collectionRepository.save(eenCollection);
      
      /** The base collection for idealist */
      LOGGER.info("++ Creating oppfin IDEALIST Collection");
      CollectionEntity<?> idealistCollection = new CollectionEntity<>()
          .setClazz(IdealISTCollection.class)
          .setName("idealistCooperations")
          .setSearchEngine(engine)
          .setAutoStart(false)
          .setIdFieldName("uid");
      idealistCollection = collectionRepository.save(idealistCollection);
      
      
      LOGGER.info("++ Creating oppfin CORDIS Collection");
      CollectionEntity<?> cordisCollection = new CollectionEntity<>()
        .setClazz(CordisCollection.class)
        .setIdFieldName("cordisId")
        .setName("fundedProjects")
        .setAutoStart(false)
        .setSearchEngine(engine);
      cordisCollection = collectionRepository.save(cordisCollection);
      
      
      searchbox.newPreset().setLabel("Search All")
      .setDescription("All Collections")
      .setSlug("all")
      .setCollection(collectionRepository.save(
            new CollectionEntity<>()
            .setClazz(MultiCollection.class)
            .setName("all")
            .setSearchEngine(engine)
            .setAttribute("collections", 
                Arrays.asList(new String[]{
                    topicsCollection.getName(),
                    eenCollection.getName(),
                    idealistCollection.getName(),
                    cordisCollection.getName()
                }))))      
            .addQueryElement()
            .addFieldFacet("Source", "docSource")
            .addStatElement()
            .addPagingElement()
            .addDebugElement()
   
            
      /** 
       * 
       * Topic Preset
       *  
       */
      //Create a new preset in searchbox
            .newChildPreset(true, TemplateElement.class)
            .setLabel("Project Funding")
        .setDescription("Project Funding (open calls)")
        .setSlug("funding")
        
        .setCollection(topicsCollection)
      
        /**
         * Setting up fieldAttributes for preset
         */
        .newFieldAttribute("topicIdentifier")
          .setAttribute("searchable", true)
          .setAttribute("spelling", true)
          .setAttribute("suggestion", true)
          .setAttribute("label", "Topic ID")
          .end()
          
        .newFieldAttribute("callIdentifier")
          .setAttribute("searchable", true)
          .setAttribute("spelling", true)
          .setAttribute("suggestion", true)
          .setAttribute("label", "Call ID")
          .end()
          
        .newFieldAttribute("topicTitle")
          .setAttribute("searchable", true)
          .setAttribute("spelling", true)
          .setAttribute("suggestion", true)
          .setAttribute("highlight", true)
          .setAttribute("label", "title")
          .setAttribute("lang", lang)
          .end()
          
        .newFieldAttribute("description","topicDescriptionRaw")
          .setAttribute("searchable", true)
          .setAttribute("spelling", true)
          .setAttribute("suggestion", true)
          .setAttribute("highlight", true)
          .setAttribute("label", "description")
          .setAttribute("lang", lang)
          .end()
          
        .newFieldAttribute("callDeadline")
          .setAttribute("sortable", true)
          .end()
          
        /**
         *  Creating the SearchElements for preset 
         */
        .newSearchElement()
          .setClazz(SolrToString.class).end()
          
        .newSearchElement()
          .setClazz(EdismaxQuery.class).end()
          
        .newSearchElement()
          .setClazz(TemplateElement.class)
          .setAttribute("titleField", "topicTitle")
          .setAttribute("idField", topicsCollection.getIdFieldName())
          .setAttribute("templateFile", "/WEB-INF/templates/oppfin/_topicHit.jspx")
          .setProcess("search")
          .end()
          
        .newSearchElement()
          .setClazz(TemplateElement.class)
          .setLabel("leftCol")
          .setProcess("view")
          .setAttribute("titleField", "topicTitle")
          .setAttribute("idField", topicsCollection.getIdFieldName())
          .setAttribute("templateFile", "/WEB-INF/templates/oppfin/_topicViewMeta.jspx")
	  .end()
	  
	.newSearchElement()
	  .setClazz(TemplateElement.class)
          .setLabel("body")
          .setProcess("view")
          .setAttribute("titleField", "topicTitle")
          .setAttribute("idField", topicsCollection.getIdFieldName())
          .setAttribute("templateFile", "/WEB-INF/templates/oppfin/_topicView.jspx")
          .end()
          
        .newSearchElement()
          .setClazz(FieldSort.class)
          .setAttribute("values",  new TreeSet<FieldSort.Value>(
              Arrays.asList(new FieldSort.Value[]{
                FieldSort.getRelevancySort(),
                new FieldSort.Value(
                  "By Deadline <span class=\"pull-right glyphicon glyphicon-chevron-down\"></span>",
                  "callDeadline", Sort.ASC)
                }
              )))
          .end()
        
       .newSearchElement()
         .setClazz(BasicSearchStats.class)
         .setLabel("Basic Stats")
         .setProcess("search")
         .end()

       .newSearchElement()
         .setClazz(FieldFacet.class)
         .setLabel("Call")
         .setAttribute("fieldName", "callIdentifier")
         .setAttribute("order", Order.BY_VALUE)
         .setAttribute("sort", Sort.DESC)
         .end()

      /**
       * Ideally this is a range facet. We agreed that for now it will be a list
       * of months For instance(March 14, April 14, May 14, June 14, ...)
       */
       .newSearchElement()
         .setClazz(FieldFacet.class)
         .setLabel("Deadline")
         .setAttribute("fieldName","callDeadline")
         .setAttribute("order", Order.BY_VALUE)
         .setAttribute("sort", Sort.DESC)
         .end()
         
       .newSearchElement()
         .setClazz(FieldFacet.class)
         .setAttribute("fieldName", "topicFlags")
         .setLabel("Flags")
         .setAttribute("order", Order.BY_VALUE)
         .setAttribute("sort", Sort.DESC)
         .end()
         
       .newSearchElement()
         .setClazz(BasicPagination.class)
         .end()
       .endChild()
       
       
       
      
      
      
      /**
       * 
       * Cooperation preset
       * 
       * 
       */
      
   
      
      .newChildPreset(true, TemplateElement.class)
        .setCollection(collectionRepository.save(
            new CollectionEntity<>()
            .setClazz(MultiCollection.class)
            .setName("cooperations")
            .setSearchEngine(engine)
            .setAttribute("collections", 
                Arrays.asList(new String[]{
                    eenCollection.getName(),
                    idealistCollection.getName()
                }))))
        .setSlug("cooperations")
        .setLabel("Cooperations")
        .addQueryElement()
        .addStatElement()
        .addFieldFacet("Cooperation Source", "docSource")
        .addFieldFacet("EEN Type", "eenReferenceType")
        .addFieldFacet("Keyword", "eenKeywordTechnologiesLabel")
        .addFieldFacet("Partner Country", "eenCompanyCountryLabel")
        
        //TODO Steph: Check why this is failing.
        /*.addSortableFieldAttribute("Published", "eenDatumSubmit")
        .addSortableFieldAttribute("Updated", "eenDatumUpdate")
        .addSortableFieldAttribute("Deadline", "eenDatumDeadline")*/
        
        .addPagingElement()
        .addDebugElement()
         
      /**
       * 
       * EEN preset
       * 
       * 
       */
     
   
      //LOGGER.info("++ Creating Cooperation preset");
      //searchbox.newPreset()
      .newChildPreset(true, FieldFacet.class, TemplateElement.class)
        .setCollection(eenCollection)
        .setDescription("EEN cooperations")
        .setLabel("EEN")
        .setSlug("een")
        .setVisible(false)
         
        .addSortableFieldAttribute("Published", "eenDatumSubmit")
        .addSortableFieldAttribute("Updated", "eenDatumUpdate")
        .addSortableFieldAttribute("Deadline", "eenDatumDeadline")

        .newFieldAttribute("Title","eenContentTitle")
          .setSearchanble(true)
          .setHighlight(true)
          .setSpelling(true)
          .setSuggestion(true)
          .setLanguages(lang)
          .end()
          
        .newFieldAttribute("Summary","eenContentSummary")
          .setSearchanble(true)
          .setHighlight(true)
          .setSpelling(true)
          .setSuggestion(true)
          .setLanguages(lang)
          .end()
          
        .newFieldAttribute("Keyword","eenKeywordTechnologiesLabel")
          .setSearchanble(true)
          .setSpelling(true)
          .setHighlight(true)
          .setLanguages(lang)
          .end()
          
        .newFieldAttribute("Description","eenContentDescription")
          .setSearchanble(true)
          .setHighlight(true)
          .setSpelling(true)
          .setSuggestion(true)
          .setLanguages(lang)
          .end()    

        .addQueryElement()
        .newSearchElement().setClazz(BasicSearchStats.class)
          .setProcess("search")
          .end()
          
        .newTemplateElement("eenContentTitle",  "/WEB-INF/templates/oppfin/_eenHit.jspx")
          .setProcess("search")
          .end()
        
        .newTemplateElement("eenContentTitle", "/WEB-INF/templates/oppfin/_eenViewMeta.jspx")
          .setLabel("leftCol")
          .setProcess("view")
          .end()
          
        .newTemplateElement("eenContentTitle", "/WEB-INF/templates/oppfin/_eenView.jspx")
          .setLabel("body")
          .setProcess("view")
          .end()

        .newSearchElement()
          .setClazz(FieldSort.class)
          .setAttribute("values", new TreeSet<FieldSort.Value>(
              Arrays.asList(new FieldSort.Value[]{
                  FieldSort.getRelevancySort(),
                  new FieldSort.Value("Newest first", "eenDatumUpdate", Sort.DESC)
              })))
          .end()
        
          .addFieldFacet("EEN Type", "eenReferenceType")
          .addFieldFacet("Keyword", "eenKeywordTechnologiesLabel")
          .addFieldFacet("Partner Country", "eenCompanyCountryLabel")
          
        .addPagingElement()
        .addDebugElement()
        .endChild()
         

      /**
       * IDEALIST PRESET
       * 
       * 
       */
      
      .newChildPreset(true,  FieldFacet.class, TemplateElement.class)
        .setCollection(idealistCollection)
        .setSlug("idealist")
        .setLabel("Ideal-IST")
        .setVisible(false)
        .setDescription("IDEALIST cooperations")
        
        //.addFieldCondition("Open Opportunities Only", "idealistStatus","Open")
        .addFieldFacet("Status", "idealistStatus")
        
        .newFieldAttribute("Title","idealistTitle")
          .setLanguages(lang)
          .setSearchanble(true)
          .setHighlight(true)
          .setSpelling(true)
          .setSuggestion(true)
          .end()
        
        .newFieldAttribute("Summary","idealistOutline")
          .setLanguages(lang)
          .setSearchanble(true)
          .setHighlight(true)
          .setSpelling(true)
          .setSuggestion(true)
          .end()
          
        .newFieldAttribute("Summary","idealistBody")
          .setLanguages(lang)
          .setSearchanble(true)
          .setHighlight(true)
          .setSpelling(true)
          .setSuggestion(true)
          .end()

//      /** Facets for the presetIdealist. */
//     /**
//      * - Cooperation Source
//      * - Partner Country
//      */

        .addQueryElement()
        .addStatElement()
        .newTemplateElement("idealistTitle", "/WEB-INF/templates/oppfin/_idealistHit.jspx")
          .setProcess("search")
          .end()
        .newTemplateElement("idealistTitle", "/WEB-INF/templates/oppfin/_idealistView.jspx")
          .setLabel("body")
          .setProcess("view")
          .end()
        .newTemplateElement("idealistTitle", "/WEB-INF/templates/oppfin/_idealistViewMeta.jspx")
          .setLabel("leftCol")
          .setProcess("view")
          .end()
          
        .addPagingElement()
        .addDebugElement()
        .endChild()
       .endChild()
          
      /**
       * 
       * Cordis Preset
       * 
       * 
       */
     

      //LOGGER.info("++ Creating CORDIS preset");
      
       .newChildPreset(true, TemplateElement.class)
        .setLabel("Funded Projects")
        .setDescription("Funded projects")
        .setSlug("funded")
        .setCollection(cordisCollection)
        
        .newFieldAttribute("Title","cordisTitle")
          .setLanguages(lang)
          .setSearchanble(true)
          .setHighlight(true)
          .setSpelling(true)
          .setSuggestion(true)
          .end()
          
        .newFieldAttribute("Summary", "cordisSnippet")
          .setLanguages(lang)
          .setSearchanble(true)
          .setHighlight(true)
          .setSpelling(true)
          .setSuggestion(true)
          .end()
          
        .addQueryElement()
        .addStatElement()
        
        .addFieldFacet("Year", "cordisStartYear")
        .addFieldFacet("Area", "cordisArea")
        .addFieldFacet("Category", "cordisCategory")
        .addFieldFacet("Tag", "cordisTag")
        .addFieldFacet("Status", "cordisProjectStatus")
        
        .newTemplateElement("cordisTitle", "/WEB-INF/templates/oppfin/_cordisHit.jspx")
          .setProcess("search")
          .end()
        .newTemplateElement("cordisTitle", "/WEB-INF/templates/oppfin/_cordisView.jspx")
          .setLabel("body")
          .setProcess("view")
          .end()
        .newTemplateElement("cordisTitle", "/WEB-INF/templates/oppfin/_cordisViewMeta.jspx")
          .setLabel("leftCol")
          .setProcess("view")
          .end()

        .addPagingElement()
        .addDebugElement()
        .endChild()
       .end();
      
      /**
       * Users preset
       */

//      searchbox.addUserRole(new UserRole(system, Role.SYSTEM))
//        .addUserRole(new UserRole(admin, Role.ADMIN))
//        .addUserRole(new UserRole(user, Role.USER));
      
      repository.save(searchbox);

      LOGGER.info("Bootstraping application with oppfin data... done");

    }

    LOGGER.info("Starting all your engine");
    Iterator<SearchEngineEntity<?>> searchEngines = engineRepository
        .findAll().iterator();

    while (searchEngines.hasNext()) {
      SearchEngineEntity<?> searchEngine = searchEngines.next();
      LOGGER.info("++ Starting SearchEngine: " + searchEngine.getName());
      searchEngine.build();
    }

    LOGGER.info("****************************************************");
    LOGGER.info("*                  Welcome                         *");
    LOGGER.info("****************************************************");
    LOGGER.info("*                                                  *");
    LOGGER.info("*                             __ _                 *");
    LOGGER.info("*           ___  _ __  _ __  / _(_)_ __            *");
    LOGGER.info("*          / _ \\| '_ \\| '_ \\| |_| | '_ \\           *");
    LOGGER.info("*         | (_) | |_) | |_) |  _| | | | |          *");
    LOGGER.info("*          \\___/| .__/| .__/|_| |_|_| |_|          *");
    LOGGER.info("*               |_|   |_|                          *");
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