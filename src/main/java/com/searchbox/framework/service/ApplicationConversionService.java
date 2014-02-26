package com.searchbox.framework.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.stereotype.Service;

import com.searchbox.core.SearchComponent;
import com.searchbox.core.SearchConverter;
import com.searchbox.core.search.SearchCondition;
import com.searchbox.framework.domain.PresetDefinition;
import com.searchbox.framework.domain.Searchbox;
import com.searchbox.framework.repository.PresetRepository;
import com.searchbox.framework.repository.SearchboxRepository;

@Service("conversionService")
@Configurable
public class ApplicationConversionService {

	@Autowired
	private ApplicationContext context;

	@Autowired
	private DefaultFormattingConversionService conversionService;

	@Autowired
	private PresetRepository presetRepository;

	@Autowired
	private SearchboxRepository searchboxRepository;

	private static Logger logger = LoggerFactory
			.getLogger(ApplicationConversionService.class);

	private Map<String, Class<?>> searchConditions;

	public ApplicationConversionService() {
		this.searchConditions = new HashMap<String, Class<?>>();
	}

	@PostConstruct
	public void init() {

		logger.info("Scanning for SearchComponents");
		Map<Class<?>, String> conditionUrl = new HashMap<Class<?>, String>();		
		ClassPathScanningCandidateComponentProvider scanner;
		

		//Getting all the SearchElements
		scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(SearchComponent.class));
		for (BeanDefinition bean:scanner.findCandidateComponents("com.searchbox")) {
			try {
				Class<?> clazz = Class.forName(bean.getBeanClassName());
				String urlParam = clazz.getAnnotation(SearchComponent.class).urlParam();
				if(urlParam != null && !urlParam.isEmpty()){
					logger.info("Getting condition for: "+ urlParam + " in class: " + clazz.getSimpleName());
					ParameterizedType pi = (ParameterizedType)clazz.getGenericSuperclass();
					for (Type piarg : pi.getActualTypeArguments()) {					
						if (SearchCondition.class.isAssignableFrom(((Class<?>) piarg))) {
							conditionUrl.put(((Class<?>) piarg), urlParam);
						}
					}
				}
			} catch (Exception e) {
				logger.error("Could not introspect SearchElement: " + bean,e);
			}
		}
		
		//Getting all converters for SearchConditions
		scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(SearchConverter.class));
		for (BeanDefinition bean : scanner
				.findCandidateComponents("com.searchbox")) {
			try {
				Class<?> clazz = Class.forName(bean.getBeanClassName());
				for (Type i : clazz.getGenericInterfaces()) {
					ParameterizedType pi = (ParameterizedType) i;
					for (Type piarg : pi.getActualTypeArguments()) {								
						if (SearchCondition.class.isAssignableFrom(((Class<?>) piarg))) {
							Class<?> conditionClass = ((Class<?>) piarg);
							searchConditions.put(conditionUrl.get(conditionClass), ((Class<?>) piarg));
							conversionService.addConverter((Converter<?, ?>) clazz.newInstance());
							logger.info("Registered Converter "+ clazz.getSimpleName()
									+ " for " + ((Class<?>) piarg).getSimpleName()
									+ " with prefix: " + conditionUrl.get(conditionClass));
						}
					}
				}
			} catch (Exception e) {
				logger.error("Could not create Converter for: "+ bean.getBeanClassName(), e);
			}
		}

		conversionService.addConverter(new Converter<String, Searchbox>() {
			public Searchbox convert(String slug) {
				return searchboxRepository.findBySlug(slug);
			}
		});

		conversionService.addConverter(new Converter<Searchbox, String>() {
			public String convert(Searchbox searchbox) {
				return searchbox.getSlug();
			}
		});

		conversionService.addConverter(new Converter<Long, Searchbox>() {
			public Searchbox convert(java.lang.Long id) {
				return searchboxRepository.findOne(id);
			}
		});

		conversionService.addConverter(new Converter<Searchbox, Long>() {
			public Long convert(Searchbox searchbox) {
				return searchbox.getId();
			}
		});

		conversionService
				.addConverter(new Converter<String, PresetDefinition>() {
					public PresetDefinition convert(String id) {
						return presetRepository.findOne(Long.parseLong(id));
					}
				});

		conversionService.addConverter(new Converter<Long, PresetDefinition>() {
			public PresetDefinition convert(java.lang.Long id) {
				return presetRepository.findOne(id);
			}
		});

		conversionService
				.addConverter(new Converter<PresetDefinition, String>() {
					public String convert(PresetDefinition presetDefinition) {
						return new StringBuilder().append(
								presetDefinition.getSlug()).toString();
					}
				});

		conversionService.addConverter(new Converter<Class<?>, String>() {
			@Override
			public String convert(Class<?> source) {
				return source.getName();
			}
		});

		conversionService.addConverter(new Converter<String, Class<?>>() {

			@Override
			public Class<?> convert(String source) {
				try {
					// TODO Such a bad hack...
					if (source.contains("class")) {
						source = source.replace("class", "").trim();
					}
					return context.getClassLoader().loadClass(source);
					// Class.forName(source);
				} catch (ClassNotFoundException e) {
					logger.error("Could not convert \"" + source
							+ "\" to class.", e);
				}
				return null;
			}

		});
	}

	public boolean isSearchConditionParam(String paramName) {
		logger.debug("checking if " + paramName
				+ " is a parameter for any SearchComponent");
		return this.searchConditions.keySet().contains(paramName);
	}

	public Set<String> getSearchConditionParams() {
		return this.searchConditions.keySet();
	}

	public Class<?> getSearchConditionClass(String param) {
		return this.searchConditions.get(param);
	}
}