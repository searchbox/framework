package com.searchbox.collection.sonar;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.database.Order;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.searchbox.collection.JDBCcollection;
import com.searchbox.core.dm.Field;
import com.searchbox.core.engine.ManagedSearchEngine;
import com.searchbox.engine.es.ElasticSearch;

@Configurable
public class SonarCollection extends JDBCcollection {
  
  private static final Logger LOGGER = LoggerFactory
      .getLogger(SonarCollection.class);

  public static List<Field> GET_FIELDS() {
    List<Field> fields = new ArrayList<Field>();
    fields.add(new Field(Long.class, "assignee.id"));
    fields.add(new Field(String.class, "assignee.login"));
    fields.add(new Field(String.class, "assignee.name"));
    fields.add(new Field(Long.class, "reporter.id"));
    fields.add(new Field(String.class, "reporter.login"));
    fields.add(new Field(String.class, "reporter.name"));
    fields.add(new Field(String.class, "author.login"));
    fields.add(new Field(Long.class, "rule.id"));
    fields.add(new Field(String.class, "rule.name"));
    fields.add(new Field(String.class, "rule.status"));
    fields.add(new Field(String.class, "rule.description"));
    fields.add(new Field(String.class, "rule.language"));
    fields.add(new Field(String.class, "rule.plugin_name"));
    fields.add(new Field(String.class, "rule.priority"));
    fields.add(new Field(Date.class, "rule.creation_date"));
    fields.add(new Field(Date.class, "rule.updated_at"));
    fields.add(new Field(Long.class, "root.id"));
    fields.add(new Field(String.class, "component.name"));
    fields.add(new Field(Long.class, "component.id"));
    fields.add(new Field(File.class, "component.path"));
    fields.add(new Field(Long.class, "id"));
    fields.add(new Field(String.class, "effort_to_fix"));
    fields.add(new Field(String.class, "status"));
    fields.add(new Field(String.class, "resolution"));
    fields.add(new Field(Date.class, "creation_date"));
    fields.add(new Field(Date.class, "close_date"));
    fields.add(new Field(Date.class, "update_date"));
    fields.add(new Field(String.class, "severity"));
    fields.add(new Field(Integer.class, "line"));
    fields.add(new Field(Integer.class, "technical_debt"));
    return fields;

  }


  
  public SonarCollection() throws IOException{
    this("sonar");
  }
  
  public SonarCollection(String name) throws IOException {
    super(name);
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
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    String path = "classpath:com/searchbox/collection/sonar/normalizedIssues.sql";
    StringWriter queryWriter = new StringWriter();
    try {
      IOUtils.copy(resolver.getResource(path).getInputStream(), queryWriter);
    } catch (IOException e) {
      LOGGER.error("Could not read from query file.",e);
    }
    return queryWriter.toString();
  }
  
  @Override
  protected Map<String, Order> getSortKeys() {
      Map<String, Order> sort = new HashMap<>();
      sort.put("issue.id", Order.ASCENDING);
      return sort;
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
    searchEngine.register(collection);

    collection.setSearchEngine(searchEngine);
    
    for(Field field:collection.getFields()){
      LOGGER.info("{}:\t{}",field.getClazz(), field.getKey());
    }
    
    //collection.detectFields();
    
   // collection.synchronize();
  }
}
