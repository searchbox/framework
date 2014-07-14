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
import java.util.Iterator;
import java.util.List;

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

import com.searchbox.collection.pubmed.PubmedCollection;
import com.searchbox.core.engine.SearchEngine;
import com.searchbox.core.ref.Order;
import com.searchbox.core.ref.Sort;
import com.searchbox.core.search.facet.FieldFacet;
import com.searchbox.engine.solr.EmbeddedSolr;
import com.searchbox.framework.event.SearchboxReady;
import com.searchbox.framework.model.CollectionEntity;
import com.searchbox.framework.model.SearchEngineEntity;
import com.searchbox.framework.model.SearchboxEntity;
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

  @Override
  synchronized public void onApplicationEvent(ContextRefreshedEvent event) {
    doBootStrap();
  }

  public void doBootStrap() {

    if (BOOTSTRAPED) {
      return;
    }

    BOOTSTRAPED = true;

    boolean doBootstrap = new Boolean(env.getProperty("searchbox.bootstrap", "false"));

    if (doBootstrap) {

      /**
       * The embedded Solr SearchEngine
       */
      LOGGER.info("++ Creating Search Engine");
      SearchEngineEntity<?> engine = null;
      try {

        LOGGER.info("Property " + env.getProperty("searchengine.prop.value"));
        
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

      


      LOGGER.info("Bootstraping application with pubmed data...");

      /**
       * The base Searchbox.
       */
      LOGGER.info("++ Creating Pubmed searchbox");
      SearchboxEntity searchbox = new SearchboxEntity()
        .setSlug("pubmed")
        .setName("Pubmed Searchbox")
        .setLogo("https://www.nihms.nih.gov/corehtml/pmc/pmcgifs/pmclogo.gif");


      List<String> lang = new ArrayList<String>();
      lang.add("en");

      /**
       * Pubmed Collections
       */
      LOGGER.info("++ Creating Pubmed Collection");
      CollectionEntity<?> pumedCollection = new CollectionEntity<>()
        .setClazz(PubmedCollection.class)
        .setName("pubmed")
        .setAutoStart(true)
        .setIdFieldName("id")
        .setSearchEngine(engine);
      pumedCollection = collectionRepository.save(pumedCollection);
      

      /**
       * Pubmed Articles Preset
       */
      searchbox
        .newPreset()
        .setCollection(pumedCollection)
        .setSlug("articles")
        .setLabel("Scientific articles")
        .setVisible(true)
        .setDescription("Pubmed articles")

        .newFieldAttribute("Title","articleTitle")
          .setLanguages(lang)
          .setSearchanble(true)
          .setHighlight(true)
          .setSpelling(true)
          .setBoost(1.8f)
          .setSuggestion(true)
          .end()

        .newFieldAttribute("Summary", "articleAbstract")
          .setLanguages(lang)
          .setSearchanble(true)
          .setHighlight(true)
          .setSpelling(true)
          .setSuggestion(true)
          .end()
          
        .newFieldAttribute("Completion Date", "articleCompletionDate")
          .setSortable(true)
          .end()
          
        .newFieldAttribute("Revision", "articleRevisionDate")
          .setSortable(true)
          .end()

        .newSearchElement()
         .setClazz(FieldFacet.class)
         .setLabel("Author")
         .setAttribute("fieldName","author")
         .setAttribute("order", Order.BY_VALUE)
         .setAttribute("sort", Sort.DESC)
         .end()

       .newSearchElement()
         .setClazz(FieldFacet.class)
         .setAttribute("fieldName", "articleYear")
         .setLabel("Year")
         .setAttribute("order", Order.BY_KEY)
         .setAttribute("sort", Sort.DESC)
         .end()
         
        .addQueryElement()
        .addStatElement()

        //Param - title field, template file
        .newTemplateElement("articleTitle", "/WEB-INF/templates/_pubmedHit.jspx")
          .setProcess("search")
          .end()
        .newTemplateElement("articleTitle", "/WEB-INF/templates/_defaultHitView.jspx")
          .setLabel("body")
          .setProcess("view")
          .end()
        .newTemplateElement("articleTitle", "/WEB-INF/templates/_defaultHitView.jspx")
          .setLabel("leftCol")
          .setProcess("view")
          .end()

        .addPagingElement("search")
        .addDebugElement()
       .end();
       
      
      repository.save(searchbox);

      LOGGER.info("Bootstraping application with Pubmed data... done");

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
    LOGGER.info("*  visit: http://localhost:8080/searchbox/pubmed   *");
    LOGGER.info("*  admin: http://localhost:8080/searchbox/admin/pubmed*");
    LOGGER.info("*                                                  *");
    LOGGER.info("****************************************************");

    publisher.publishEvent(new SearchboxReady(this));

  }
}
