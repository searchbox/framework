package com.searchbox.framework.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {
  
  private static final String EMAIL_SMTP_HOST = "email.smtp.host";
  private static final String EMAIL_SMTP_PORT = "email.smtp.port";
  private static final String EMAIL_SMTP_USER = "email.smtp.user";
  private static final String EMAIL_SMTP_PASSWORD = "email.smtp.password";
  
  @Bean
  public MailSender getMailSender(){
    JavaMailSenderImpl sender = new JavaMailSenderImpl();

    Properties props = new Properties();
    props.setProperty("mail.smtp.auth","true");
    props.setProperty("mail.smtp.socketFactory.port","465");
    props.setProperty("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
    props.setProperty("mail.smtp.socketFactory.fallback","false");
    sender.setJavaMailProperties(props);
    sender.setHost("email-smtp.us-east-1.amazonaws.com");
    sender.setPort(465);
    sender.setUsername("AKIAJXPXT6W37KX6OWJQ");
    sender.setPassword("Av914GMG3FdghIPTWjpuHGulY6KHGZkUkoAeVtxkO9Hj");
    return sender;
  }

}
