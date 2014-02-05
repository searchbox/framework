package com.searchbox.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.searchbox.ann.search.SearchComponent;
import com.searchbox.domain.search.SearchCondition;

@Service
public class SearchComponentService implements ApplicationListener<ContextRefreshedEvent> {

	private static Logger logger = LoggerFactory.getLogger(SearchComponentService.class);

	@Autowired
	private WebApplicationContext applicationContext;

	private Map<String, Class<?>> searchComponents;
	private Map<String, Class<?>> searchConditions;
	
	public SearchComponentService() {
		this.searchComponents = new HashMap<String, Class<?>>();
		this.searchConditions = new HashMap<String, Class<?>>();
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		logger.info("Scanning for SearchComponents");

		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(SearchComponent.class));
		for (BeanDefinition beanDefinition : scanner.findCandidateComponents("com.searchbox.domain.search")) {
			try {
				Class<?> searchComponent = Class.forName(beanDefinition.getBeanClassName());
				String prefix = ((SearchComponent) searchComponent.getAnnotation(SearchComponent.class)).prefix();
				Class<?> searchCondition = ((SearchComponent) searchComponent.getAnnotation(SearchComponent.class)).condition();
				
				logger.info("Found "+prefix+":"+searchComponent.getSimpleName()+" with filter["+searchCondition.getSimpleName()+"]");
				
				searchComponents.put(prefix, searchComponent);
				searchConditions.put(prefix, searchCondition);

			} catch (ClassNotFoundException e) {
				logger.error("Could not find class for: "+ beanDefinition.getBeanClassName());
			}
		}
	}
	
	public boolean isSearchConditionParam(String paramName){
		logger.debug("checking if "+paramName+" is a parameter for any SearchComponent");
		return this.searchConditions.keySet().contains(paramName);
	}
	
	public List<SearchCondition> getSearchCondition(String paramName, String values){
		logger.info("Creating a " + searchConditions.get(paramName).getSimpleName() +
				" for component: " + searchComponents.get(paramName).getSimpleName());
		
		//TODO use Component to generate the condition
		return Collections.emptyList();
	}

	public Set<String> getSearchConditionParams() {
		return this.searchConditions.keySet();
	}

}
