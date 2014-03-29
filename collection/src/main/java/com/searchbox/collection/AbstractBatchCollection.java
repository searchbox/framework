package com.searchbox.collection;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.job.builder.FlowJobBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.env.Environment;

import com.searchbox.core.dm.DefaultCollection;
import com.searchbox.core.dm.FieldMap;
import com.searchbox.core.engine.ManagedSearchEngine;

@Configurable
public abstract class AbstractBatchCollection extends DefaultCollection
    implements SynchronizedCollection, JobExecutionListener {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(AbstractBatchCollection.class);

  @Resource
  protected Environment env;

  @Autowired
  protected StepBuilderFactory stepBuilderFactory;

  @Autowired
  protected JobLauncher launcher;

  @Autowired
  protected JobRepository repository;

  @Autowired
  protected JobBuilderFactory jobBuilderFactory;

  @Autowired
  protected JobExplorer explorer;

  private final DefaultCollection collection;

  public AbstractBatchCollection() {
    collection = this;
  }

  public AbstractBatchCollection(String name) {
    collection = this;
    this.name = name;
  }

  @Override
  public Date getLastUpdate() {
    JobInstance job = explorer.getJobInstances(this.getName(), 0, 1).get(0);
    JobExecution exec = explorer.getJobExecutions(job).get(0);
    return exec.getEndTime();
  }

  @Override
  public void synchronize() throws JobExecutionAlreadyRunningException,
      JobRestartException, JobInstanceAlreadyCompleteException,
      JobParametersInvalidException {

    JobParameters params = new JobParametersBuilder().addLong("time",
        System.currentTimeMillis()).toJobParameters();
    Job job = this.getJob();
    JobExecution jobExecution = launcher.run(job, params);
    LOGGER.info("JobExecution for collection: "
        + jobExecution.getExitStatus().getExitCode());

  }

  protected Job getJob() {

    JobBuilder builder = jobBuilderFactory.get(this.getName());

    Job job = getJobFlow(builder).build();

    return job;
  }

  protected abstract FlowJobBuilder getJobFlow(JobBuilder builder);

  @Override
  public void beforeJob(final JobExecution jobExecution) {
    LOGGER.info("Starting Batch Job");

    new Thread(new Runnable() {

      @Override
      public void run() {

        while (jobExecution.isRunning()) {
          LOGGER.info("Batch status: {}", jobExecution.getStatus());
          for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
            LOGGER.info("step: {}, read:{}, write: {}",
                stepExecution.getStepName(), stepExecution.getReadCount(),
                stepExecution.getWriteCount());
          }
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            ;
          }
        }
      }
    }).start();
  }

  @Override
  public void afterJob(JobExecution jobExecution) {
    // FIXME should wait for engine's commit here...
    try {
      LOGGER.info("Batch Job is over. need to update engine");
      if (this.searchEngine == null) {
        return;
      } else if (ManagedSearchEngine.class.isAssignableFrom(this.searchEngine
          .getClass())) {
        ((ManagedSearchEngine) this.searchEngine)
            .reloadPlugins(this.collection);
      }
    } catch (Exception e) {
      LOGGER.warn(
          "Error while reloading plugins. " + "(Nothing Commited yet?)", e);
    }
  }

  /** The abstractBatchColleciton has a few writters available */
  protected ItemWriter<FieldMap> fieldMapWriter() {
    ItemWriter<FieldMap> writer = new ItemWriter<FieldMap>() {
      public void write(List<? extends FieldMap> items) {

        List<Map<String, Object>> indexables = new ArrayList<>();

        for (FieldMap fields : items) {

          Map<String, Object> actualFields = new HashMap<String, Object>();

          // Manage STD fields for collection
          if (StandardCollection.class.isAssignableFrom(collection.getClass())) {
            if (((StandardCollection) collection).getIdValue(fields) == null) {
              throw new RuntimeException(
                  "Collection Implementing StandardColleciton "
                      + "cannot have a null value for field StandardCollection.STD_ID_FIELD");
            }
            actualFields.put(StandardCollection.STD_ID_FIELD,
                ((StandardCollection) collection).getIdValue(fields));

            if (((StandardCollection) collection).getTitleValue(fields) != null) {
              actualFields.put(StandardCollection.STD_TITLE_FIELD,
                  ((StandardCollection) collection).getTitleValue(fields));
            }

            if (((StandardCollection) collection).getPublishedValue(fields) != null) {
              actualFields.put(StandardCollection.STD_PUBLISHED_FIELD,
                  ((StandardCollection) collection).getPublishedValue(fields));
            }

            if (((StandardCollection) collection).getUpdateValue(fields) != null) {
              actualFields.put(StandardCollection.STD_UPDATED_FIELD,
                  ((StandardCollection) collection).getUpdateValue(fields));
            }

            if (((StandardCollection) collection).getBodyValue(fields) != null) {
              actualFields.put(StandardCollection.STD_BODY_FIELD,
                  ((StandardCollection) collection).getBodyValue(fields));
            }
          }

          // Manage STD_EXPIRE fields for collection
          if (ExpiringDocuments.class.isAssignableFrom(collection.getClass())) {
            actualFields.put(ExpiringDocuments.STD_DEADLINE_FIELD,
                ((ExpiringDocuments) collection).getDeadlineValue(fields));
          }

          for (Entry<String, List<Object>> field : fields.entrySet()) {
            if (!field.getValue().isEmpty()) {
              actualFields.put(field.getKey(),
                  (field.getValue().size() == 1) ? field.getValue().get(0)
                      : field.getValue());
            }
          }
          indexables.add(actualFields);

        }
        try {
          getSearchEngine().indexMap(collection, indexables);
        } catch (Exception e) {
          LOGGER.error("Could not index document", e);
        }
      }
    };
    return writer;
  }

  protected ItemWriter<File> fileWriter() {
    ItemWriter<File> writer = new ItemWriter<File>() {
      @Override
      public void write(List<? extends File> items) {
        for (File item : items) {
          getSearchEngine().indexFile(collection, item);
        }
      }
    };
    return writer;
  }
}
