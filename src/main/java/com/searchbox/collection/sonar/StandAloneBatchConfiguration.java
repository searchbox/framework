package com.searchbox.collection.sonar;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.sql.DataSource;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableBatchProcessing
public class StandAloneBatchConfiguration  {

  @Bean
  public DataSource dataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();
    return embeddedDatabaseBuilder
        .addScript("classpath:org/springframework/batch/core/schema-h2.sql")
        .setType(EmbeddedDatabaseType.H2).build();
  }

  @Bean
  public TaskExecutor taskExecutor() {
    ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
    taskExecutor.setMaxPoolSize(4);
    taskExecutor.afterPropertiesSet();
    return taskExecutor;
  }

  @Bean
  public JobExplorerFactoryBean jobExplorerFactoryBean(DataSource datasource)
      throws Exception {
    JobExplorerFactoryBean factory = new JobExplorerFactoryBean();
    factory.setDataSource(datasource);
    return factory;
  }
  
//  @Bean
//  public ConnectionFactory connectionFactory() {
//          return new ActiveMQConnectionFactory("tcp://localhost:61616");
//  }
//
//  @Bean
//  public Queue queue() {
//          return new ActiveMQQueue("queueName");
//  }
//
//  @Bean
//  public BrokerService broker() throws Exception{
//          BrokerService broker = new BrokerService();
//          // configure the broker
//          broker.addConnector("tcp://localhost:61616");
//          broker.start();
//          return broker;
//  }
//
//  @Bean
//  public JmsTemplate jmsTemplate(){
//          JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
//          jmsTemplate.setDefaultDestination(queue());
//          jmsTemplate.setReceiveTimeout(500);
//          return jmsTemplate;
//  }

}