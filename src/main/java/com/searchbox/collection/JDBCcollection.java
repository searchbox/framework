package com.searchbox.collection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.builder.FlowJobBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.searchbox.core.dm.FieldMap;

public abstract class JDBCcollection extends
    AbstractBatchCollection {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(JDBCcollection.class);

  private final DataSource dataSource;

  public JDBCcollection(String name) {
    super(name);
    dataSource = this.getDataSource();
  }

  abstract protected DataSource getDataSource();

  abstract protected String getQuery();
  
  private ItemReader<FieldMap> jdbcReader() throws SQLException {
    JdbcCursorItemReader<FieldMap> reader = new JdbcCursorItemReader<>();
    reader.setDataSource(this.dataSource);
    reader.setFetchSize(100);
    reader.setMaxItemCount(10);
    reader.setSql(getQuery());
    reader.setRowMapper(getFieldMapRowMapper());
    return reader;
//    JdbcPagingItemReader<FieldMap> reader = new JdbcPagingItemReader<>();
//    reader.setQueryProvider(getQueryProvider());
//    reader.setDataSource(this.dataSource);
//    reader.setMaxItemCount(10);
//    reader.setPageSize(100);
//    reader.setRowMapper(getFieldMapRowMapper());
//    return reader;
  }

  private RowMapper<FieldMap> getFieldMapRowMapper(){
    return new RowMapper<FieldMap>(){

      @Override
      public FieldMap mapRow(ResultSet result, int rowNum) throws SQLException {
        LOGGER.info("Processing row #{}",rowNum);
        FieldMap map = new FieldMap();
        ResultSetMetaData meta = result.getMetaData();
        for (int i = 0; i < meta.getColumnCount(); i++) {
          map.put(meta.getColumnLabel(i + 1), result.getObject(i + 1));
          LOGGER.debug("Adding to map -> [{}]: {}", 
              meta.getColumnLabel(i + 1),
              result.getObject(i + 1));
        }
        return map;
      }
    };
  }

  public ItemProcessor<FieldMap, FieldMap> processor(){
    return new ItemProcessor<FieldMap, FieldMap>(){
      @Override
      public FieldMap process(FieldMap item) throws Exception {
        return item;
      }
    };
  }
  
  @Override
  protected FlowJobBuilder getJobFlow(JobBuilder builder) {

    Step indexItems = null;

    ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
    taskExecutor.setMaxPoolSize(10);
    taskExecutor.afterPropertiesSet();

    try {

      indexItems = stepBuilderFactory.get("indexItems")
          .<FieldMap, FieldMap> chunk(200)
          .reader(jdbcReader())
          .processor(processor())
          .writer(fieldMapWriter())
          .taskExecutor(taskExecutor).build();

    } catch (Exception e) {
      LOGGER.error("Could not create step!", e);
    }

    return builder.listener(this).flow(indexItems).build();
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
            ;
          }
        }
      }
    }).start();
  }
}
