package com.searchbox.collection.sonar;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.searchbox.collection.SynchronizedCollection;
import com.searchbox.core.dm.Field;
import com.searchbox.core.dm.FieldMap;
import com.searchbox.engine.es.ElasticSearch;

@Configurable
public class NormalizedIssueCollection extends AbstractBatchCollection implements
    SynchronizedCollection {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(NormalizedIssueCollection.class);

  private final BasicDataSource sonarDataSource;
  
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

  public NormalizedIssueCollection() {
    super("sonar");
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
      // push = stepBuilderFactory.get("getIds").tasklet(readIds()).build();

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
        
        con = sonarDataSource.getConnection();
        con.setReadOnly(true);
        results = con.createStatement(ResultSet.FETCH_FORWARD,
            ResultSet.CONCUR_READ_ONLY).executeQuery(
            query.toString() + " WHERE issue.id = " + issueId);

        rsmd = results.getMetaData();

        //LOGGER.info("got columns: {}",rsmd.get);
        if (results.next()) {
          for (int i = 0; i < rsmd.getColumnCount(); i++) {
            map.put(rsmd.getColumnLabel(i + 1), results.getObject(i + 1));
            LOGGER.debug("Adding to map -> [{}]: {}", rsmd.getColumnLabel(i + 1),
                results.getObject(i + 1));
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
          // LOGGER.info("Field map: " + item.get("id"));
        }
      }
    };
  }

  public static void main(String... args) throws Exception {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
        StandAloneBatchConfiguration.class);

    NormalizedIssueCollection collection = context.getBeanFactory().createBean(
        NormalizedIssueCollection.class);
    collection.setIdFieldName("id");

    ElasticSearch searchEngine = new ElasticSearch("ElasticSearch Engine");
    searchEngine.setClusterName("elasticsearch");
    searchEngine.afterPropertiesSet();
    // searchEngine.register(collection);

    collection.setSearchEngine(searchEngine);

    collection.synchronize();
  }
}
