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
import com.searchbox.core.ref.Sort;
import com.searchbox.core.search.sort.FieldSort;
import com.searchbox.engine.solr.EmbeddedSolr;
import com.searchbox.framework.domain.UserRole;
import com.searchbox.framework.domain.UserRole.Role;
import com.searchbox.framework.event.SearchboxReady;
import com.searchbox.framework.model.CollectionEntity;
import com.searchbox.framework.model.SearchEngineEntity;
import com.searchbox.framework.model.SearchboxEntity;
import com.searchbox.framework.model.UserEntity;
import com.searchbox.framework.repository.CollectionRepository;
import com.searchbox.framework.repository.SearchEngineRepository;
import com.searchbox.framework.repository.SearchboxRepository;
import com.searchbox.framework.service.DirectoryService;
import com.searchbox.framework.service.UserService;

@Component
@org.springframework.core.annotation.Order(value = 10000)
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
  DirectoryService directoryService;

  @Autowired
  ApplicationEventPublisher publisher;

  private static boolean BOOTSTRAPED = false;

  private static boolean defaultData = true;

  @Override
  @Transactional
  synchronized public void onApplicationEvent(ContextRefreshedEvent event) {

    if (BOOTSTRAPED) {
      return;
    }

    BOOTSTRAPED = true;

    if (defaultData) {

      LOGGER.info("Creating Default Users...");
      UserEntity system = userService.registerNewUserAccount("system", "password");
      UserEntity admin = userService.registerNewUserAccount("admin", "password");
      UserEntity user = userService.registerNewUserAccount("user", "password");

      LOGGER.info("Bootstraping application with default data...");

      /** The embedded Solr SearchEngine */
      LOGGER.info("++ Creating Embedded Solr Engine");
      SearchEngineEntity<?> engine = null;
      try {
//        engine = new SearchEngineEntity<>()
//            .setClazz(SolrCloud.class)
//            .setName("Local SolrCloud")
//            .setAttribute("zkHost", "localhost:9983");

        /** Dev Embedded Solr */
        engine = new SearchEngineEntity<>()
            .setClazz(EmbeddedSolr.class)
            .setName("Embedded Solr")
            .setAttribute("solrHome", context.getResource("classpath:solr/").getURL().getPath());
        
       
        engine = engineRepository.save(engine);
      } catch (Exception e) {
        LOGGER.error("Could not set definition for SolrEmbededServer", e);
      }
      
      /** The USed Languages. */
      List<String> lang = new ArrayList<String>();
      lang.add("en");
      
      /** The base Searchbox. */
      LOGGER.info("++ Creating pubmed searchbox");
      SearchboxEntity searchbox = new SearchboxEntity()
        .setName("Embeded pubmed Demo")
        .setSlug("pubmed");
      
      /** The base collection for searchbox */
      LOGGER.info("++ Creating pubmed Collection");
      CollectionEntity<?> collection = new CollectionEntity<>()
        .setClazz(PubmedCollection.class)
        .setName("pubmed")
        .setIdFieldName("id")
        .setAutoStart(true)
        .setSearchEngine(engine);
      collection = collectionRepository.save(collection);

      /** SearchAll preset */
      LOGGER.info("++ Creating Search All preset");
      searchbox.newPreset()
        .setSlug("all")
        .setLabel("Search All")
        
        .setCollection(collection)
        
        .newFieldAttribute("Title", collection.getField("article_title"))
          .setLanguages(lang)
          .setSearchanble(true)
          .setHighlight(true)
          .setSpelling(true)
          .setSuggestion(true)
          .end()
          
        .newFieldAttribute("Abstract", collection.getField("article_abstract"))
          .setLanguages(lang)
          .setSearchanble(true)
          .setHighlight(true)
          .setSpelling(true)
          .setSuggestion(true)
          .end()
          
        .addSortableFieldAttribute("Completion Date", collection.getField("article_completion_date"))
        .addSortableFieldAttribute("Revision Date", collection.getField("article_revision_date"))
        
        .addQueryElement()
        .addStatElement()
        .addTemplateElement("article_title", "/WEB-INF/templates/_pubmedHit.jspx")

      
      /** Sample to make a dynamic (not on file). Use Directory Service to 
       * persist the template. Template can them be a field of the 
       * SearchElementDefinition. TBD.
       *
      templatedHitList
          .setAttributeValue("templateFile",directoryService.createRelativeCachedAttribute(
                "<jsp:root xmlns:jsp=\"http://java.sun.com/JSP/Page\" " 
                  + "xmlns:sbx=\"urn:jsptagdir:/WEB-INF/tags/sbx\" "
                  + "xmlns:c=\"http://java.sun.com/jsp/jstl/core\" " 
                  + "version=\"2.0\">" 
                  + "<sbx:title hit=\"${hit}\" link=\"http://www.ncbi.nlm.nih.gov/pubmed/${hit.getId()}\"/>"
                  + "<sbx:snippet hit=\"${hit}\" field=\"article-abstract\"/>"
                  + "<sbx:tagAttribute filter=\"author\" limit=\"3\" label=\"Author(s)\" values=\"${hit.fieldValues['author']}\"/>"
                  + "</jsp:root>"));
      */
        
        .newSearchElement()
          .setLabel("Sort Element")
          .setClazz(FieldSort.class)
          .setAttribute("values", new TreeSet<FieldSort.Value>(
              Arrays.asList(new FieldSort.Value[]{
                  FieldSort.getRelevancySort(),
                  new FieldSort.Value("Latest Article", 
                      "article_completion_date", Sort.DESC),
                  new FieldSort.Value("Latest Reviewed",
                      "article_revision_date", Sort.DESC)
              })))
          .end()
     

        .addFieldFacet("Type", "publication_type")
    
        .addPagingElement()
        .addDebugElement()
        .end();

     

      searchbox.addUserRole(new UserRole(system, Role.SYSTEM));
      searchbox.addUserRole(new UserRole(admin, Role.ADMIN));
      searchbox.addUserRole(new UserRole(user, Role.USER));
      repository.save(searchbox);

     
      LOGGER.info("Bootstraping application with default data... done");

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
    LOGGER.info("*   __                     _     _                 *");
    LOGGER.info("*  / _\\ ___  __ _ _ __ ___| |__ | |__   _____  __  *");
    LOGGER.info("*  \\ \\ / _ \\/ _` | '__/ __| '_ \\| '_ \\ / _ \\ \\/ /  *");
    LOGGER.info("*  _\\ \\  __/ (_| | | | (__| | | | |_) | (_) >  <   *");
    LOGGER.info("*  \\__/\\___|\\__,_|_|  \\___|_| |_|_.__/ \\___/_/\\_\\  *");
    LOGGER.info("*                                                  *");
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