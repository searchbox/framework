package com.searchbox.collection.oppfin;

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowJobBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.env.Environment;

import com.searchbox.collection.AbstractBatchCollection;
import com.searchbox.collection.SynchronizedCollection;
import com.searchbox.core.dm.Field;

@Configurable
public class TopicCollection extends AbstractBatchCollection implements
        SynchronizedCollection {

    /** Properties */
    private final static String CRAWLER_USER_AGENT = "crawler.userAgent";
    private final static String TOPIC_LIST_URL = "topicList.url";
    private final static String TOPIC_DETAIL_URL = "topicDetail.url";
    private final static String TOPIC_DETAIL_BEGIN = "topicDetail.begin";
    private final static String TOPIC_DETAIL_END = "topicDetail.end";

    private final static String CRAWLER_USER_AGENT_DEFAULT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_1) AppleWebKit/537.73.11 (KHTML, like Gecko) Version/7.0.1 Safari/537.73.11";
    private final static String TOPIC_LIST_URL_DEFAULT = "http://ec.europa.eu/research/participants/portal/data/call/h2020/topics.json";
    private final static String TOPIC_DETAIL_URL_DEFAULT = "http://ec.europa.eu/research/participants/portal/desktop/en/opportunities/h2020/topics/%s.html";
    private final static String TOPIC_DETAIL_BEGIN_DEFAULT = "<div class=\"tab-pane active\" id=\"tab1\">";
    private final static String TOPIC_DETAIL_END_DEFAULT = "</div>";

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
        fields.add(new Field(String.class, "topicIdentifier"));
        fields.add(new Field(String.class, "title"));
        fields.add(new Field(String.class, "descriptionHtml"));
        fields.add(new Field(String.class, "descriptionRaw"));
        fields.add(new Field(String.class, "docType"));
        fields.add(new Field(String.class, "programme"));
        fields.add(new Field(String.class, "tags"));
        fields.add(new Field(String.class, "flags"));
        fields.add(new Field(String.class, "callTitle"));
        fields.add(new Field(String.class, "callIdentifier"));
        fields.add(new Field(Date.class, "callDeadline"));
        fields.add(new Field(String.class, "callStatus"));
        return fields;
    }

    public TopicCollection() {
        super();
    }

    public TopicCollection(String name) {
        super(name);
    }

    private String loadFromUrl(String url) {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);

            // add request header
            request.addHeader("User-Agent", env.getProperty(CRAWLER_USER_AGENT,
                    CRAWLER_USER_AGENT_DEFAULT));
            HttpResponse response = client.execute(request);

            LOGGER.debug("Recieved the response code "
                    + response.getStatusLine().getStatusCode() + " from " + url);

            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } catch (ClientProtocolException e) {
            LOGGER.error(e.getLocalizedMessage());
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage());
        }
        return null;
    }

    private String getTopicDescription(String topicFileName) {
        String topicUrlDetail = String.format(
                env.getProperty(TOPIC_DETAIL_URL, TOPIC_DETAIL_URL_DEFAULT),
                topicFileName);

        return StringUtils
                .substringBetween(
                        this.loadFromUrl(topicUrlDetail),
                        env.getProperty(TOPIC_DETAIL_BEGIN,
                                TOPIC_DETAIL_BEGIN_DEFAULT),
                        env.getProperty(TOPIC_DETAIL_END,
                                TOPIC_DETAIL_END_DEFAULT)).trim();
    }

    public ItemReader<JSONObject> reader() {
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

    public ItemProcessor<JSONObject, Map<String, Object>> itemProcessor() {
        return new ItemProcessor<JSONObject, Map<String, Object>>() {

            public Map<String, Object> process(JSONObject item)
                    throws IOException {
                // Populating useful variables
                String topicIdentifier = (String) item.get("identifier");
                String topicFileName = (String) item.get("topicFileName");
                LOGGER.info("Processing topic[" + topicIdentifier + "]");

                // Pulling full HTML description from the web
                String topicDetailHtml = getTopicDescription(topicFileName);

                // Converting HTML to plain text for Solr
                String topicDetailRaw = new HtmlToPlainText()
                        .getPlainText(Jsoup.parse(topicDetailHtml));

                // Creating the Field Map
                Map<String, Object> doc = new HashMap<String, Object>();
                doc.put("topicIdentifier", topicIdentifier);
                doc.put("title", (String) item.get("title"));
                doc.put("descriptionRaw", topicDetailRaw);
                doc.put("descriptionHtml", topicDetailHtml);
                doc.put("docType", "Topic H2020");
                doc.put("programme", "H2020");

                doc.put("tags", (JSONArray) item.get("tags"));
                doc.put("flags", (JSONArray) item.get("flags"));

                doc.put("callTitle", (String) item.get("callTitle"));
                doc.put("callIdentifier", (String) item.get("callIdentifier"));

                DateFormat df = new SimpleDateFormat(
                        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                df.setTimeZone(TimeZone.getTimeZone("UTC"));
                String utcDate = df.format(new Date((Long) item
                        .get("callDeadline")));

                doc.put("callDeadline", utcDate);
                doc.put("callStatus", (String) item.get("callStatus"));

                return doc;
            }
        };
    }

    public ItemWriter<Map<String, Object>> writer() {
        ItemWriter<Map<String, Object>> writer = new ItemWriter<Map<String, Object>>() {

            public void write(List<? extends Map<String, Object>> items) {
                for (Map<String, Object> fields : items) {
                    getSearchEngine().indexMap(getName(), fields);
                }
            }
        };
        return writer;
    }

    @Override
    protected FlowJobBuilder getJobFlow(JobBuilder builder) {
        Step step = stepBuilderFactory.get("getFile")
                .<JSONObject, Map<String, Object>> chunk(50).reader(reader())
                .processor(itemProcessor()).writer(writer()).build();

        return builder.flow(step).end();
    }
}
