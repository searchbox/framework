/*******************************************************************************
 * Copyright Searchbox - http://www.searchbox.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.searchbox.framework.config;

import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@EnableSpringDataWebSupport
@ComponentScan(basePackages = { "com.searchbox.framework.web", "com.searchbox.framework.bootstrap" })
public class WebConfig extends WebMvcConfigurerAdapter {

  private static final String PROPERTY_NAME_MESSAGESOURCE_BASENAME = "message.source.basename";
  private static final String PROPERTY_NAME_MESSAGESOURCE_USE_CODE_AS_DEFAULT_MESSAGE = "message.source.use.code.as.default.message";

  @Resource
  Environment environment;

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/login").setViewName("login");
    registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
  }

  @Bean
  public MessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

    messageSource.setBasename(environment.getRequiredProperty(PROPERTY_NAME_MESSAGESOURCE_BASENAME));
    messageSource.setUseCodeAsDefaultMessage(Boolean.parseBoolean(environment
        .getRequiredProperty(PROPERTY_NAME_MESSAGESOURCE_USE_CODE_AS_DEFAULT_MESSAGE)));

    return messageSource;
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/assets/css/**").addResourceLocations("/css/").setCachePeriod(31556926);
    registry.addResourceHandler("/assets/images/**").addResourceLocations("/img/").setCachePeriod(31556926);
    registry.addResourceHandler("/assets/js/**").addResourceLocations("/js/").setCachePeriod(31556926);
    registry.setOrder(Ordered.LOWEST_PRECEDENCE);
  }

  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }

  @Bean
  public InternalResourceViewResolver getInternalResourceViewResolver() {
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setPrefix("/WEB-INF/views/");
    resolver.setSuffix(".jspx");
    resolver.setViewClass(JstlView.class);
    return resolver;
  }

  @Bean
  public SimpleMappingExceptionResolver exceptionResolver() {
    SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();

    Properties exceptionMappings = new Properties();

    exceptionMappings.put("java.lang.Exception", "error/error");
    exceptionMappings.put("java.lang.RuntimeException", "error/error");

    exceptionResolver.setExceptionMappings(exceptionMappings);

    Properties statusCodes = new Properties();

    statusCodes.put("error/404", "404");
    statusCodes.put("error/error", "500");

    exceptionResolver.setStatusCodes(statusCodes);

    return exceptionResolver;
  }
}
