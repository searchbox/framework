package com.searchbox.collection;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.job.builder.FlowJobBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.searchbox.core.dm.Collection;
import com.searchbox.core.engine.ManagedSearchEngine;

@Configurable
public abstract class AbstractBatchCollection extends Collection 
	implements SynchronizedCollection, JobExecutionListener{

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractBatchCollection.class);

	@Autowired
	protected JobLauncher launcher;
	
	@Autowired
	protected JobRepository repository;

	@Autowired
	JobBuilderFactory jobBuilderFactory;

	@Autowired
	protected JobExplorer explorer;
	

	public AbstractBatchCollection() {

	}

	public AbstractBatchCollection(String name) {
		this.name = name;
	}

	@Override
	public Date getLastUpdate() {
		JobInstance job = explorer.getJobInstances(this.getName(), 0, 1).get(0);
		JobExecution exec = explorer.getJobExecutions(job).get(0);
		return exec.getEndTime();
	}

	@Override
	public void synchronize() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		
	
		JobParameters params = 
				  new JobParametersBuilder()
				  .addLong("time",System.currentTimeMillis()).toJobParameters();
		Job job = this.getJob();
		JobExecution jobExecution = launcher.run(job, params);
		LOGGER.info("JobExecution for collection: "
				+ jobExecution.getExitStatus().getExitCode());


	}
	
	protected Job getJob(){

		JobBuilder builder = jobBuilderFactory.get(this.getName())
				.incrementer(new RunIdIncrementer())
				.listener(this);
		
		Job job = getJobFlow(builder).build();
		
		return job;
	}

	protected abstract FlowJobBuilder getJobFlow(JobBuilder builder);
	

	@Override
	public void beforeJob(JobExecution jobExecution) {
		LOGGER.info("Starting Batch Job");
		
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
//		LOGGER.info("Batch Job is over. need to update engine");
//		if(this.searchEngine == null){
//			return;
//		} else if(ManagedSearchEngine.class.isAssignableFrom(this.searchEngine.getClass())){
//			((ManagedSearchEngine)this.searchEngine).reloadPlugins();
//		}		
	}
}
