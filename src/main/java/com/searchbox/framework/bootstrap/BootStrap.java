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

import java.io.File;
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

import com.searchbox.collection.ExpiringDocuments;
import com.searchbox.collection.StandardCollection;
import com.searchbox.collection.deindeal.ProductCollection;
import com.searchbox.core.dm.MultiCollection;
import com.searchbox.core.engine.SearchEngine;
import com.searchbox.core.ref.Order;
import com.searchbox.core.ref.Sort;
import com.searchbox.core.search.debug.SolrToString;
import com.searchbox.core.search.facet.FieldFacet;
import com.searchbox.core.search.paging.BasicPagination;
import com.searchbox.core.search.query.EdismaxQuery;
import com.searchbox.core.search.query.MoreLikeThisQuery;
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
        
        File f = new File(env.getProperty("searchengine.prop.value"));
        engine = new SearchEngineEntity<>()
            .setClazz(clazz)
            //.setAttribute("solrHome",f.getPath());
            .setAttribute(env.getProperty("searchengine.prop","solrHome"),
                env.getProperty("searchengine.prop.value",
                    context.getResource("classpath:solr/").getURL().getPath()));

        engine = engineRepository.save(engine);
      } catch (Exception e) {
        LOGGER.error("Could not set definition for SolrEmbededServer", e);
      }

      


      LOGGER.info("Bootstraping application with deindeal data...");

      /**
       * The base Searchbox.
       */
      LOGGER.info("++ Creating DeinDeal searchbox");
      SearchboxEntity searchbox = new SearchboxEntity()
        .setSlug("deindeal")
        .setName("DeinDeal Searchbox")
        .setLogo("http://blog.carpathia.ch/wp-content/uploads/2014/02/logo-deindeal.jpg");


      List<String> lang = new ArrayList<String>();
      lang.add("fr");
      lang.add("de");

      /**
       * DeinDeal Base Product Collections
       */
      LOGGER.info("++ Creating DeinDeal Product Base Collection");
      CollectionEntity<?> productsCollection = new CollectionEntity<>()
        .setClazz(ProductCollection.class)
        .setName("products")
        .setAutoStart(true)
        .setIdFieldName("productId")
        .setSearchEngine(engine);
      productsCollection = collectionRepository.save(productsCollection);

      /**
       *
       * Products Preset
       *
       *
       */

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
                    productsCollection.getName()
                  
                }))))
            .addQueryElement()
            .addFieldFacet("Source", "docSource")
            .addStatElement()
            .addPagingElement("search")
            .addDebugElement()
            
      //LOGGER.info("++ Creating Products preset");

      //Product fr
      .newChildPreset(true,  TemplateElement.class)
        .setCollection(productsCollection)
        .setSlug("products")
        .setLabel("Products")
        .setVisible(true)
        .setDescription("DeinDeal products")

        .newFieldAttribute("Title","name")
          .setLanguages(lang)
          .setSearchanble(true)
          .setHighlight(true)
          .setSpelling(true)
          .setSuggestion(true)
          .end()

        .newFieldAttribute("Summary", "description_fr")
          .setLanguages(lang)
          .setSearchanble(true)
          .setHighlight(true)
          .setSpelling(true)
          .setSuggestion(true)
          .end()

        .addQueryElement()
        .addStatElement()

        .addFieldFacet("State", "state")
        .addFieldFacet("Category", "category_fr")
        .addFieldFacet("Sub-Category", "subcategory_fr")
        .addFieldFacet("Color", "color_fr")

        .newTemplateElement("name", "/WEB-INF/templates/_defaultHitView.jspx")
          .setProcess("search")
          .end()
        .newTemplateElement("name", "/WEB-INF/templates/_defaultHitView.jspx")
          .setLabel("body")
          .setProcess("view")
          .end()
        .newTemplateElement("name", "/WEB-INF/templates/_defaultHitView.jspx")
          .setLabel("leftCol")
          .setProcess("view")
          .end()

        .addPagingElement("search")
        .addDebugElement()
        .endChild()
        
        //Product DE
        .newChildPreset(true,  TemplateElement.class)
        .setCollection(productsCollection)
        .setSlug("products_de")
        .setLabel("Products DE")
        .setVisible(true)
        .setDescription("DeinDeal products")

        .newFieldAttribute("Title","name")
          .setLanguages(lang)
          .setSearchanble(true)
          .setHighlight(true)
          .setSpelling(true)
          .setSuggestion(true)
          .end()

        .newFieldAttribute("Summary", "description_de")
          .setLanguages(lang)
          .setSearchanble(true)
          .setHighlight(true)
          .setSpelling(true)
          .setSuggestion(true)
          .end()

        .addQueryElement()
        .addStatElement()

        .addFieldFacet("State", "state")
        .addFieldFacet("Category", "category_de")
        .addFieldFacet("Sub-Category", "subcategory_de")
        .addFieldFacet("Color", "color_de")

        .newTemplateElement("name", "/WEB-INF/templates/_defaultHitView.jspx")
          .setProcess("search")
          .end()
        .newTemplateElement("name", "/WEB-INF/templates/_defaultHitView.jspx")
          .setLabel("body")
          .setProcess("view")
          .end()
        .newTemplateElement("name", "/WEB-INF/templates/_defaultHitView.jspx")
          .setLabel("leftCol")
          .setProcess("view")
          .end()

        .addPagingElement("search")
        .addDebugElement()
        .endChild()
       .end();

      

      repository.save(searchbox);

      LOGGER.info("Bootstraping application with Deindeal data... done");

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
    LOGGER.info("*                DeinDeal Searchbox                *");
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
