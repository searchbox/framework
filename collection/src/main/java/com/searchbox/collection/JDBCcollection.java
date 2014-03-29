package com.searchbox.collection;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.searchbox.core.dm.Field;
import com.searchbox.core.dm.FieldMap;

public abstract class JDBCcollection extends
    AbstractBatchCollection implements InitializingBean {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(JDBCcollection.class);

  private DataSource dataSource;
  private String query;

  public JDBCcollection(String name) {
    super(name);
  }
  
  @Override
  public void afterPropertiesSet() throws Exception {
    this.dataSource = this.getDataSource();
    this.query = this.getQuery();
  }

  abstract protected DataSource getDataSource();

  abstract protected String getQuery();
 
  protected abstract Map<String, Order> getSortKeys();

  protected String getSelectClause() {
    return query.substring(query.indexOf("SELECT")+6,
        query.indexOf("FROM"));
  }
  
  protected String getFromClause() {
    return query.substring(query.indexOf("FROM")+4,
        Math.max(query.length(), query.indexOf("WHERE")));
  }
  
  protected String getWhereClause() {
    return query.substring(Math.max(query.indexOf("WHERE")+5,query.length()),
        Math.max(query.length(), query.indexOf("LIMIT")));
  }
 
  
  private PagingQueryProvider getQueryProvider() {
    MySqlPagingQueryProvider qprovider = new MySqlPagingQueryProvider();
    qprovider.setFromClause(getFromClause());
    qprovider.setSelectClause(getSelectClause());
    qprovider.setWhereClause(getWhereClause());
    qprovider.setSortKeys(getSortKeys());
    return qprovider;
  }

  private ItemReader<FieldMap> jdbcReader() throws Exception {
    JdbcPagingItemReader<FieldMap> reader = new JdbcPagingItemReader<>();
    reader.setQueryProvider(getQueryProvider());
    reader.setDataSource(this.dataSource);
    reader.setPageSize(500);
    reader.setRowMapper(getFieldMapRowMapper());
    reader.afterPropertiesSet();
    return reader;
  }

  private RowMapper<FieldMap> getFieldMapRowMapper(){
    return new RowMapper<FieldMap>(){

      @Override
      public FieldMap mapRow(ResultSet result, int rowNum) throws SQLException {
        LOGGER.debug("Processing row #{}",rowNum);
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
  
  public void detectFields() {
    try {
      FieldMap map = this.jdbcReader().read();
      for(String key:map.keySet()){
        this.getFields().add(new Field(map.getClazz(key), key));
        LOGGER.debug("[{}]",key, map.getClazz(key));
      }
    } catch (Exception e) {
      LOGGER.error("Could not peek in collection to detect fields",e);
    }
    
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
}
