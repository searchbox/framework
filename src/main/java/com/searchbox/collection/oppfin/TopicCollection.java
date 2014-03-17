/*******************************************************************************
 * Copyright Euresearch - 2014 - http://www.euresearch.ch
 * Proprietary software license.
 *******************************************************************************/
package com.searchbox.collection.oppfin;
/*******************************************************************************
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowJobBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import org.xml.sax.SAXException;

import com.searchbox.collection.AbstractBatchCollection;
import com.searchbox.collection.StandardCollection;
import com.searchbox.collection.SynchronizedCollection;
import com.searchbox.core.dm.Field;
import com.searchbox.framework.config.RootConfiguration;

@Configurable
public class TopicCollection extends AbstractBatchCollection implements 
	StandardCollection, SynchronizedCollection {

  /** Properties */
  private final static String CRAWLER_USER_AGENT = "crawler.userAgent";
  private final static String TOPIC_LIST_URL = "topicList.url";
  private final static String TOPIC_DETAIL_URL = "topicDetail.url";
  private final static String TOPIC_DETAIL_BEGIN = "topicDetail.begin";
  private final static String TOPIC_DETAIL_END = "topicDetail.end";

  private final static String CALL_LIST_URL = "callList.url";
  private final static String CALL_DETAIL_URL = "callDetail.url";
  private final static String CALL_DETAIL_BEGIN = "callDetail.begin";
  private final static String CALL_DETAIL_END = "callDetail.end";

  private final static String CRAWLER_USER_AGENT_DEFAULT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_1) AppleWebKit/537.73.11 (KHTML, like Gecko) Version/7.0.1 Safari/537.73.11";

  private final static String TOPIC_LIST_URL_DEFAULT = "http://ec.europa.eu/research/participants/portal/data/call/h2020/topics.json";
  private final static String TOPIC_DETAIL_URL_DEFAULT = "http://ec.europa.eu/research/participants/portal/desktop/en/opportunities/h2020/topics/%s.html";
  private final static String TOPIC_DETAIL_BEGIN_DEFAULT = "<div class=\"tab-pane active\" id=\"tab1\">";
  private final static String TOPIC_DETAIL_END_DEFAULT = "</div>";

  private final static String CALL_LIST_URL_DEFAULT = "http://ec.europa.eu/research/participants/portal/data/call/h2020/calls.json";
  private final static String CALL_DETAIL_URL_DEFAULT = "http://ec.europa.eu/research/participants/portal/desktop/en/opportunities/h2020/calls/%s.html";
  private final static String CALL_DETAIL_BEGIN_DEFAULT = "<div class=\"tab-pane active\" id=\"tab1\">";
  private final static String CALL_DETAIL_END_DEFAULT = "</div>";

  private final Map<String, FieldMap> callList = new HashMap<String, FieldMap>();

  @Resource
  private Environment env;

  @Autowired
  private JobBuilderFactory jobBuilderFactory;

  @Autowired
  private StepBuilderFactory stepBuilderFactory;

  private static final Logger LOGGER = LoggerFactory
      .getLogger(TopicCollection.class);

  public static List<Field> GET_FIELDS() {
    List<Field> fields = new ArrayList<Field>();

    // Topic Fields
    fields.add(new Field(String.class, "topicIdentifier"));
    fields.add(new Field(String.class, "topicFileName"));
    fields.add(new Field(String.class, "topicTitle"));
    fields.add(new Field(String.class, "topicDescriptionHtml"));
    fields.add(new Field(String.class, "topicDescriptionRaw"));
    fields.add(new Field(String.class, "topicTags"));
    fields.add(new Field(String.class, "topicFlags"));

    // Call related Fields
    fields.add(new Field(String.class, "callTitle"));
    fields.add(new Field(String.class, "callIdentifier"));
    fields.add(new Field(String.class, "callFileName"));
    fields.add(new Field(Date.class, "callDeadline"));
    fields.add(new Field(String.class, "callStatus"));
    
    //Extended call fields
    fields.add(new Field(Integer.class, "callTotalBudget"));
    fields.add(new Field(String.class, "callFrameworkProgramme"));    
    fields.add(new Field(String.class, "callCategory"));
    fields.add(new Field(String.class, "callType"));
    fields.add(new Field(Date.class, "callPublication"));
    fields.add(new Field(String.class, "callProgrammeDescription"));
    fields.add(new Field(String.class, "callDescriptionHtml"));
    fields.add(new Field(String.class, "callDescriptionRaw"));

    // Generic fields
    fields.add(new Field(String.class, "docType"));
    fields.add(new Field(String.class, "programme"));
    fields.add(new Field(String.class, "source"));
    return fields;
  }

  @Override
  public String getIdValue(FieldMap fields) {
    return (String) fields.get("topicIdentifier").get(0);
  }

  @Override
  public String getBodyValue(FieldMap fields) {
	return (String) fields.get("topicDescriptionRaw").get(0);
  }

  @Override
  public String getTitleValue(FieldMap fields) {
    return (String) fields.get("topicTitle").get(0);
  }
  
  @Override
  public Date getPublishedValue(FieldMap fields) {
	return null;
  }

  @Override
  public Date getUpdateValue(FieldMap fields) {
  	return null;
  }
  
  public TopicCollection() {
    super("topicCollection");
  }

  public TopicCollection(String name) {
    super(name);
  }

  /**
   * Load content from a URL using a valid User agent.
   * 
   * @param url
   *          The Url to load data from
   * @return The content of the page as string.
   */
  private String loadFromUrl(String url) {
    try {
      HttpClient client = HttpClientBuilder.create().build();
      HttpGet request = new HttpGet(url);

      // add request header
      request.addHeader("User-Agent",
          env.getProperty(CRAWLER_USER_AGENT, CRAWLER_USER_AGENT_DEFAULT));
      HttpResponse response = client.execute(request);

      LOGGER.debug("Recieved the response code "
          + response.getStatusLine().getStatusCode() + " from " + url);

      BufferedReader rd = new BufferedReader(new InputStreamReader(response
          .getEntity().getContent()));

      StringBuffer result = new StringBuffer();
      String line = "";
      while ((line = rd.readLine()) != null) {
        result.append(line);
      }
      return result.toString();
    } catch (ClientProtocolException e) {
      LOGGER.error("Error loading url " + e.getLocalizedMessage());
    } catch (IOException e) {
      LOGGER.error("Error loading url " + e.getLocalizedMessage());
    }
    return null;
  }

  /**
   * Load HTML page and extract the part with the description
   * 
   * @param topicFileName
   *          The topic identifier in the URL
   * @return The html markup that corresponds to the description
   */
  private String getTopicDescription(String topicFileName) {

    LOGGER.info("Loading topic description [" + topicFileName + "]");

    String topicUrlDetail = String.format(
        env.getProperty(TOPIC_DETAIL_URL, TOPIC_DETAIL_URL_DEFAULT),
        topicFileName);

    return StringUtils.substringBetween(this.loadFromUrl(topicUrlDetail),
        env.getProperty(TOPIC_DETAIL_BEGIN, TOPIC_DETAIL_BEGIN_DEFAULT),
        env.getProperty(TOPIC_DETAIL_END, TOPIC_DETAIL_END_DEFAULT)).trim();
  }

  /**
   * Load HTML page and extract the part with the description
   * 
   * @param callFileName
   *          The call identifier in the URL
   * @return The html markup that corresponds to the description
   */
  private String getCallDescription(String callFileName) {

    LOGGER.info("Loading call description [" + callFileName + "]");

    // If the call is not cached, load it from URL
    if (!callList.containsKey(callFileName)) {
      callList.put(callFileName, new FieldMap());
      // callList.put(item.get("callIdentifier").toString(), item);
    }

    if (!callList.get(callFileName).containsKey("callDescriptionHtml")) {

      String topicUrlDetail = String.format(
          env.getProperty(CALL_DETAIL_URL, CALL_DETAIL_URL_DEFAULT),
          callFileName);

      String htmlValue = StringUtils.substringBetween(
          this.loadFromUrl(topicUrlDetail),
          env.getProperty(CALL_DETAIL_BEGIN, CALL_DETAIL_BEGIN_DEFAULT),
          env.getProperty(CALL_DETAIL_END, CALL_DETAIL_END_DEFAULT)).trim();

      callList.get(callFileName).put("callDescriptionHtml", htmlValue);
    }

    return callList.get(callFileName).get("callDescriptionHtml").toString();
  }

  public ItemReader<JSONObject> topicReader() {
    return new ItemReader<JSONObject>() {

      String topicList = loadFromUrl(env.getProperty(TOPIC_LIST_URL,
          TOPIC_LIST_URL_DEFAULT));

      // Topic list is a json list and we are interested in topicData =>
      // Topics => all
      Object obj = JSONValue.parse(topicList);

      JSONObject jsonObject = (JSONObject) obj;
      JSONObject topicData = (JSONObject) jsonObject.get("topicData");
      JSONArray topics = (JSONArray) topicData.get("Topics");

      // loop array
      Iterator<JSONObject> iterator = topics.iterator();

      public JSONObject read() {
        if (iterator.hasNext()) {
          return iterator.next();
        }
        return null;
      }
    };
  }

  public ItemReader<JSONObject> callReader() {
    return new ItemReader<JSONObject>() {

      String callListJSON = loadFromUrl(env.getProperty(CALL_LIST_URL,
          CALL_LIST_URL_DEFAULT));

      // Topic list is a json list and we are interested in topicData =>
      // Topics => all
      Object obj = JSONValue.parse(callListJSON);

      JSONObject jsonObject = (JSONObject) obj;
      JSONObject callData = (JSONObject) jsonObject.get("callData");
      JSONArray calls = (JSONArray) callData.get("Calls");

      // loop array
      Iterator<JSONObject> iterator = calls.iterator();

      public JSONObject read() {
        if (iterator.hasNext()) {
          return iterator.next();
        }
        return null;
      }
    };
  }

  public ItemProcessor<JSONObject, FieldMap> topicProcessor() {
    return new ItemProcessor<JSONObject, FieldMap>() {

      public FieldMap process(JSONObject topicObject) throws IOException {

        /**
         * Sample Topic object { "tags":[], "callTitle":
         * "Marie Skaodowska-Curie Research and Innovation Staff Exchange (RISE) "
         * , "title":
         * "Marie Skaodowska-Curie Research and Innovation Staff Exchange (RISE)"
         * , "flags":["Gender","IntlCoop","SSH"],
         * "callIdentifier":"H2020-MSCA-RISE-2014", "description":
         * "Objective:\r\n\r\nThe RISE scheme will promote international and inter-sector collaboration \r\nthrough"
         * , "callFileName":"h2020-msca-rise-2014",
         * "callDeadline":1398351600000, "callStatus":"Open",
         * "topicFileName":"59-msca-rise-2014", "identifier":"MSCA-RISE-2014" }
         */

        // Populating useful variables
        String topicIdentifier = (String) topicObject.get("identifier");
        String topicFileName = (String) topicObject.get("topicFileName");
        String callFileName = (String) topicObject.get("callFileName");

        // Pulling full HTML description from the web
        String topicDetailHtml = getTopicDescription(topicFileName);

        // Converting HTML to plain text for Solr
        String topicDetailRaw = new HtmlToPlainText().getPlainText(Jsoup
            .parse(topicDetailHtml));

        // Creating a new enhancedTopic
        JSONObject enhancedTopic = new JSONObject();
        enhancedTopic.put("eur_topic_description_html", topicDetailHtml);
        enhancedTopic.put("eur_topic_description_raw", topicDetailRaw);
        enhancedTopic.put("eur_topic_timestamp", Calendar.getInstance()
            .getTime());
        enhancedTopic.put("eur_topic_topicFileName", topicFileName);
        enhancedTopic.put("eur_topic_identifier", topicIdentifier);

        // Saving the enhanced file
        try {
          File file = new File("output/topics/" + topicFileName + ".json");
          file.getParentFile().mkdirs();

          FileWriter writer = new FileWriter(file);
          writer.write(enhancedTopic.toJSONString());
          writer.flush();
          writer.close();
          LOGGER.debug("File saved under " + file.getAbsolutePath());

          // Saving the list of calls identifiers
          File file2 = new File("output/topics/call_identifiers.txt");
          FileWriter writer2 = new FileWriter(file2, true);
          writer2.write(topicObject.get("callIdentifier") + "\n");
          writer2.flush();
          writer2.close();
        } catch (IOException e) {
          LOGGER.error(e.getLocalizedMessage());
        }

        // Creating the Field Map
        //FieldMap doc = new FieldMap();

        LOGGER.info("Inserting call {} into topic {}", 
        		callFileName,topicFileName);
        
        FieldMap doc = callList.get(callFileName);
        
        LOGGER.info("Current doc has "+doc.size());
        //LOGGER.info("Current doc is "+doc.toString());
        
        doc.put("source", "H2020");
        doc.put("docType", "Funding");
        doc.put("programme", "H2020");

        doc.put("topicIdentifier", topicIdentifier);
        doc.put("topicFileName", topicFileName);
        doc.put("topicTitle", (String) topicObject.get("title"));
        doc.put("topicDescriptionRaw", topicDetailRaw);
        doc.put("topicDescriptionHtml", topicDetailHtml);

        doc.put("topicTags", (JSONArray) topicObject.get("tags"));
        doc.put("topicFlags", (JSONArray) topicObject.get("flags"));

        

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        String utcDate = df.format(new Date((Long) topicObject
            .get("callDeadline")));

        doc.put("callDeadline", utcDate);

        if (LOGGER.isDebugEnabled()) {
          for (String key : doc.keySet()) {
            LOGGER.debug("field: {}\t{}", key, doc.get(key));
          }
        }

        /*LOGGER.info("***************************");
        LOGGER.info(doc.toString());
        LOGGER.info("***************************");
        System.exit(0);*/
        return doc;
      }
    };
  }

  public ItemProcessor<JSONObject, FieldMap> callProcessor() {
    return new ItemProcessor<JSONObject, FieldMap>() {

      public FieldMap process(JSONObject callObject) throws IOException {

        /**
         * Sample Call object { "CallIdentifier":{
         * "FileName":"h2020-msca-itn-2014", "CallId":"H2020-MSCA-ITN-2014",
         * "Status":"OPEN" }, "Title":
         * "MARIE SKAODOWSKA-CURIE ACTION: INNOVATIVE TRAINING NETWORKS (ITN)",
         * "FrameworkProgramme":"H2020",
         * "SpecificProgrammeLevel1Names":["EU.1."],
         * "SpecificProgrammeLevel2Names":["EU.1.3."],
         * "SpecificProgrammeNames":["EU.1.3.1."],
         * "MainSpecificProgrammeLevel1Name":"EU.1.",
         * "MainSpecificProgrammeLevel1Description":"Excellent Science",
         * "SP_color":"EU_1", "PublicationDate":1386716400000,
         * "DeadlineDate":1397055600000, "CallDetails":{ "AdditionalInfo":"",
         * "LatestInfo":{ "ApprovalDate":1391177582681, "Content":
         * "The submission session is now available for: MSCA-ITN-2014-EID(MSCA-ITN-EID), MSCA-ITN-2014-EJD(MSCA-ITN-EJD),MSCA-ITN-2014-ETN(MSCA-ITN-ETN)"
         * } }, "TotalIndicativeBudget":"405,180,000", "Theme":[],
         * "Type":"Fixed call", "Category":"CALL_FOR_PROPOSALS" }
         */

        // Populating useful variables
        JSONObject callIdentifierObject = (JSONObject) callObject
            .get("CallIdentifier");
        String callIdentifier = (String) callIdentifierObject.get("CallId");
        String callFileName = (String) callIdentifierObject.get("FileName");

        // Pulling full HTML description from the web
        String callDetailHtml = getCallDescription(callFileName);

        // Converting HTML to plain text for Solr
        String callDetailRaw = new HtmlToPlainText().getPlainText(Jsoup
            .parse(callDetailHtml));

        // Creating a new enhancedCall
        JSONObject enhancedCall = new JSONObject();
        enhancedCall.put("eur_call_description_html", callDetailHtml);
        enhancedCall.put("eur_call_description_raw", callDetailRaw);
        enhancedCall
            .put("eur_call_timestamp", Calendar.getInstance().getTime());
        enhancedCall.put("eur_call_FileName", callFileName);
        enhancedCall.put("eur_call_CallId", callIdentifier);

        // Saving the enhanced file
        try {
          File file = new File("output/calls/" + callFileName + ".json");
          file.getParentFile().mkdirs();

          FileWriter writer = new FileWriter(file);
          writer.write(enhancedCall.toJSONString());
          writer.flush();
          writer.close();
          LOGGER.debug("File saved under " + file.getAbsolutePath());

          // Saving the list of calls identifiers
          File file2 = new File("output/calls/calls_identifiers.txt");
          FileWriter writer2 = new FileWriter(file2, true);
          writer2.write(callIdentifier + "\n");
          writer2.flush();
          writer2.close();
        } catch (IOException e) {
          LOGGER.error(e.getLocalizedMessage());
        }

        FieldMap doc = new FieldMap();

        doc.put("callId", callIdentifier);
        doc.put("callFileName", callFileName);
        doc.put("callTitle", (String) callObject.get("Title"));
        doc.put("callDescriptionRaw", callDetailRaw);
        doc.put("callDescriptionHtml", callDetailHtml);
        doc.put("callDocType", "Call H2020");
        doc.put("callProgramme", "H2020");

        doc.put("callProgrammeDescription",
            (String) callObject.get("MainSpecificProgrammeLevel1Description"));
        doc.put("callPublication", (Long) callObject.get("PublicationDate"));
        //doc.put("callDeadline", (Long) callObject.get("DeadlineDate"));
        doc.put("callFrameworkProgramme",
            (String) callObject.get("FrameworkProgramme"));
        doc.put("callType", (String) callObject.get("Type"));
        doc.put("callCategory", (String) callObject.get("Category"));

        // Indicative budget needs to be parsed using US locale
        // ("TotalIndicativeBudget":"405,180,000")
        try {
          Long num = (Long) NumberFormat.getNumberInstance(java.util.Locale.US)
              .parse((String) callObject.get("TotalIndicativeBudget"));
          doc.put("callTotalBudget", num);
        } catch (ParseException e) {
          LOGGER.error("ParseException : " + e.getLocalizedMessage());
        }

        // Using data from CallIdentifier object
        doc.put("callStatus", (String) callIdentifierObject.get("Status"));

        if (LOGGER.isDebugEnabled()) {
          for (String key : doc.keySet()) {
            LOGGER.debug("field: {}\t{}", key, doc.get(key));
          }
        }
        
        return doc;
      }
    };
  }

  private ItemWriter<FieldMap> hashMapWriter() {

    return new ItemWriter<FieldMap>() {

      @Override
      public void write(List<? extends FieldMap> items) throws Exception {
        for (FieldMap item : items) {
          LOGGER.info("Adding call {} to callList", 
	        		  item.get("callFileName").get(0).toString()
        		  	);

          //TODO: Find why the call filename is in bracket if we 
          //don't put get(0) i.e: [h2020-msca-itn-2014]     
          callList.put(item.get("callFileName").get(0).toString(), item);
        }

      }

    };
  }

  @Override
  protected FlowJobBuilder getJobFlow(JobBuilder builder) {
    Step callStep = stepBuilderFactory.get("getCalls")
        .<JSONObject, FieldMap> chunk(50).reader(callReader())
        .processor(callProcessor()).writer(hashMapWriter()).build();

    Step topicStep = stepBuilderFactory.get("getTopics")
        .<JSONObject, FieldMap> chunk(50).reader(topicReader())
        .processor(topicProcessor()).writer(fieldMapWriter()).build();

    return builder.flow(callStep).next(topicStep).end();
  }


  public static void main(String... args)
      throws JobExecutionAlreadyRunningException, JobRestartException,
      JobInstanceAlreadyCompleteException, JobParametersInvalidException,
      SAXException, IOException {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
        RootConfiguration.class, TopicCollection.class);

    TopicCollection collection = context.getBean(TopicCollection.class);
    
    collection.synchronize();
  }
}
