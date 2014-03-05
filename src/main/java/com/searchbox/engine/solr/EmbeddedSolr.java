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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrResponse;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.SolrResponseBase;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.util.ContentStreamBase;
import org.apache.solr.core.CoreContainer;
import org.apache.solr.core.CoreDescriptor;
import org.apache.solr.core.SolrCore;
import org.apache.solr.schema.CopyField;
import org.apache.solr.schema.IndexSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.core.SearchAttribute;
import com.searchbox.core.dm.Field;
import com.searchbox.core.dm.FieldAttribute;
import com.searchbox.core.engine.AbstractSearchEngine;
import com.searchbox.core.engine.ManagedSearchEngine;

public class EmbeddedSolr extends AbstractSearchEngine<SolrQuery, SolrResponse> 
	implements ManagedSearchEngine {

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
	private static SolrCore core = null;

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

				EmbeddedSolr.core = coreContainer.create(dcore);
				
				coreContainer.register(EmbeddedSolr.core, false);
				
				LOGGER.info("Solr Core config: " + EmbeddedSolr.core.getConfigResource());
				LOGGER.info("Solr SchemaResource: " + EmbeddedSolr.core.getSchemaResource());
				LOGGER.info("Solr Data dir: " + EmbeddedSolr.core.getDataDir());

				
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
		SolrInputDocument document = new SolrInputDocument();
		for(Entry<String, Object> entry:fields.entrySet()){
			document.addField(entry.getKey(), entry.getValue());
		}
		UpdateRequest update = new UpdateRequest();
		update.add(document);
		try {
			UpdateResponse response = update.process(EmbeddedSolr.server);
			LOGGER.info("Updated FieldMap with status: " + response.getStatus());
			return true;
		} catch (Exception e){
			LOGGER.error("Could not index FieldMap",e);
			return false;
		}
	}

	@Override
	public boolean updateForField(Field field, FieldAttribute fieldAttribute) {
		
		/** Get the translation for the field's key */
		
		
		
		IndexSchema schema = EmbeddedSolr.core.getLatestSchema();
		List<CopyField> copyFields = schema.getCopyFieldsList(field.getKey());
		
		
		return false;
	}

	@Override
	public List<String> getKeyForField(Field field, FieldAttribute fieldAttribute) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getKeyForField(Field field, FieldAttribute fieldAttribute,
			String operation) {
		// TODO Auto-generated method stub
		return null;
	}
}
