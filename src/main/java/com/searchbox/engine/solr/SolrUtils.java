package com.searchbox.engine.solr;

import java.net.MalformedURLException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolrUtils {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(SolrSearchEngine.class);

    public static String getSolrVersion(CloudSolrServer server) {
        

        return null;

    }

    public static void main(String... args) throws MalformedURLException{
        CloudSolrServer server = new CloudSolrServer("localhost:9983", true);
        server.connect();
        
        LOGGER.info("Engine version is: " + getSolrVersion(server));
        
        server.shutdown();
    }
}
