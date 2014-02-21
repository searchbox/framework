package com.searchbox.data;

import java.io.File;
import java.util.List;

import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
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
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.searchbox.service.SearchService;

@Component
public class PubmedCollection implements ApplicationListener<ContextRefreshedEvent> {

	private static Logger logger = LoggerFactory.getLogger(PubmedCollection.class);
	
	@Autowired
	ApplicationContext context;
	
	@Autowired
	SearchService searchService;

	public ItemReader<Resource> reader() {
		return new ItemReader<Resource>() {
			boolean hasmore = true;
			@Override
			public Resource read() throws Exception, UnexpectedInputException,
					ParseException, NonTransientResourceException {
				if(hasmore){
					hasmore = false;
					Resource resource = context.getResource("classpath:META-INF/data/pubmedIndex.xml");
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
					logger.error("Indexing for pubmed: " + item.getAbsolutePath());
					ContentStreamBase contentstream = new ContentStreamBase.FileStream(item);
					contentstream.setContentType("text/xml");
					ContentStreamUpdateRequest request = new ContentStreamUpdateRequest("/update");
					request.addContentStream(contentstream);
					request.process(searchService.getServer());
					searchService.getServer().commit();
					logger.info("Writing stuff for file: " + item.getAbsolutePath());					
				}
			}
		};
		return writer;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {


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
