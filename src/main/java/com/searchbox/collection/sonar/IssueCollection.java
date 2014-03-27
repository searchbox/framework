package com.searchbox.collection.sonar;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.builder.FlowJobBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.searchbox.collection.AbstractBatchCollection;
import com.searchbox.collection.ProtocolListener;
import com.searchbox.collection.StandardCollection;
import com.searchbox.collection.SynchronizedCollection;
import com.searchbox.core.dm.Field;
import com.searchbox.core.dm.FieldMap;

@Configurable
public class IssueCollection extends AbstractBatchCollection implements
    SynchronizedCollection {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(IssueCollection.class);

  private final BasicDataSource sonarDataSource;
  
  public static List<Field> GET_FIELDS() {
    List<Field> fields = new ArrayList<Field>();
    fields.add(new Field(String.class, "assignee.id"));
    fields.add(new Field(String.class, "assignee.login"));
    fields.add(new Field(String.class, "assignee.name"));
    fields.add(new Field(String.class, "reporter.id"));
    fields.add(new Field(String.class, "reporter.login"));
    fields.add(new Field(String.class, "reporter.name"));
    fields.add(new Field(String.class, "author.login"));
    fields.add(new Field(String.class, "rule.id"));
    fields.add(new Field(String.class, "rule.name"));
    fields.add(new Field(String.class, "rule.status"));
    fields.add(new Field(String.class, "rule.description"));
    fields.add(new Field(String.class, "rule.language"));
    fields.add(new Field(String.class, "rule.plugin_name"));
    fields.add(new Field(String.class, "rule.priority"));
    fields.add(new Field(String.class, "rule.creation_date"));
    fields.add(new Field(String.class, "rule.updated_at"));
    fields.add(new Field(String.class, "root.id"));
    fields.add(new Field(String.class, "component.name"));
    fields.add(new Field(String.class, "component.id"));
    fields.add(new Field(String.class, "component.path"));
    fields.add(new Field(String.class, "id"));
    fields.add(new Field(String.class, "effort_to_fix"));
    fields.add(new Field(String.class, "status"));
    fields.add(new Field(String.class, "resolution"));
    fields.add(new Field(String.class, "creation_date"));
    fields.add(new Field(String.class, "close_date"));
    fields.add(new Field(String.class, "update_date"));
    fields.add(new Field(String.class, "severity"));
    fields.add(new Field(String.class, "line"));
    fields.add(new Field(String.class, "technical_debt"));    return fields;
    
  }

  public IssueCollection() {
    super("SonarIssues");
    sonarDataSource = new BasicDataSource();
    sonarDataSource.setDriverClassName("com.mysql.jdbc.Driver");
    sonarDataSource
        .setUrl("jdbc:mysql://localhost:13306/sonar?useUnicode=true&characterEncoding=utf8&rewriteBatchedStatements=true");
    sonarDataSource.setUsername("sonar");
    sonarDataSource.setPassword("sonar");
    sonarDataSource.setMinIdle(20);
    sonarDataSource.setMaxTotal(200);
    sonarDataSource.setDefaultReadOnly(true);
    sonarDataSource.setMaxOpenPreparedStatements(200);
    sonarDataSource.setMaxIdle(200);
  }

  @Override
  protected FlowJobBuilder getJobFlow(JobBuilder builder) {

    Step indexItems = null;
    Step push = null;

    ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
    taskExecutor.setMaxPoolSize(4);
    taskExecutor.afterPropertiesSet();

    try {
//      push = stepBuilderFactory.get("getIds").tasklet(readIds()).build();

      indexItems = stepBuilderFactory.get("indexItems")
          .<Long, FieldMap> chunk(200).reader(issueReader())
          .processor(issueProcessor()).writer(fieldMapWriter())
          .taskExecutor(taskExecutor).build();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return builder.listener(this).flow(indexItems).build();

  }

  private ItemReader<? extends Long> issueReader() throws SQLException,
      IOException {
    return new ItemReader<Long>() {

      private ResultSet results;
      private ResultSetMetaData rsmd;
      private Connection con;

      {
        LOGGER.info("Connecting to SonarDB {}", sonarDataSource);
        con = sonarDataSource.getConnection();
        con.setReadOnly(true);
        results = con.createStatement(ResultSet.FETCH_FORWARD,
            ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT id FROM issues");
        rsmd = results.getMetaData();
      }

      @Override
      public Long read() throws Exception, UnexpectedInputException,
          ParseException, NonTransientResourceException {

        if (results.next()) {
          return results.getLong("id");
        } else {
          results.getStatement().close();
          results.close();
          con.close();
          return null;
        }
      }

    };
  }

  private ItemProcessor<? super Long, ? extends FieldMap> issueProcessor()
      throws IOException, SQLException {
    return new ItemProcessor<Long, FieldMap>() {

      String query;

      {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        String path = "classpath:com/searchbox/collection/sonar/normalizedIssues.sql";
        StringWriter queryWriter = new StringWriter();
        IOUtils.copy(resolver.getResource(path).getInputStream(), queryWriter);
        query = queryWriter.toString();
      }

      @Override
      public FieldMap process(Long issueId) throws Exception {

        ResultSet results;
        ResultSetMetaData rsmd;
        Connection con;

        FieldMap map = new FieldMap();
        map.put("id", issueId);

        con = sonarDataSource.getConnection();
        con.setReadOnly(true);
        results = con.createStatement(ResultSet.FETCH_FORWARD,
            ResultSet.CONCUR_READ_ONLY).executeQuery(
            query.toString() + " WHERE issue.id = " + issueId);

        rsmd = results.getMetaData();

        if (results.next()) {
          for (int i = 0; i < rsmd.getColumnCount(); i++) {
            map.put(rsmd.getCatalogName(i + 1), results.getObject(i + 1));
          }
          results.getStatement().close();
          results.close();
          con.close();
          return map;
        } else {
          return null;
        }
      }

    };
  }

  private ItemWriter<? super FieldMap> pseudoWriter() {
    return new ItemWriter<FieldMap>() {

      @Override
      public void write(List<? extends FieldMap> items) throws Exception {
        for (FieldMap item : items) {
           //LOGGER.info("Field map: " + item.get("id"));
        }
      }
    };
  }

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
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
      }
    }).start();
  }

  public static void main(String... args)
      throws JobExecutionAlreadyRunningException, JobRestartException,
      JobInstanceAlreadyCompleteException, JobParametersInvalidException,
      NoSuchJobException, InterruptedException {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
        StandAloneBatchConfiguration.class);

    
    IssueCollection collection = context.getBeanFactory().createBean(
        IssueCollection.class);
    collection.synchronize();
  }
}
