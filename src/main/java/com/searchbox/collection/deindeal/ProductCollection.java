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
package com.searchbox.collection.deindeal;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowJobBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.searchbox.collection.AbstractBatchCollection;
import com.searchbox.collection.StandardCollection;
import com.searchbox.collection.SynchronizedCollection;
import com.searchbox.core.dm.Field;
import com.searchbox.core.dm.FieldMap;

@Configurable
public class ProductCollection extends AbstractBatchCollection implements
    SynchronizedCollection, StandardCollection {

  @Autowired
  ApplicationContext context;

  @Autowired
  StepBuilderFactory stepBuilderFactory;

  DateFormat df;

  private static final Logger LOGGER = LoggerFactory
      .getLogger(ProductCollection.class);

  public static List<Field> GET_FIELDS() {
    /*
     * "62218":{
      "name":"Muskelstimulator \u00abVitality\u00bb 1043023",
      "state":"work_in_progress",
      "updated_at":"2014-06-18 14:55:37.98397",
      "fr":{
         "category":"",
         "description":"",
         "hl_name":"",
         "color":"",
         "subcategory":""
      },
      "de":{
         "category":"Fitness",
         "description":"Alle wollen einen sexy Body, wenn da nur das Trainieren nicht w\u00e4re. Mit diesem Muskelstimulator kannst du dein Training angenehmer gestalten. Das Ger\u00e4t l\u00e4sst sich gut an deine Tagesplanung anpassen und kann deine Muskeln zwischen den Aktivit\u00e4ten und Ruhephasen stimulieren. Die ausgesendeten Reize sorgen f\u00fcr Muskelzuckungen, die deine Muskulatur nicht von richtigem Training unterscheiden kann. So kannst du dein Workout perfektionieren und deinen Weg zum Traumk\u00f6rper etwas bequemer bestreiten.",
         "hl_name":"Muskelstimulator \u00abVitality\u00bb",
         "color":"gr\u00fcn",
         "subcategory":"Muskelstimulation"
      }
   },
     */
    List<Field> fields = new ArrayList<Field>();
    fields.add(new Field(Integer.class, "productId"));
    fields.add(new Field(String.class, "name"));
    fields.add(new Field(String.class, "state"));
    fields.add(new Field(Date.class, "updated_at"));
    
    fields.add(new Field(String.class, "category_fr"));
    fields.add(new Field(String.class, "description_fr"));
    fields.add(new Field(String.class, "hl_name_fr"));
    fields.add(new Field(String.class, "color_fr"));
    fields.add(new Field(String.class, "subcategory_fr"));
    
    fields.add(new Field(String.class, "category_de"));
    fields.add(new Field(String.class, "description_de"));
    fields.add(new Field(String.class, "hl_name_de"));
    fields.add(new Field(String.class, "color_de"));
    fields.add(new Field(String.class, "subcategory_de"));
    
    return fields;
  }

  public ProductCollection() {
    this.df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    this.df.setTimeZone(TimeZone.getTimeZone("UTC"));

  }

  public ProductCollection(String name) {
    super(name);
  }

  public ItemReader<JSONObject> reader() throws IOException {

    return new ItemReader<JSONObject>() {

      JSONArray productData;
      Iterator iterator;

      {
        LOGGER.info("Starting collection / ItemReader");
        String jsonData = FileUtils.readFileToString(context.getResource(
            "classpath:data/test_data_deindeal.json").getFile());

        LOGGER.info("Finished reading test_data_deindeal.json. File is {}",
            jsonData.length());

        Object obj = JSONValue.parse(jsonData);
        LOGGER.info("Cordis data is loaded {}", obj != null);

        jsonData = null;
        productData = (JSONArray) obj;
        LOGGER.info("Cordis data is parsed {}", productData.size());

        iterator = productData.iterator();
      }

      @Override
      public JSONObject read() {
        if (iterator.hasNext()) {
          return (JSONObject) iterator.next();
        }
        return null;
      }
    };
  }

  public ItemProcessor<JSONObject, FieldMap> itemProcessor() {
    return new ItemProcessor<JSONObject, FieldMap>() {
      @Override
      public FieldMap process(JSONObject item) throws IOException {
        DateFormat dfSource = new SimpleDateFormat(
            "EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);

        LOGGER.info("Processing cordis {}", item.get("id"));
        FieldMap doc = new FieldMap();
        
        doc.put("docSource", "JSON");
        doc.put("docType", "Product");
        
/*
 * fields.add(new Field(Integer.class, "productId"));
    fields.add(new Field(String.class, "name"));
    fields.add(new Field(String.class, "state"));
    fields.add(new Field(Date.class, "updated_at"));
 */
        doc.put("productId", item.get("name"));

        System.out.println(item.toString());
        System.exit(0);
        
        doc.put("cordisId", item.get("id"));
        doc.put("cordisTag", item.get("tag"));
        doc.put("cordisStartYear", item.get("start_year"));
        doc.put("cordisProjectFunding", item.get("projectfunding"));
        doc.put("cordisContractType", item.get("contract_type"));
        doc.put("cordisProjectStatus", item.get("project_status"));
        doc.put("cordisUrl", item.get("url"));
        doc.put("cordisCategory", item.get("category"));
        doc.put("cordisProgram", item.get("program"));
        doc.put("cordisProjectCost", item.get("projectcost"));
        doc.put("cordisProjectDuration", item.get("projectduration"));
        doc.put("cordisAcronymDescription", item.get("acronym_description"));
        doc.put("cordisSnippet", item.get("snippet"));
        doc.put("cordisSubProgrammArea", item.get("subprogrammearea"));
        doc.put("cordisCallIdentifier", item.get("call_identifier"));
        doc.put("cordisTitle", item.get("title"));
        doc.put("cordisCountryCode", item.get("country_code"));
        doc.put("cordisArea", item.get("aera"));
        doc.put("cordisSubjectIndexCode", item.get("subjectindexcode"));
        doc.put("cordisLanguage", item.get("language"));

        try {
          doc.put("cordisProjectStartDate",
              dfSource.parse((String) item.get("projectstartdate")));
        } catch (Exception e) {
          LOGGER.error("Cannot parse date", e);
        }

        try {
          doc.put("cordisProjectEndDate",
              dfSource.parse((String) item.get("projectenddate")));
        } catch (Exception e) {
          LOGGER.error("Cannot parse date", e);
        }

        return doc;
      }
    };
  }

  @Override
  public String getIdValue(FieldMap fields) {
    return (String) fields.get("cordisId").get(0);
  }

  @Override
  public String getTitleValue(FieldMap fields) {
    return (String) fields.get("cordisTitle").get(0);
  }

  @Override
  public String getBodyValue(FieldMap fields) {
    return null;// (String) fields.get("cordisSnippet").get(0);
  }

  @Override
  public Date getPublishedValue(FieldMap fields) {
    return null;
  }

  @Override
  public Date getUpdateValue(FieldMap fields) {
    return null;
  }
  
  @Override
  protected FlowJobBuilder getJobFlow(JobBuilder builder) {

    Step step = null;
    try {
      step = stepBuilderFactory.get("getFile")
          .<JSONObject, FieldMap> chunk(500).reader(reader())
          .processor(itemProcessor()).writer(fieldMapWriter()).build();
    } catch (IOException e) {
      LOGGER.error("Could not build step.", e);
    }
    return builder.flow(step).end();
  }

}