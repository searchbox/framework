package com.searchbox.collection.sonar;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.builder.FlowJobBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.searchbox.collection.AbstractBatchCollection;
import com.searchbox.collection.JDBCcollection;
import com.searchbox.collection.SynchronizedCollection;
import com.searchbox.core.dm.Field;
import com.searchbox.core.dm.FieldMap;
import com.searchbox.engine.es.ElasticSearch;

@Configurable
public class SonarCollection extends JDBCcollection {
  
  private static final Logger LOGGER = LoggerFactory
      .getLogger(SonarCollection.class);

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
    fields.add(new Field(String.class, "technical_debt"));
    return fields;

  }

  private String processQuery;
  
  public SonarCollection() throws IOException{
    this("sonar");
  }
  
  public SonarCollection(String name) throws IOException {
    super(name);
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    String path = "classpath:com/searchbox/collection/sonar/normalizedIssues.sql";
    StringWriter queryWriter = new StringWriter();
    IOUtils.copy(resolver.getResource(path).getInputStream(), queryWriter);
    processQuery = queryWriter.toString();
  }

  protected DataSource getDataSource() {
    BasicDataSource sonarDataSource = new BasicDataSource();
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
    return sonarDataSource;
  }

  
  @Override
  protected String getQuery() {
    return this.processQuery;
  }
  
  public static void main(String... args) throws Exception {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
        StandAloneBatchConfiguration.class);

    SonarCollection collection = context.getBeanFactory().createBean(
        SonarCollection.class);
    collection.setIdFieldName("id");
    collection.setName("testing");

    ElasticSearch searchEngine = new ElasticSearch("ElasticSearch Engine");
    searchEngine.setClusterName("elasticsearch");
    searchEngine.afterPropertiesSet();
    // searchEngine.register(collection);

    collection.setSearchEngine(searchEngine);

    collection.synchronize();
  }
}
