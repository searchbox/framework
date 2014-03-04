package com.searchbox.collection;

import java.io.File;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.searchbox.core.dm.Collection;

@Configurable
public abstract class AbstractBatchCollection extends Collection implements
		FileIndexing, SynchronizedCollection {

	private static Logger logger = LoggerFactory
			.getLogger(AbstractBatchCollection.class);

	@Autowired
	protected JobLauncher launcher;
	
	@Autowired
	protected JobRepository repository;

	public AbstractBatchCollection() {

	}

	public AbstractBatchCollection(String name) {
		this.name = name;
	}

	@Override
	public Date getLastUpdate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void synchronize() {
		Job job = this.getJob();
		JobParameters params = new JobParameters();
		
		JobExecution jobExecution;
		
		try {

			jobExecution = launcher.run(job, params);
			logger.info("JobExecution for pubmed: "
					+ jobExecution.getExitStatus().getExitCode());

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

	@Override
	public boolean indexFile(File file) {
		return this.searchEngine.indexFile(file);
	}

	protected abstract Job getJob();

}
