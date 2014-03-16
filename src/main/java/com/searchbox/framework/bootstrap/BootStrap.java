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
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.searchbox.collection.oppfin.EENCollection;
import com.searchbox.collection.oppfin.IdealISTCollection;
import com.searchbox.collection.oppfin.TopicCollection;
import com.searchbox.core.ref.Order;
import com.searchbox.core.ref.Sort;
import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.debug.SolrToString;
import com.searchbox.core.search.facet.FieldFacet;
import com.searchbox.core.search.paging.BasicPagination;
import com.searchbox.core.search.query.EdismaxQuery;
import com.searchbox.core.search.result.TemplateElement;
import com.searchbox.core.search.sort.FieldSort;
import com.searchbox.core.search.stat.BasicSearchStats;
import com.searchbox.engine.solr.SolrCloud;
import com.searchbox.framework.config.RootConfiguration;
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
@Configuration
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
  ApplicationEventPublisher publisher;

  private static boolean BOOTSTRAPED = false;

  private static boolean defaultData = true;

  @Override
  @Transactional
  synchronized public void onApplicationEvent(ContextRefreshedEvent event) {
    doBootStrap();
  }

  public void doBootStrap() {

    if (BOOTSTRAPED) {
      return;
    }

    BOOTSTRAPED = true;

    if (defaultData) {

      LOGGER.info("Creating Default Users...");
      User system = userService.registerNewUserAccount("system", "password");
      User admin = userService.registerNewUserAccount("admin", "password");
      User user = userService.registerNewUserAccount("user", "password");

      LOGGER.info("Bootstraping application with oppfin data...");

      /** The base Searchbox. */
      LOGGER.info("++ Creating oppfin searchbox");
      Searchbox searchbox = new Searchbox("oppfin", "Opportunity Finder Searchbox");

      /** The embedded Solr SearchEngine */
      LOGGER.info("++ Creating Embedded Solr Engine");
      SearchEngineDefinition engine = null;
      try {
        engine = new SearchEngineDefinition(SolrCloud.class, "Local SolrCloud");
        engine.setAttributeValue("zkHost", "localhost:9983");

        // engine = new
        // SearchEngineDefinition(EmbeddedSolr.class,"embedded Solr");
        // engine.setAttributeValue("solrHome",context.getResource("classpath:solr/").getURL().getPath());
        engine = engineRepository.save(engine);
      } catch (Exception e) {
        LOGGER.error("Could not set definition for SolrEmbededServer", e);
      }

      /**
       * - Search All - Project Funding (Topics) - Cooperations - Funded
       * projects (Mêmes données qu'avant, même layout)
       * 
       * 
       * 
       * Search All - Facets - Programme (H2020, ...) - Opportunity Type
       * (docType) - Hitlist - Filtres: All but funded projects - Pas besoin de
       * mettre "Contact information" - Normalement on a plus de mouse over
       * 
       * 
       * Project Funding (Topics) - Facets - Call Identifier - Deadline (list of
       * months) - Flags
       * 
       * - Hitlist - Filtres: docType=topic & callDeadline >= NOW - HitList -
       * Title - Description - Tags: callDeadline, callIdentifier, callBudget (à
       * confirmer par Francesco) - DetailView - Title - FullDescription (HTML
       * si possible) - Left panel - Topic Identifier: topicIdentifier - Call
       * Identifier: callIdentifier - Call Deadline: callDeadline (MMM DD, YYYY)
       * - Further information: - Call (link CallIdentifier) - Topic (link
       * topicIdentifier)
       * 
       * Cooperations (EEN connector / même layout, même template) - Mettre
       * crawler à jour et faire layout en fonction
       */

      /**
       * Topic preset
       */

      List<String> lang = new ArrayList<String>();
      lang.add("en");

      /** The base collection for searchbox */
      LOGGER.info("++ Creating oppfin Topic Collection");
      CollectionDefinition collection = new CollectionDefinition(TopicCollection.class, "H2020Topics");
      collection.setIdFieldName("topicIdentifier");
      collection.setAutoStart(false);
      collection.setSearchEngine(engine);
      collection = collectionRepository.save(collection);

      LOGGER.info("++ Creating Topic preset");
      PresetDefinition presetTopic = new PresetDefinition(collection);
      presetTopic.setLabel("H2020 Topics");
      presetTopic.setDescription("H2020 open calls");
      presetTopic.setSlug("topic");

      FieldAttributeDefinition topicIdentifier = new FieldAttributeDefinition(
          collection.getFieldDefinition("topicIdentifier"));
      topicIdentifier.setAttributeValue("searchable", true);
      topicIdentifier.setAttributeValue("spelling", true);
      topicIdentifier.setAttributeValue("suggestion", true);
      topicIdentifier.setAttributeValue("label", "Topic ID");
      presetTopic.addFieldAttribute(topicIdentifier);

      FieldAttributeDefinition callIdentifier = new FieldAttributeDefinition(
          collection.getFieldDefinition("callIdentifier"));
      callIdentifier.setAttributeValue("searchable", true);
      callIdentifier.setAttributeValue("spelling", true);
      callIdentifier.setAttributeValue("suggestion", true);
      callIdentifier.setAttributeValue("label", "Call ID");
      presetTopic.addFieldAttribute(callIdentifier);

      FieldAttributeDefinition fieldAttr = new FieldAttributeDefinition(
          collection.getFieldDefinition("topicTitle"));
      fieldAttr.setAttributeValue("searchable", true);
      fieldAttr.setAttributeValue("highlight", true);
      fieldAttr.setAttributeValue("spelling", true);
      fieldAttr.setAttributeValue("label", "title");
      fieldAttr.setAttributeValue("lang", lang);
      presetTopic.addFieldAttribute(fieldAttr);

      FieldAttributeDefinition fieldAttr2 = new FieldAttributeDefinition(
          collection.getFieldDefinition("topicDescriptionRaw"));
      fieldAttr2.setAttributeValue("searchable", true);
      fieldAttr2.setAttributeValue("highlight", true);
      fieldAttr2.setAttributeValue("spelling", true);
      fieldAttr2.setAttributeValue("label", "description");
      fieldAttr2.setAttributeValue("lang", lang);
      presetTopic.addFieldAttribute(fieldAttr2);

      FieldAttributeDefinition fieldAttr3 = new FieldAttributeDefinition(
          collection.getFieldDefinition("callDeadline"));
      fieldAttr3.setAttributeValue("sortable", true);
      presetTopic.addFieldAttribute(fieldAttr3);

      /** Create & add a querydebug SearchComponent to the preset; */
      SearchElementDefinition querydebug = new SearchElementDefinition(
          SolrToString.class);
      presetTopic.addSearchElement(querydebug);

      /** Create & add a query SearchComponent to the preset; */
      SearchElementDefinition query = new SearchElementDefinition(
          EdismaxQuery.class);
      presetTopic.addSearchElement(query);

      /** Create & add a TemplateElement SearchComponent to the preset; */
      SearchElementDefinition TemplateElement = new SearchElementDefinition(
          TemplateElement.class);
      TemplateElement.setAttributeValue("titleField", "topicTitle");
      TemplateElement.setAttributeValue("idField", "topicIdentifier");
      TemplateElement.setAttributeValue("templateFile",
          "/WEB-INF/templates/oppfin/_topicHit.jspx");
      presetTopic.addSearchElement(TemplateElement);

      /**
       * Create & add another TemplateElement SearchComponent to the preset;
       * SearchElementType can be overriden
       */
      SearchElementDefinition viewHit = new SearchElementDefinition(TemplateElement.class);
      viewHit.setType(SearchElement.Type.INSPECT);
      viewHit.setLabel("body");
      viewHit.setAttributeValue("titleField", "topicTitle");
      viewHit.setAttributeValue("idField", "id");
      viewHit.setAttributeValue("templateFile",
          "/WEB-INF/templates/oppfin/_topicView.jspx");
      presetTopic.addSearchElement(viewHit);

      /** Create & add a FieldSort SearchComponent to the preset; */
      SearchElementDefinition fieldSort = new SearchElementDefinition(FieldSort.class);
      SortedSet<FieldSort.Value> sortFields = new TreeSet<FieldSort.Value>();
      sortFields.add(FieldSort.getRelevancySort());
      sortFields.add(new FieldSort.Value(
          "By Deadline <span class=\"pull-right glyphicon glyphicon-chevron-down\"></span>", "callDeadline", Sort.ASC));
      fieldSort.setAttributeValue("values", sortFields);
      presetTopic.addSearchElement(fieldSort);

      /** Create & add a basicSearchStat SearchComponent to the preset; */
      SearchElementDefinition basicStatus = new SearchElementDefinition(BasicSearchStats.class);
      presetTopic.addSearchElement(basicStatus);

      /** Create & add a facet to the presetTopic. */
      SearchElementDefinition callFacet = new SearchElementDefinition(FieldFacet.class);
      callFacet.setAttributeValue("field", collection.getFieldDefinition("callIdentifier").getInstance());
      callFacet.setLabel("Call");
      callFacet.setAttributeValue("order", Order.BY_VALUE);
      callFacet.setAttributeValue("sort", Sort.DESC);
      presetTopic.addSearchElement(callFacet);

      /**
       * Ideally this is a range facet. We agreed that for now it will be a list
       * of months For instance(March 14, April 14, May 14, June 14, ...)
       */
      SearchElementDefinition deadlineFacet = new SearchElementDefinition(FieldFacet.class);
      deadlineFacet.setAttributeValue("field", collection.getFieldDefinition("callDeadline").getInstance());
      deadlineFacet.setLabel("Deadline");
      deadlineFacet.setAttributeValue("order", Order.BY_VALUE);
      deadlineFacet.setAttributeValue("sort", Sort.DESC);
      presetTopic.addSearchElement(deadlineFacet);

      SearchElementDefinition flagFacet = new SearchElementDefinition(FieldFacet.class);
      flagFacet.setAttributeValue("field", collection.getFieldDefinition("topicFlags").getInstance());
      flagFacet.setLabel("Flags");
      flagFacet.setAttributeValue("order", Order.BY_VALUE);
      flagFacet.setAttributeValue("sort", Sort.DESC);
      presetTopic.addSearchElement(flagFacet);

      SearchElementDefinition pagination = new SearchElementDefinition(BasicPagination.class);
      presetTopic.addSearchElement(pagination);

      searchbox.addPresetDefinition(presetTopic);

      /**
       * Cooperation preset
       */

      /** The base collection for een */
      LOGGER.info("++ Creating oppfin EEN Collection");
      CollectionDefinition eenCollection = new CollectionDefinition(EENCollection.class, "eenCooperations");
      eenCollection.setIdFieldName("eenReferenceExternal");
      eenCollection.setAutoStart(false);
      eenCollection.setSearchEngine(engine);
      eenCollection = collectionRepository.save(eenCollection);

      LOGGER.info("++ Creating Cooperation preset");
      PresetDefinition presetEEN = new PresetDefinition(eenCollection);
      presetEEN.setLabel("Cooperations");
      presetEEN.setDescription("EEN cooperations");
      presetEEN.setSlug("coop");
      presetEEN.setCollection(eenCollection);
      searchbox.addPresetDefinition(presetEEN);

      FieldAttributeDefinition eenSubmitField = new FieldAttributeDefinition(
          eenCollection.getFieldDefinition("eenDatumSubmit"));
      eenSubmitField.setAttributeValue("label", "Published");
      eenSubmitField.setAttributeValue("sortable", true);
      presetEEN.addFieldAttribute(eenSubmitField);

      FieldAttributeDefinition eenUpdateField = new FieldAttributeDefinition(
          eenCollection.getFieldDefinition("eenDatumUpdate"));
      eenUpdateField.setAttributeValue("label", "update");
      eenUpdateField.setAttributeValue("sortable", true);
      presetEEN.addFieldAttribute(eenUpdateField);

      FieldAttributeDefinition eenDeadlineField = new FieldAttributeDefinition(
          eenCollection.getFieldDefinition("eenDatumDeadline"));
      eenDeadlineField.setAttributeValue("label", "Deadline");
      eenDeadlineField.setAttributeValue("sortable", true);
      presetEEN.addFieldAttribute(eenDeadlineField);

      FieldAttributeDefinition eenTitleField = new FieldAttributeDefinition(
          eenCollection.getFieldDefinition("eenContentTitle"));
      eenTitleField.setAttributeValue("searchable", true);
      eenTitleField.setAttributeValue("highlight", true);
      eenTitleField.setAttributeValue("spelling", true);
      eenTitleField.setAttributeValue("suggestion", true);
      eenTitleField.setAttributeValue("label", "Title");
      eenTitleField.setAttributeValue("lang", lang);
      presetEEN.addFieldAttribute(eenTitleField);

      FieldAttributeDefinition eenSummaryField = new FieldAttributeDefinition(
          eenCollection.getFieldDefinition("eenContentSummary"));
      eenSummaryField.setAttributeValue("searchable", true);
      eenSummaryField.setAttributeValue("highlight", true);
      eenSummaryField.setAttributeValue("spelling", true);
      eenSummaryField.setAttributeValue("suggestion", true);
      eenSummaryField.setAttributeValue("label", "Summary");
      eenSummaryField.setAttributeValue("lang", lang);
      presetEEN.addFieldAttribute(eenSummaryField);

      FieldAttributeDefinition eenKeywordField = new FieldAttributeDefinition(
          eenCollection.getFieldDefinition("eenKeywordTechnologiesLabel"));
      eenKeywordField.setAttributeValue("searchable", true);
      eenKeywordField.setAttributeValue("spelling", true);
      eenKeywordField.setAttributeValue("suggestion", true);
      eenKeywordField.setAttributeValue("label", "Keyword");
      eenKeywordField.setAttributeValue("lang", lang);
      presetEEN.addFieldAttribute(eenKeywordField);

      FieldAttributeDefinition eenDescriptionField = new FieldAttributeDefinition(
          eenCollection.getFieldDefinition("eenContentDescription"));
      eenDescriptionField.setAttributeValue("searchable", true);
      eenDescriptionField.setAttributeValue("highlight", true);
      eenDescriptionField.setAttributeValue("spelling", true);
      eenDescriptionField.setAttributeValue("suggestion", true);
      eenDescriptionField.setAttributeValue("label", "Description");
      eenDescriptionField.setAttributeValue("lang", lang);
      presetEEN.addFieldAttribute(eenDescriptionField);

      /** Create & add a query SearchComponent to the preset; */
      SearchElementDefinition eenQuery = new SearchElementDefinition(EdismaxQuery.class);
      presetEEN.addSearchElement(eenQuery);

      /** Create & add a TemplateElement SearchComponent to the preset; */
      SearchElementDefinition eenTemplateElement = new SearchElementDefinition(TemplateElement.class);
      eenTemplateElement.setAttributeValue("titleField", "eenContentTitle");
      eenTemplateElement.setAttributeValue("idField", "eenReferenceExternal");
      eenTemplateElement.setAttributeValue("templateFile",
          "/WEB-INF/templates/oppfin/_eenHit.jspx");
     presetEEN.addSearchElement(eenTemplateElement);

      SearchElementDefinition eenViewHitMeta = new SearchElementDefinition(TemplateElement.class);
      eenViewHitMeta.setLabel("leftCol");
      eenViewHitMeta.setType(SearchElement.Type.INSPECT);
      eenViewHitMeta.setAttributeValue("titleField", "eenContentTitle");
      eenViewHitMeta.setAttributeValue("idField", "eenReferenceExternal");
      eenViewHitMeta.setAttributeValue("templateFile",
          "/WEB-INF/templates/oppfin/_eenViewMeta.jspx");
      presetEEN.addSearchElement(eenViewHitMeta);
      
      SearchElementDefinition eenViewHit = new SearchElementDefinition(TemplateElement.class);
      eenViewHit.setLabel("body");
      eenViewHit.setType(SearchElement.Type.INSPECT);
      eenViewHit.setAttributeValue("titleField", "eenContentTitle");
      eenViewHit.setAttributeValue("idField", "eenReferenceExternal");
      eenViewHit.setAttributeValue("templateFile",
          "/WEB-INF/templates/oppfin/_eenView.jspx");
      presetEEN.addSearchElement(eenViewHit);

      
      /** Create & add a basicSearchStat SearchComponent to the preset; */
      SearchElementDefinition eenBasicStatus = new SearchElementDefinition(
          BasicSearchStats.class);
      presetEEN.addSearchElement(eenBasicStatus);

      SearchElementDefinition eenPagination = new SearchElementDefinition(
          BasicPagination.class);
      presetEEN.addSearchElement(eenPagination);

      /** Create & add a FieldSort SearchComponent to the preset; */
      SearchElementDefinition eenFieldSort = new SearchElementDefinition(FieldSort.class);
      SortedSet<FieldSort.Value> eenSortFields = new TreeSet<FieldSort.Value>();
      eenSortFields.add(FieldSort.getRelevancySort());
      eenSortFields.add(new FieldSort.Value("Newest first", "eenDatumUpdate", Sort.DESC));
      eenFieldSort.setAttributeValue("values", eenSortFields);
      presetEEN.addSearchElement(eenFieldSort);

      /** Create & add a facet to the presetTopic. */
      SearchElementDefinition eenDocSource = new SearchElementDefinition(FieldFacet.class);
      eenDocSource.setAttributeValue("field", eenCollection.getFieldDefinition("docSource").getInstance());
      eenDocSource.setLabel("Cooperation Source");
      eenDocSource.setAttributeValue("order", Order.BY_VALUE);
      eenDocSource.setAttributeValue("sort", Sort.DESC);
      presetEEN.addSearchElement(eenDocSource);

      /** Create & add a facet to the presetTopic. */
      SearchElementDefinition eenReferenceTypeFacet = new SearchElementDefinition(FieldFacet.class);
      eenReferenceTypeFacet.setAttributeValue("field", eenCollection.getFieldDefinition("eenReferenceType")
          .getInstance());
      eenReferenceTypeFacet.setLabel("EEN Type");
      eenReferenceTypeFacet.setAttributeValue("order", Order.BY_VALUE);
      eenReferenceTypeFacet.setAttributeValue("sort", Sort.DESC);
      presetEEN.addSearchElement(eenReferenceTypeFacet);

      /** Create & add a facet to the presetTopic. */
      SearchElementDefinition eenKeyword = new SearchElementDefinition(FieldFacet.class);
      eenKeyword.setAttributeValue("field", eenCollection.getFieldDefinition("eenKeywordTechnologiesLabel")
          .getInstance());
      eenKeyword.setLabel("Keyword");
      eenKeyword.setAttributeValue("order", Order.BY_VALUE);
      eenKeyword.setAttributeValue("sort", Sort.DESC);
      presetEEN.addSearchElement(eenKeyword);

      /** Create & add a facet to the presetTopic. */
      SearchElementDefinition eenCompanyCountry = new SearchElementDefinition(FieldFacet.class);
      eenCompanyCountry.setAttributeValue("field", eenCollection.getFieldDefinition("eenCompanyCountryLabel")
          .getInstance());
      eenCompanyCountry.setLabel("Partner Country");
      eenCompanyCountry.setAttributeValue("order", Order.BY_VALUE);
      eenCompanyCountry.setAttributeValue("sort", Sort.DESC);
      presetEEN.addSearchElement(eenCompanyCountry);

       SearchElementDefinition eenQueryDebug = new
       SearchElementDefinition(SolrToString.class);
       presetEEN.addSearchElement(eenQueryDebug);

      /**
       * IDEALIST PRESET
       * 
       * 
       * 
       * 
       * 
       */
      /** The base collection for idealist */
      LOGGER.info("++ Creating oppfin IDEALIST Collection");
      CollectionDefinition idealistCollection = new CollectionDefinition(
          IdealISTCollection.class, "idealistCooperations");
      idealistCollection.setIdFieldName("id");
      idealistCollection.setAutoStart(false);
      idealistCollection.setSearchEngine(engine);
      idealistCollection = collectionRepository.save(idealistCollection);

      LOGGER.info("++ Creating Cooperation preset");
      PresetDefinition presetIDEALIST = new PresetDefinition(idealistCollection);
      presetIDEALIST.setLabel("Ideal-IST");
      presetIDEALIST.setDescription("IDEALIST cooperations");
      presetIDEALIST.setSlug("idealist");
      presetIDEALIST.setCollection(idealistCollection);
      searchbox.addPresetDefinition(presetIDEALIST);

      FieldAttributeDefinition idealistTitleField = new FieldAttributeDefinition(
          idealistCollection.getFieldDefinition("idealistTitle"));
      idealistTitleField.setAttributeValue("searchable", true);
      idealistTitleField.setAttributeValue("highlight", true);
      idealistTitleField.setAttributeValue("spelling", true);
      idealistTitleField.setAttributeValue("suggestion", true);
      idealistTitleField.setAttributeValue("label", "Title");
      idealistTitleField.setAttributeValue("lang", lang);
      presetIDEALIST.addFieldAttribute(idealistTitleField);

      FieldAttributeDefinition idealistSummaryField = new FieldAttributeDefinition(
          idealistCollection.getFieldDefinition("idealistOutline"));
      idealistSummaryField.setAttributeValue("searchable", true);
      idealistSummaryField.setAttributeValue("highlight", true);
      idealistSummaryField.setAttributeValue("spelling", true);
      idealistSummaryField.setAttributeValue("suggestion", true);
      idealistSummaryField.setAttributeValue("label", "Summary");
      idealistSummaryField.setAttributeValue("lang", lang);
      presetIDEALIST.addFieldAttribute(idealistSummaryField);

      FieldAttributeDefinition idealistBodyField = new FieldAttributeDefinition(
          idealistCollection.getFieldDefinition("idealistBody"));
      idealistBodyField.setAttributeValue("searchable", true);
      idealistBodyField.setAttributeValue("highlight", true);
      idealistBodyField.setAttributeValue("spelling", true);
      idealistBodyField.setAttributeValue("suggestion", true);
      idealistBodyField.setAttributeValue("label", "Summary");
      idealistBodyField.setAttributeValue("lang", lang);
      presetIDEALIST.addFieldAttribute(idealistBodyField);

      /** Create & add a query SearchComponent to the preset; */
      SearchElementDefinition idealistQuery = new SearchElementDefinition(
          EdismaxQuery.class);
      presetIDEALIST.addSearchElement(idealistQuery);

      /** Create & add a TemplateElement SearchComponent to the preset; */
      SearchElementDefinition idealistTmpHit = new SearchElementDefinition(
          TemplateElement.class);
      idealistTmpHit.setAttributeValue("titleField", "idealistTitle");
      idealistTmpHit.setAttributeValue("idField", "id");
      idealistTmpHit.setAttributeValue("templateFile",
          "/WEB-INF/templates/oppfin/_idealistHit.jspx");
      presetIDEALIST.addSearchElement(idealistTmpHit);
      
      SearchElementDefinition idealistTmpView = new SearchElementDefinition(
          TemplateElement.class);
      idealistTmpView.setType(SearchElement.Type.INSPECT);
      idealistTmpView.setAttributeValue("titleField", "idealistTitle");
      idealistTmpView.setAttributeValue("idField", "id");
      idealistTmpView.setAttributeValue("templateFile",
          "/WEB-INF/templates/oppfin/_idealistView.jspx");
      presetIDEALIST.addSearchElement(idealistTmpView);
      
      


      // SearchElementDefinition idealistViewHit = new SearchElementDefinition(
      // TemplateElement.class);
      // idealistViewHit.setType(SearchElement.Type.INSPECT);
      // idealistViewHit.setAttributeValue("titleField", "idealistTitle");
      // idealistViewHit.setAttributeValue("idField", "id");
      // idealistViewHit
      // .setAttributeValue(
      // "template","template here"
      // );
      // presetIDEALIST.addSearchElement(idealistViewHit);

      /** Create & add a basicSearchStat SearchComponent to the preset; */
      SearchElementDefinition idealistBasicStatus = new SearchElementDefinition(
          BasicSearchStats.class);
      presetIDEALIST.addSearchElement(idealistBasicStatus);

      SearchElementDefinition idealistPagination = new SearchElementDefinition(
          BasicPagination.class);
      presetIDEALIST.addSearchElement(idealistPagination);

      SearchElementDefinition idealistDeb = new SearchElementDefinition(
          SolrToString.class);
      presetIDEALIST.addSearchElement(idealistDeb);

      /** Create & add a FieldSort SearchComponent to the preset; */
      // SearchElementDefinition idealistFieldSort = new
      // SearchElementDefinition(
      // FieldSort.class);
      // SortedSet<FieldSort.Value> idealistSortFields = new
      // TreeSet<FieldSort.Value>();
      // idealistSortFields.add(FieldSort.getRelevancySort());
      // idealistSortFields.add(new FieldSort.Value("Newest first",
      // "idealistDatumUpdate", Sort.DESC));
      // idealistFieldSort.setAttributeValue("values", idealistSortFields);
      // presetIDEALIST.addSearchElement(idealistFieldSort);

      /**
       * Users preset
       */

      searchbox.addUserRole(new UserRole(system, Role.SYSTEM));
      searchbox.addUserRole(new UserRole(admin, Role.ADMIN));
      searchbox.addUserRole(new UserRole(user, Role.USER));
      repository.save(searchbox);

      LOGGER.info("Bootstraping application with oppfin data... done");

    }

    LOGGER.info("Starting all your engine");
    Iterator<SearchEngineDefinition> engineDefinitions = engineRepository.findAll().iterator();

    while (engineDefinitions.hasNext()) {
      SearchEngineDefinition engineDefinition = engineDefinitions.next();
      LOGGER.info("++ Starting SearchEngine: " + engineDefinition.getName());
      engineDefinition.getInstance();
    }

    LOGGER.info("****************************************************");
    LOGGER.info("*                  Welcome                         *");
    LOGGER.info("****************************************************");
    LOGGER.info("*                                                  *");
    LOGGER.info("*                             __ _                 *");
    LOGGER.info("*           ___  _ __  _ __  / _(_)_ __            *");
    LOGGER.info("*          / _ \\| '_ \\| '_ \\| |_| | '_ \\       *");
    LOGGER.info("*         | (_) | |_) | |_) |  _| | | | |          *");
    LOGGER.info("*          \\___/| .__/| .__/|_| |_|_| |_|         *");
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

  public static void main(String... args) {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RootConfiguration.class,
        BootStrap.class);

    // context.getBeanFactory().createBean(BootStrap.class).doBootStrap();
  }
}
