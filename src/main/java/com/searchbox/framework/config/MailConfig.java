package com.searchbox.framework.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {
  
  private static final String EMAIL_SMTP_HOST = "mail.host";
  private static final String EMAIL_SMTP_PORT = "mail.port";
  private static final String EMAIL_SMTP_USER = "mail.user";
  private static final String EMAIL_SMTP_PASSWORD = "mail.password";
  
//  mail.smtp.auth
//  mail.smtp.socketFactory.port
//  mail.smtp.socketFactory.port
//  mail.smtp.socketFactory.class
//  mail.smtp.socketFactory.fallback
  
  @Bean
  public MailSender getMailSender(){
    JavaMailSenderImpl sender = new JavaMailSenderImpl();

//    Properties props = new Properties();
//    props.setProperty("mail.smtp.auth","true");
//    props.setProperty("mail.smtp.socketFactory.port","465");
//    props.setProperty("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
//    props.setProperty("mail.smtp.socketFactory.fallback","false");
//    sender.setJavaMailProperties(props);
//    sender.setHost("email-smtp.us-east-1.amazonaws.com");
//    sender.setPort(465);
//    sender.setUsername("AKIAJXPXT6W37KX6OWJQ");
//    sender.setPassword("Av914GMG3FdghIPTWjpuHGulY6KHGZkUkoAeVtxkO9Hj");
    sender.setHost("outlook.euresearch.ch");
    sender.setPort(587);
    return sender;
  }

}
