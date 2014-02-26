package com.searchbox.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@ComponentScan("com.searchbox.framework.web")
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/META-INF/resources/webjars/").setCachePeriod(31556926);

		registry.addResourceHandler("/assets/css/**")
				.addResourceLocations("/css/").setCachePeriod(31556926);
		registry.addResourceHandler("/assets/images/**")
				.addResourceLocations("/img/").setCachePeriod(31556926);
		registry.addResourceHandler("/assets/js/**")
				.addResourceLocations("/js/").setCachePeriod(31556926);
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Bean
	public OpenEntityManagerInViewFilter getOpenEntityManagerInViewFilter(){
		OpenEntityManagerInViewFilter filter = new OpenEntityManagerInViewFilter();
		return filter;
	}
	
	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jspx");
		resolver.setViewClass(JstlView.class);
		return resolver;
	}
}