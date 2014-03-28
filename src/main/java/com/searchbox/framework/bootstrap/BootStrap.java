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

import com.searchbox.collection.sonar.NormalizedIssueCollection;
import com.searchbox.core.engine.SearchEngine;
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
        .setSlug("sonar")
        .setName("Sonar Dory Searchbox");


      List<String> lang = new ArrayList<String>();
      lang.add("en");

      /** 
       * 
       * Oppfin Collections
       *  
       */
      LOGGER.info("++ Creating issues Collection");
      CollectionEntity<?> issueCollection = new CollectionEntity<>()
        .setClazz(NormalizedIssueCollection.class)
        .setName("SonarIssues")
        .setAutoStart(false)
        .setIdFieldName("id")
        .setSearchEngine(engine);
      issueCollection = collectionRepository.save(issueCollection);
      
      searchbox.newPreset()
        .setSlug("issues")
        .setLabel("Issues")
        .setCollection(issueCollection)
        .newFieldAttribute("Name", "rule.name")
          .setLanguages(lang)
          .setSearchanble(true)
          .setSuggestion(true)
          .setSpelling(true)
          .end()
        .end();
     
      
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
