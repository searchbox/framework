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
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowJobBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.searchbox.collection.AbstractBatchCollection;
import com.searchbox.collection.StandardCollection;
import com.searchbox.collection.SynchronizedCollection;
import com.searchbox.core.dm.Field;
import com.searchbox.core.dm.FieldMap;
import com.searchbox.framework.config.RootConfiguration;

@Configurable
public class IndividualProductCollection extends AbstractBatchCollection implements
    SynchronizedCollection, StandardCollection {

  @Autowired
  ApplicationContext context;

  @Autowired
  StepBuilderFactory stepBuilderFactory;

  DateFormat df;

  private static final Logger LOGGER = LoggerFactory
      .getLogger(IndividualProductCollection.class);

  public static List<Field> GET_FIELDS() {
    /*
     * {
       id:"60699",
       collection_id:"31163",
       state:"ready",
       product_group_id:"46622",
       product_group:{
          id:"46622",
          updated_at:"2014-06-27 11:06:53.41912",
          fr:{
             differentiation_factor:"taille",
             sku_group_title:"Chemise homme bordeaux "
          },
          de:{
             differentiation_factor:"Grösse",
             sku_group_title:"Herren-Hemd bordeaux"
          }
       },
       fr:{
          pg_differentiation_factor:"taille",
          refundable:"14 jours après la livraison",
          special_highlights:"Petit logo brodé sur la poitrine",
          pg_sku_group_title:"Chemise homme bordeaux ",
          option_name:"XL",
          material:"97% coton, 3% élasthanne",
          hl_name:"Chemise homme bordeaux XL",
          color:"bordeaux",
          care_instructions:"Lavable à 40° C max., peut être mise au sèche-linge à chaleur moyenne et repassée",
          additional_info:"Col Button Down, coupe Slim Fit, ourlet arrondi, dernier trou de bouton cousu dans une couleur différente",
          description:"Cette chemise TOMMY HILFIGER est parfaite pour un look sobre et classe à la fois. Sa simplicité fait que tu pourras facilement l'assortir à de nombreux vêtements. ",
          subcategory:"",
          category:""
       },
       de:{
          pg_differentiation_factor:"Grösse",
          refundable:"14 Tage Rückgaberecht",
          special_highlights:"Dezente Logo-Stickerei auf der Brust",
          pg_sku_group_title:"Herren-Hemd bordeaux",
          option_name:"XL",
          material:"97% Baumwolle, 3% Elastan",
          hl_name:"Herren-Hemd bordeaux XL",
          color:"Bordeaux",
          care_instructions:"Waschbar bei 40° C, bügelbar und tumblergeeignet bei mittlerer Hitze",
          additional_info:"Button-Down-Kragen, Slim Fit, abgerundeter Saum, letztes Knopfloch ist in Kontrastfarbe umnäht",
          description:"Dieses Hemd von TOMMY HILFIGER kleidet dich schlicht, aber mit viel Klasse ein. Das Design ist ordentlich und schnörkellos, so dass es sich nicht in den Vordergrund drängt und dir freie Hand beim Kombinieren lässt.",
          subcategory:"Hemden",
          category:"Mode Herren"
       },
       price:"79.0",
       variant_id:"72873",
       discount_percent:"39.0"
    }
     */
    List<Field> fields = new ArrayList<Field>();
    fields.add(new Field(Integer.class, "indProductId"));
    fields.add(new Field(Integer.class, "product_group_id"));
    fields.add(new Field(Integer.class, "collection_id"));
    fields.add(new Field(Integer.class, "variant_id"));
    
    fields.add(new Field(Float.class, "price"));
    fields.add(new Field(Float.class, "discount_percent"));
    fields.add(new Field(String.class, "state"));

    //fields.add(new Field(Date.class, "updated_at"));
    
    fields.add(new Field(String.class, "pg_sku_group_title"));
    fields.add(new Field(String.class, "pg_differentiation_factor"));
    fields.add(new Field(String.class, "special_highlights"));
    fields.add(new Field(String.class, "option_name"));
    fields.add(new Field(String.class, "hl_name"));
    fields.add(new Field(String.class, "color"));
    fields.add(new Field(String.class, "material"));
    fields.add(new Field(String.class, "description"));
    fields.add(new Field(String.class, "category"));
    fields.add(new Field(String.class, "subcategory"));
    
    return fields;
  }

  public IndividualProductCollection() {
    this.df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    this.df.setTimeZone(TimeZone.getTimeZone("UTC"));
  }

  public IndividualProductCollection(String name) {
    super(name);
  }

  public ItemReader<JSONObject> reader() throws IOException {

    return new ItemReader<JSONObject>() {

      JSONArray productData;
      Iterator iterator;

      {
        LOGGER.info("Starting collection / ItemReader");
        String jsonData = FileUtils.readFileToString(context.getResource(
            "classpath:data/individual_products.json").getFile());

        LOGGER.info("Finished reading individual_products.json. File is {}",
            jsonData.length());

        
        Object obj = JSONValue.parse(jsonData);
        LOGGER.info("DeinDeal data is loaded {}", obj != null);
       

        JSONObject productObject=(JSONObject)obj;
        LOGGER.info("Object has {} entries", productObject.size());

        iterator = productObject.values().iterator();
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

        FieldMap doc = new FieldMap();
        
        doc.put("docSource", "JSON");
        doc.put("docType", "IndividualProduct");
        
        //Global fields
        doc.put("indProductId", item.get("id"));
        doc.put("product_group_id", item.get("product_group_id"));
        doc.put("collection_id", item.get("collection_id"));
        doc.put("variant_id", item.get("variant_id"));
        
        doc.put("price", item.get("price"));
        doc.put("discount_percent", item.get("discount_percent"));

        
        //French
        try {
          JSONObject fr_item = (JSONObject) item.get("fr");
          LOGGER.info("Processing IndividualProduct {}", fr_item.get("pg_sku_group_title"));

          
          doc.put("category", fr_item.get("category"));
          doc.put("subcategory", fr_item.get("subcategory"));
          doc.put("description", fr_item.get("description"));
          doc.put("option_name", fr_item.get("option_name"));
          doc.put("color", fr_item.get("color"));
          doc.put("material", fr_item.get("material"));
          doc.put("special_highlights", fr_item.get("special_highlights"));
          doc.put("pg_differentiation_factor", fr_item.get("pg_differentiation_factor"));
          doc.put("pg_sku_group_title", fr_item.get("pg_sku_group_title"));
          
        } catch(Exception e){
          LOGGER.error("Cannot cast french object {}",e);
        }

        return doc;
      }
    };
  }

  @Override
  public String getIdValue(FieldMap fields) {
    return (String) fields.get("indProductId").get(0);
  }

  @Override
  public String getTitleValue(FieldMap fields) {
    return (String) fields.get("pg_sku_group_title").get(0);
  }

  @Override
  public String getBodyValue(FieldMap fields) {
    return (String) fields.get("description").get(0);
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
  
  public static void main(String... args)
      throws JobExecutionAlreadyRunningException, JobRestartException,
      JobInstanceAlreadyCompleteException, JobParametersInvalidException,
      IOException {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
        RootConfiguration.class, IndividualProductCollection.class);

    IndividualProductCollection collection = context.getBean(IndividualProductCollection.class);

    collection.synchronize();
  }

}