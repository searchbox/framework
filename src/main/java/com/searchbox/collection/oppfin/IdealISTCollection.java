package com.searchbox.collection.oppfin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
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
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.searchbox.collection.AbstractBatchCollection;
import com.searchbox.collection.SynchronizedCollection;
import com.searchbox.framework.config.RootConfiguration;

@Configurable
@Component
public class IdealISTCollection extends AbstractBatchCollection implements
        SynchronizedCollection, InitializingBean {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(IdealISTCollection.class);

    private final static String CRAWLER_USER_AGENT = "crawler.userAgent";
    private final static String IDEALIST_LIST_SERVICE = "idealist.service.list.url";
    private final static String IDEALIST_DOCUMENT_SERVICE = "idealist.service.document.url";

    private final static String CRAWLER_USER_AGENT_DEFAULT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_1) AppleWebKit/537.73.11 (KHTML, like Gecko) Version/7.0.1 Safari/537.73.11";
    private final static String IDEALIST_LIST_SERVICE_DEFAULT = "http://www.ideal-ist.eu/idealist_oppfinder/documents?pageNum=PAGE_NUM&pageSize=PAGE_SIZE";
    // ?uid=XXX
    private final static String IDEALIST_DOCUMENT_SERVICE_DEFAULT = "http://www.ideal-ist.eu/idealist_oppfinder/document";

    DocumentBuilder db;

    public IdealISTCollection() {
        super("Idealist");
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

        HttpUriRequest request = builder
                .addHeader("User-Agent",
                        env.getProperty(CRAWLER_USER_AGENT,
                                CRAWLER_USER_AGENT_DEFAULT)).build();

        try {
            HttpResponse httpResponse = client.execute(request);
            InputStream ips = httpResponse.getEntity().getContent();
            BufferedReader buf = new BufferedReader(new InputStreamReader(ips,
                    "UTF-8"));
            if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                LOGGER.error("could read response ({}) for url: {}",
                        httpResponse.getStatusLine().getReasonPhrase(),
                        builder.getUri());
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
            LOGGER.info("Received: {}",sb.toString());
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

                if (page > 0)
                    return null;

                if (documents.isEmpty()) {
                    // pageNum=PAGE_NUM&pageSize=PAGE_SIZE
                    Document xmlDocuments = httpGet(RequestBuilder
                            .get()
                            .setUri(env.getProperty(IDEALIST_LIST_SERVICE,
                                    IDEALIST_LIST_SERVICE_DEFAULT))
                            .addParameter("pageNum", Integer.toString(page))
                            .addParameter("pageSize", "10"));
                    NodeList documentList = xmlDocuments
                            .getElementsByTagName("document");
                    for (int i = 0; i < documentList.getLength(); i++) {
                        String uid = documentList.item(i).getAttributes()
                                .getNamedItem("uid").getNodeValue();
                        documents.add(uid);
                    }
                    ;
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

    public ItemProcessor<String, FieldMap> itemProcessor() {
        return new ItemProcessor<String, FieldMap>() {
            @Override
            public FieldMap process(String uid) throws Exception {
                LOGGER.info("Fetching document uid={}", uid);
                Document document = httpGet(RequestBuilder.get()
                            .setUri(env.getProperty(IDEALIST_DOCUMENT_SERVICE,
                                    IDEALIST_DOCUMENT_SERVICE_DEFAULT))
                            .addParameter("uid", uid));
                LOGGER.info("Got Document: {}",document);
                return new FieldMap();
            }
        };
    }

    @Override
    protected FlowJobBuilder getJobFlow(JobBuilder builder) {
        Step step = stepBuilderFactory.get("getFile")
                .<String, FieldMap> chunk(2).reader(reader())
                .processor(itemProcessor()).writer(fieldMapWriter()).build();

        return builder.flow(step).end();
    }

    public static void main(String... args)
            throws JobExecutionAlreadyRunningException, JobRestartException,
            JobInstanceAlreadyCompleteException, JobParametersInvalidException,
            SAXException, IOException {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                RootConfiguration.class);

        IdealISTCollection collection = context
                .getBean(IdealISTCollection.class);

        collection.synchronize();
    }
}
