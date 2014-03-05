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
package com.searchbox.engine.solr;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrResponse;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.util.ContentStreamBase;
import org.apache.solr.core.CoreContainer;
import org.apache.solr.core.CoreDescriptor;
import org.apache.solr.core.SolrCore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.core.SearchAttribute;
import com.searchbox.core.engine.AbstractSearchEngine;

public class EmbeddedSolr extends AbstractSearchEngine<SolrQuery, SolrResponse> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EmbeddedSolr.class);

	@SearchAttribute
	private String solrHome;

	@SearchAttribute
	private String dataDir;

	@SearchAttribute
	private String solrConfig;

	@SearchAttribute
	private String solrSchema;

	@SearchAttribute
	private String coreName;

	private static EmbeddedSolrServer server = null;

	public EmbeddedSolr() {
		super(SolrQuery.class, SolrResponse.class);
	}

	public EmbeddedSolr(String name, String solrHome) {
		super(name, SolrQuery.class, SolrResponse.class);
		this.solrHome = solrHome;
	}

	public void init() {
		if (EmbeddedSolr.server == null) {
			try {
				LOGGER.info("Embedded solr.solr.home is: " + this.solrHome);
				CoreContainer coreContainer = new CoreContainer(this.solrHome);
				coreContainer.load();

				String coreInstanceDir = this.solrHome;

				File dataDir = new File(this.dataDir);
				if (dataDir.exists()) {
					FileUtils.deleteDirectory(dataDir);
				}

				Properties properties = new Properties();
				properties.setProperty("dataDir", this.dataDir);

				CoreDescriptor dcore = new CoreDescriptor(coreContainer,
						coreName, coreInstanceDir, properties);

				SolrCore core = coreContainer.create(dcore);
				coreContainer.register(core, false);
				
				LOGGER.info("Solr Core config: " + core.getConfigResource());
				LOGGER.info("Solr SchemaResource: " + core.getSchemaResource());
				LOGGER.info("Solr Data dir: " + core.getDataDir());

				EmbeddedSolr.server = new EmbeddedSolrServer(coreContainer,
						"pubmed");

			} catch (Exception e) {
				LOGGER.error("Could not start search engine", e);
			}
		}
	}

	@Override
	public SolrResponse execute(SolrQuery query) {
		try {
			return EmbeddedSolr.server.query(query);
		} catch (SolrServerException e) {
			throw new RuntimeException("Could nexecute Query on  engine", e);
		}
	}

	public String getDataDir() {
		return dataDir;
	}

	public void setDataDir(String dataDir) {
		this.dataDir = dataDir;
	}

	public String getSolrConfig() {
		return solrConfig;
	}

	public void setSolrConfig(String solrConfig) {
		this.solrConfig = solrConfig;
	}

	public String getSolrSchema() {
		return solrSchema;
	}

	public void setSolrSchema(String solrSchema) {
		this.solrSchema = solrSchema;
	}

	public String getCoreName() {
		return coreName;
	}

	public void setCoreName(String coreName) {
		this.coreName = coreName;
	}

	public String getSolrHome() {
		return solrHome;
	}

	public void setSolrHome(String solrHome) {
		this.solrHome = solrHome;
	}

	@Override
	public boolean indexFile(File file) {
		LOGGER.info("Indexing for pubmed: " + file.getAbsolutePath());
		ContentStreamBase contentstream = new ContentStreamBase.FileStream(file);
		contentstream.setContentType("text/xml");
		ContentStreamUpdateRequest request = new ContentStreamUpdateRequest(
				"/update");
		request.addContentStream(contentstream);
		UpdateResponse response;
		try {
			response = request.process(EmbeddedSolr.server);
			LOGGER.info("Solr Response: " + response);
			response = EmbeddedSolr.server.commit();
			LOGGER.info("Solr commit: " + response);
			return true;
		} catch (SolrServerException | IOException e) {
			LOGGER.error("Could not index file: " + file, e);
		}
		return false;
	}

	@Override
	public boolean indexMap(Map<String, Object> fields) {
		// TODO Auto-generated method stub
		return false;
	}
}
