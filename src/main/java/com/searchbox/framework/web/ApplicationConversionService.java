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
package com.searchbox.framework.web;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.stereotype.Service;

import com.searchbox.core.SearchCondition;
import com.searchbox.core.SearchConverter;
import com.searchbox.core.search.AbstractSearchCondition;
import com.searchbox.framework.domain.PresetDefinition;
import com.searchbox.framework.domain.Searchbox;
import com.searchbox.framework.repository.PresetRepository;
import com.searchbox.framework.repository.SearchboxRepository;

@Service("mvcConversionService")
public class ApplicationConversionService extends DefaultFormattingConversionService {
	
	@Autowired
	private ApplicationContext context;

	@Autowired
	private PresetRepository presetRepository;

	@Autowired
	private SearchboxRepository searchboxRepository;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ApplicationConversionService.class);

	private Map<String, Class<?>> searchConditions;

	public ApplicationConversionService() {
		this.searchConditions = new HashMap<String, Class<?>>();
	}

	@PostConstruct
	public void init() {

		LOGGER.info("Scanning for SearchComponents");
		Map<Class<?>, String> conditionUrl = new HashMap<Class<?>, String>();		
		ClassPathScanningCandidateComponentProvider scanner;
		

		//Getting all the SearchElements
		scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(SearchCondition.class));
		for (BeanDefinition bean:scanner.findCandidateComponents("com.searchbox")) {
			try {
				Class<?> clazz = Class.forName(bean.getBeanClassName());
				String urlParam = clazz.getAnnotation(SearchCondition.class).urlParam();
				conditionUrl.put(clazz, urlParam);				
			} catch (Exception e) {
				LOGGER.error("Could not introspect SearchElement: " + bean,e);
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
						if (AbstractSearchCondition.class.isAssignableFrom(((Class<?>) piarg))) {
							Class<?> conditionClass = ((Class<?>) piarg);
							searchConditions.put(conditionUrl.get(conditionClass), ((Class<?>) piarg));
							this.addConverter((Converter<?, ?>) clazz.newInstance());
							LOGGER.info("Registered Converter "+ clazz.getSimpleName()
									+ " for " + ((Class<?>) piarg).getSimpleName()
									+ " with prefix: " + conditionUrl.get(conditionClass));
						}
					}
				}
			} catch (Exception e) {
				LOGGER.error("Could not create Converter for: "+ bean.getBeanClassName(), e);
			}
		}

		this.addConverter(new Converter<String, Searchbox>() {
			public Searchbox convert(String slug) {
				return searchboxRepository.findBySlug(slug);
			}
		});

		this.addConverter(new Converter<Searchbox, String>() {
			public String convert(Searchbox searchbox) {
				return searchbox.getSlug();
			}
		});

		this.addConverter(new Converter<Long, Searchbox>() {
			public Searchbox convert(java.lang.Long id) {
				return searchboxRepository.findOne(id);
			}
		});

		this.addConverter(new Converter<Searchbox, Long>() {
			public Long convert(Searchbox searchbox) {
				return searchbox.getId();
			}
		});

		this.addConverter(new Converter<String, PresetDefinition>() {
					public PresetDefinition convert(String slug) {
						return presetRepository.findPresetDefinitionBySlug(slug);
					}
				});

		this.addConverter(new Converter<Long, PresetDefinition>() {
			public PresetDefinition convert(java.lang.Long id) {
				return presetRepository.findOne(id);
			}
		});

		this.addConverter(new Converter<PresetDefinition, String>() {
					public String convert(PresetDefinition presetDefinition) {
						return new StringBuilder().append(
								presetDefinition.getSlug()).toString();
					}
				});

		this.addConverter(new Converter<Class<?>, String>() {
			@Override
			public String convert(Class<?> source) {
				return source.getName();
			}
		});

		this.addConverter(new Converter<String, Class<?>>() {

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
					LOGGER.error("Could not convert \"" + source
							+ "\" to class.", e);
				}
				return null;
			}

		});
	}
	
	public boolean isSearchConditionParam(String paramName) {
		LOGGER.debug("checking if " + paramName
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
