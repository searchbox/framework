/*******************************************************************************
 * Copyright Euresearch - 2014 - http://www.euresearch.ch
 * Proprietary software license.
 *******************************************************************************/

package com.searchbox.collection.oppfin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.lf5.util.DateFormatManager;
import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowJobBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.searchbox.collection.AbstractBatchCollection;
import com.searchbox.collection.StandardCollection;
import com.searchbox.collection.SynchronizedCollection;
import com.searchbox.core.dm.Field;
import com.searchbox.core.dm.FieldMap;

@Configurable
@Component
public class IdealISTCollection extends AbstractBatchCollection implements
    SynchronizedCollection, StandardCollection, InitializingBean {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(IdealISTCollection.class);

  private final static String CRAWLER_USER_AGENT = "crawler.userAgent";
  private final static String IDEALIST_LIST_SERVICE = "idealist.service.list.url";
  private final static String IDEALIST_DOCUMENT_SERVICE = "idealist.service.document.url";

  private final static String CRAWLER_USER_AGENT_DEFAULT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_1) AppleWebKit/537.73.11 (KHTML, like Gecko) Version/7.0.1 Safari/537.73.11";
  private final static String IDEALIST_LIST_SERVICE_DEFAULT = "http://www.ideal-ist.eu/idealist_oppfinder/documents";
  private final static String IDEALIST_DOCUMENT_SERVICE_DEFAULT = "http://www.ideal-ist.eu/idealist_oppfinder/document";

  DocumentBuilder db;

  DateFormat dfmt = new DateFormatManager("dd/MM/yyyy").getDateFormatInstance();

  public IdealISTCollection() {
    super("Idealist");
  }

  public static List<Field> GET_FIELDS() {
    List<Field> fields = new ArrayList<Field>();
    fields.add(new Field(String.class, "uid"));
    fields.add(new Field(String.class, "docSource"));
    fields.add(new Field(String.class, "docType"));
    fields.add(new Field(String.class, "programme"));

    fields.add(new Field(String.class, "idealistTitle"));
    fields.add(new Field(String.class, "idealistPsId"));
    fields.add(new Field(String.class, "idealistStatus"));
    
    fields.add(new Field(Date.class, "idealistDeadline"));
    fields.add(new Field(Date.class, "idealistUpdated"));
    fields.add(new Field(Date.class, "idealistPublished"));
    
    fields.add(new Field(String.class, "idealistObjective"));
    fields.add(new Field(String.class, "idealistFundingScheme"));
    fields.add(new Field(String.class, "idealistEvaluationScheme"));
    fields.add(new Field(String.class, "idealistTypeOfPartnerSought"));
    fields.add(new Field(String.class, "idealistCoordinationPossible"));
    fields.add(new Field(String.class, "idealistOrganisation"));
    fields.add(new Field(String.class, "idealistDepartement"));
    fields.add(new Field(String.class, "idealistTypeOfOrganisation"));
    fields.add(new Field(String.class, "idealistCountry"));
    fields.add(new Field(String.class, "idealistBody"));
    fields.add(new Field(String.class, "idealistOutline"));
    fields.add(new Field(String.class, "idealistDescriptionOfWork"));

    fields.add(new Field(String.class, "callIdentifier"));

    return fields;
  }
  
  @Override
  public String getIdValue(FieldMap fields) {
    return (String) fields.get(this.getIdFieldName()).get(0);
  }

  @Override
  public String getBodyValue(FieldMap fields) {
        return (String) fields.get("idealistBody").get(0);
  }

  @Override
  public String getTitleValue(FieldMap fields) {
    return (String) fields.get("idealistTitle").get(0);
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
  public void afterPropertiesSet() throws Exception {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    db = dbf.newDocumentBuilder();
  }

  private Document buildXMLDocument(String xml) throws SAXException,
      IOException {
    InputSource is = new InputSource();
    is.setCharacterStream(new StringReader(xml));
    Document doc = db.parse(is);
    return doc;
  }

  private Document httpGet(RequestBuilder builder) {
    HttpClient client = HttpClientBuilder.create().build();

    HttpUriRequest request = builder.addHeader("User-Agent",
        env.getProperty(CRAWLER_USER_AGENT, CRAWLER_USER_AGENT_DEFAULT))
        .build();

    try {
      HttpResponse httpResponse = client.execute(request);
      InputStream ips = httpResponse.getEntity().getContent();
      BufferedReader buf = new BufferedReader(new InputStreamReader(ips,
          "UTF-8"));
      if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
        LOGGER.error("could read response ({}) for url: {}", httpResponse
            .getStatusLine().getReasonPhrase(), builder.getUri());
      }
      StringBuilder sb = new StringBuilder();
      String s;
      while (true) {
        s = buf.readLine();
        if (s == null || s.length() == 0)
          break;
        sb.append(s);

      }
      buf.close();
      ips.close();

      return buildXMLDocument(sb.toString());

    } catch (Exception e) {
      LOGGER.error("Could not get XML from {}", builder.getUri(), e);
    }
    return null;
  }

  public ItemReader<String> reader() {
    return new ItemReader<String>() {

      List<String> documents;
      int page = 0;

      {
        documents = new ArrayList<String>();
      }

      @Override
      public String read() {

        if (documents.isEmpty()) {
          Document xmlDocuments = httpGet(RequestBuilder
              .get()
              .setUri(
                  env.getProperty(IDEALIST_LIST_SERVICE,
                      IDEALIST_LIST_SERVICE_DEFAULT))
              .addParameter("pageNum", Integer.toString(page))
              .addParameter("pageSize", "10"));
          NodeList documentList = xmlDocuments.getElementsByTagName("document");
          for (int i = 0; i < documentList.getLength(); i++) {
            String uid = documentList.item(i).getAttributes()
                .getNamedItem("uid").getNodeValue();
            documents.add(uid);
          }

          page++;
        }

        if (documents.isEmpty()) {
          return null;
        } else {
          return documents.remove(0);
        }
      }
    };
  }

  private void addDateField(String uid, Document document, FieldMap fields,
      String key, String fieldName) {
    this.addField(uid, document, fields, Date.class, key, fieldName);
  }

  private void addStringField(String uid, Document document, FieldMap fields,
      String key, String fieldName) {
    this.addField(uid, document, fields, String.class, key, fieldName);
  }

  private void addField(String uid, Document document, FieldMap fields,
      Class<?> clazz, String key, String fieldName) {
    XPath xPath = XPathFactory.newInstance().newXPath();
    String metaDataExpression = "//metadata[@key='" + key + "']|"
        + "//content[@cid='" + key + "']";

    // read a nodelist using xpath
    NodeList nodeList;
    try {
      nodeList = (NodeList) xPath.compile(metaDataExpression).evaluate(
          document, XPathConstants.NODESET);

      for (int i = 0; i < nodeList.getLength(); i++) {
        String value = nodeList.item(i).getTextContent();
        if (String.class.isAssignableFrom(clazz)) {
          String content = new HtmlToPlainText().getPlainText(Jsoup
              .parse(value));
          fields.put(fieldName, content);
          fields.put(fieldName + "Html", value);
        } else if (Date.class.isAssignableFrom(clazz)) {
          Date date;
          try {
            date = dfmt.parse(value);
            fields.put(fieldName, date);
          } catch (ParseException e) {
            LOGGER.warn("Could not parse date", e);
          }
        }
      }
    } catch (XPathExpressionException e) {
      LOGGER
          .warn("Could not execute XPATH for key {} in document {}", key, uid);
    }
  }

  public ItemProcessor<String, FieldMap> itemProcessor() {
    return new ItemProcessor<String, FieldMap>() {
      @Override
      public FieldMap process(String uid) throws Exception {
        LOGGER.info("Fetching document uid={}", uid);
        Document document = httpGet(RequestBuilder
            .get()
            .setUri(
                env.getProperty(IDEALIST_DOCUMENT_SERVICE,
                    IDEALIST_DOCUMENT_SERVICE_DEFAULT))
            .addParameter("uid", uid));
        LOGGER.debug("Got Document: {}", document);
        FieldMap fields = new FieldMap();

        fields.put("uid", uid);
        fields.put("docSource", "Ideal-Ist");
        fields.put("docType", "Collaboration");
        fields.put("programme", "H2020");

        addStringField(uid, document, fields, "title", "idealistTitle");
        addStringField(uid, document, fields, "PS_ID", "idealistPsId");
        addStringField(uid, document, fields, "Status", "idealistStatus");
        addDateField(uid, document, fields, "Date_of_last_Modification",
            "idealistUpdated");
        addDateField(uid, document, fields, "Date_of_Publication",
            "idealistPublished");
        addStringField(uid, document, fields, "Call_Identifier",
            "callIdentifier");
        addStringField(uid, document, fields, "Objective", "idealistObjective");
        addStringField(uid, document, fields, "Funding_Schemes",
            "idealistFundingScheme");
        addStringField(uid, document, fields, "Evaluation_Scheme",
            "idealistEvaluationScheme");
        addDateField(uid, document, fields, "Closure_Date", "idealistDeadline");
        addStringField(uid, document, fields, "Type_of_partner_sought",
            "idealistTypeOfPartnerSought");
        addStringField(uid, document, fields, "Coordinator_possible",
            "idealistCoordinationPossible");
        addStringField(uid, document, fields, "Organisation",
            "idealistOrganisation");
        addStringField(uid, document, fields, "Department",
            "idealistDepartement");
        addStringField(uid, document, fields, "Type_of_Organisation",
            "idealistTypeOfOrganisation");
        addStringField(uid, document, fields, "Country", "idealistCountry");
        addStringField(uid, document, fields, "Body", "idealistBody");
        addStringField(uid, document, fields, "outline", "idealistOutline");
        addStringField(uid, document, fields, "description_of_work",
            "idealistDescriptionOfWork");

        if (LOGGER.isDebugEnabled()) {
          for (String key : fields.keySet()) {
            LOGGER.debug("field: {}\t{}", key, fields.get(key));
          }
        }

        return fields;
      }
    };
  }

  @Override
  protected FlowJobBuilder getJobFlow(JobBuilder builder) {
    Step step = stepBuilderFactory.get("getFile").<String, FieldMap> chunk(10)
        .reader(reader()).processor(itemProcessor()).writer(fieldMapWriter())
        .build();

    return builder.flow(step).end();
  }

  public static void main(String... args)
      throws JobExecutionAlreadyRunningException, JobRestartException,
      JobInstanceAlreadyCompleteException, JobParametersInvalidException,
      SAXException, IOException {

    // AnnotationConfigApplicationContext context = new
    // AnnotationConfigApplicationContext(
    // RootConfiguration.class);
    //
    // IdealISTCollection collection = context
    // .getBean(IdealISTCollection.class);
    //
    // collection.synchronize();

  }
}
