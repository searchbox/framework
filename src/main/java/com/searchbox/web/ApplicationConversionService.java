package com.searchbox.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.stereotype.Component;

import com.searchbox.anno.SearchComponent;

@Component("conversionService")
public class ApplicationConversionService extends DefaultFormattingConversionService  { 

	private static Logger logger = LoggerFactory.getLogger(ApplicationConversionService.class);

    public ApplicationConversionService(){
        //DefaultFormattingConversionService's default constructor
        //creates default formatters and converters
        super();
        
    	logger.info("Scanning for SearchComponents");

		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(
				false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(SearchComponent.class));
		for (BeanDefinition beanDefinition : scanner
				.findCandidateComponents("com.searchbox")) {
			try {
				Class<?> searchComponent = Class.forName(beanDefinition
						.getBeanClassName());
				String prefix = ((SearchComponent) searchComponent
						.getAnnotation(SearchComponent.class)).prefix();
				Class<?> searchCondition = ((SearchComponent) searchComponent
						.getAnnotation(SearchComponent.class)).condition();
				Class<?> conditionConverter = ((SearchComponent) searchComponent
						.getAnnotation(SearchComponent.class)).converter();

				if(conditionConverter!=null && Converter.class.isAssignableFrom(conditionConverter)){
	
					logger.info("XOXOXOXO Found " + prefix + ":"
							+ searchComponent.getSimpleName() + " with filter["
							+ searchCondition.getName() + "]");
	
					this.addConverter((Converter<?, ?>) conditionConverter.newInstance());
				}

			} catch (ClassNotFoundException e) {
				logger.error("Could not find class for: "
						+ beanDefinition.getBeanClassName());
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
    }
}