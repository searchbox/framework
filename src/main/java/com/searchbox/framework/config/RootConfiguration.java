package com.searchbox.framework.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.stereotype.Controller;

@Configuration
@EnableSpringConfigured
@ComponentScan(basePackages={"com.searchbox"},
excludeFilters = {@ComponentScan.Filter(value = Controller.class, type = FilterType.ANNOTATION),
	@ComponentScan.Filter(pattern={"com\\.searchbox\\.framework\\.web\\..*"}, type = FilterType.REGEX),
	@ComponentScan.Filter(pattern={"com\\.searchbox\\.framework\\.bootstrap\\..*"}, type = FilterType.REGEX)
})
public class RootConfiguration {
	
}