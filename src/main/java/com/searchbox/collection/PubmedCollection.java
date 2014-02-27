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
package com.searchbox.collection;

import java.io.File;
import java.util.List;

import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.util.ContentStreamBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import com.searchbox.core.engine.SearchEngine;
import com.searchbox.engine.solr.EmbeddedSolr;
import com.searchbox.framework.service.SearchEngineService;

@Configurable
public class PubmedCollection {

	private static Logger logger = LoggerFactory.getLogger(PubmedCollection.class);
	
	@Autowired
	ApplicationContext context;
	
	@Autowired
	SearchEngineService searchEngineService;

	public ItemReader<Resource> reader() {
		return new ItemReader<Resource>() {
			boolean hasmore = true;
			@Override
			public Resource read() throws Exception, UnexpectedInputException,
					ParseException, NonTransientResourceException {
				if(hasmore){
					hasmore = false;
					Resource resource = context.getResource("classpath:data/pubmedIndex.xml");
					if(resource.exists()){
						logger.info("Read has created this resource: " + resource.getFilename());
						return resource;
					} 
				}
				return null;
			}
		};
	}

	public ItemProcessor<Resource, File> itemProcessor() {
		return new ItemProcessor<Resource, File>() {
			@Override
			public File process(Resource item) throws Exception {
				logger.info("Processing stuff here...");
				return item.getFile();
			}
		};
	}

	public ItemWriter<File> writer() {
		ItemWriter<File> writer = new ItemWriter<File>() {
			@Override
			public void write(List<? extends File> items) throws Exception {
				for(File item:items){
					logger.info("Indexing for pubmed: " + item.getAbsolutePath());
					ContentStreamBase contentstream = new ContentStreamBase.FileStream(item);
					contentstream.setContentType("text/xml");
					ContentStreamUpdateRequest request = new ContentStreamUpdateRequest("/update");
					request.addContentStream(contentstream);
					SearchEngine<?, ?> engine = searchEngineService.getSearchEngine("embedded Solr");
					
					UpdateResponse response = request.process(((EmbeddedSolr)engine).getServer());
					logger.info("Solr Response: " + response);
					response = ((EmbeddedSolr)engine).getServer().commit();
					logger.info("Solr commig: " + response);

				}
			}
		};
		return writer;
	}

	public void importCollection() {


		StepBuilderFactory stepBuilderFactory = context
				.getBean(StepBuilderFactory.class);
		JobBuilderFactory jobBuilderFactory = context
				.getBean(JobBuilderFactory.class);
		JobLauncher launcher = context.getBean(JobLauncher.class);
		
		Step step = stepBuilderFactory.get("getFile")
				.<Resource, File> chunk(1).reader(reader())
				.processor(itemProcessor()).writer(writer()).build();

		Job myJob = jobBuilderFactory.get("importFile")
				.incrementer(new RunIdIncrementer()).flow(step).end().build();
		
		JobParameters params = new JobParameters();

		JobExecution jobExecution;
		try {
			jobExecution = launcher.run(myJob, params);
			logger.info("JobExecution for pubmed: " + jobExecution.getExitStatus().getExitCode());

		} catch (JobExecutionAlreadyRunningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobRestartException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobInstanceAlreadyCompleteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobParametersInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
