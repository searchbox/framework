package com.searchbox.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.searchbox.web" })
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
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean(name="conversionService")
    public ConversionService getConversionService() {
		DefaultFormattingConversionService conversionService = 
				new DefaultFormattingConversionService(true);
        return conversionService;
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