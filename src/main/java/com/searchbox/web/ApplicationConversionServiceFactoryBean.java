package com.searchbox.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.converter.RooConversionService;

import com.searchbox.domain.search.SearchCondition;
import com.searchbox.domain.search.facet.FieldFacet;
import com.searchbox.domain.search.query.SimpleQuery;
import com.searchbox.service.SearchComponentService;

@Configurable
/**
 * A central place to register application converters and formatters.
 */
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends
		FormattingConversionServiceFactoryBean {

	private static Logger logger = LoggerFactory.getLogger(ApplicationConversionServiceFactoryBean.class);

	@Autowired
	SearchComponentService searchComponentService;
	
	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}
	
	public Converter<String, FieldFacet.ValueCondition> getStringToFieldFacetConverter() {
		 return new Converter<String, FieldFacet.ValueCondition>() {
			@Override
			public FieldFacet.ValueCondition convert(String source) {
				logger.info("~~~ converting FF: " + source);
				//return searchComponentService.getSearchCondition(paramName, source)
				return null;
			}
		 };	
     }
	
	public Converter<String, SimpleQuery.Condition> getStringToSimpleQueryConverter() {
		 return new Converter<String, SimpleQuery.Condition>() {
			@Override
			public SimpleQuery.Condition convert(String source) {
				logger.info("~~~ converting Q: " + source);
				//return searchComponentService.getSearchCondition(paramName, source)
				return null;
			}
		 };	
     }


	public void installLabelConverters(FormatterRegistry registry) {
		
		for(Class condition:searchComponentService.getConditionClasses()){
			
		}
        registry.addConverter(getStringToSimpleQueryConverter());
        registry.addConverter(getStringToFieldFacetConverter());
//        registry.addConverter(getIdToPartConverter());
//        registry.addConverter(getStringToPartConverter());
//        registry.addConverter(getPlanToStringConverter());
//        registry.addConverter(getIdToPlanConverter());
//        registry.addConverter(getStringToPlanConverter());
    }
	
	public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
}
