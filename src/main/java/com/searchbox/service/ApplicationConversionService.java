package com.searchbox.service;

import java.text.ParseException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.format.Formatter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.stereotype.Service;

import com.searchbox.anno.SearchComponent;
import com.searchbox.core.adaptor.SearchConditionAdapter;
import com.searchbox.core.adaptor.SearchElementAdapter;
import com.searchbox.core.search.SearchCondition;
import com.searchbox.domain.PresetDefinition;

@Service("conversionService")
public class ApplicationConversionService extends DefaultFormattingConversionService  { 

	@Autowired
    private ApplicationContext context;
	
	private static Logger logger = LoggerFactory.getLogger(ApplicationConversionService.class);
	
	private Map<String, Class<?>> searchComponents;
	private Map<String, Class<?>> searchConditions;
	private Map<String, Class<?>> conditionConverters;
	
	
    public ApplicationConversionService(){
        super();
        
        this.searchComponents = new HashMap<String, Class<?>>();
		this.searchConditions = new HashMap<String, Class<?>>();
		this.conditionConverters = new HashMap<String, Class<?>>();

		
    	logger.info("Scanning for SearchComponents");

		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(
				false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(SearchComponent.class));
		scanner.addIncludeFilter(new AssignableTypeFilter(SearchElementAdapter.class));
		scanner.addIncludeFilter(new AssignableTypeFilter(SearchConditionAdapter.class));
		for (BeanDefinition beanDefinition : scanner
				.findCandidateComponents("com.searchbox")) {
			try {
				logger.info("Got scanner: " + beanDefinition
						.getBeanClassName());
				Class<?> searchComponent = Class.forName(beanDefinition
						.getBeanClassName());
				
				if(searchComponent.isAnnotationPresent(SearchComponent.class)){					
					String prefix = ((SearchComponent) searchComponent
							.getAnnotation(SearchComponent.class)).prefix();
					Class<?> searchCondition = ((SearchComponent) searchComponent
							.getAnnotation(SearchComponent.class)).condition();
					Class<?> conditionConverter = ((SearchComponent) searchComponent
							.getAnnotation(SearchComponent.class)).converter();
	
					searchComponents.put(prefix, searchComponent);
					searchConditions.put(prefix, searchCondition);
					conditionConverters.put(prefix, conditionConverter);
					
					if(conditionConverter!=null && Converter.class.isAssignableFrom(conditionConverter)){
		
						logger.info("~~ Found " + prefix + ":"
								+ searchComponent.getSimpleName() + " with filter["
								+ searchCondition.getName() + "]");
		
						this.addConverter((Converter<?, ?>) conditionConverter.newInstance());
					}
				} else if(SearchElementAdapter.class.isAssignableFrom(searchComponent)){
					logger.info("~~ Found Adaptor: " + searchComponent);

				} else if(SearchConditionAdapter.class.isAssignableFrom(searchComponent)){
					logger.info("~~ Found Adaptor: " + searchComponent);
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
		
		this.addConverter(new Converter<String, PresetDefinition>() {
            public PresetDefinition convert(String id) {
                return PresetDefinition.findPresetDefinition(Long.parseLong(id));
            }
        });
		
		this.addConverter(new Converter<Long, PresetDefinition>() {
            public PresetDefinition convert(java.lang.Long id) {
                return PresetDefinition.findPresetDefinition(id);
            }
        });
		
        this.addConverter(new Converter<PresetDefinition, String>() {
            public String convert(PresetDefinition presetDefinition) {
                return new StringBuilder().append(presetDefinition.getSlug()).append(' ').append(presetDefinition.getLabel()).append(' ').append(presetDefinition.getDescription()).append(' ').append(presetDefinition.getPosition()).toString();
            }
        });
		
		this.addConverter(new Converter<Class, String>(){
			@Override
			public String convert(Class source) {
				return source.getName();
			}
		});
		
		this.addConverter(new Converter<String, Class>(){

			@Override
			public Class convert(String source) {
				try {
					//TODO Such a bad hack...
					if(source.contains("class")){
						source = source.replace("class", "").trim();
					}
					return context.getClassLoader().loadClass(source);
					//Class.forName(source);
				} catch (ClassNotFoundException e) {
					logger.error("Could not convert \""+source+"\" to class.",e);
				}
				return null;
			}

			
		});
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

	public Class<?> getSearchConditionClass(String param) {
		return this.searchConditions.get(param);
	}
	
	public Class<?> getConditionConverterClass(String param) {
		return this.conditionConverters.get(param);
	}

	public Collection<Class<?>> getConditionClasses() {
		return this.searchConditions.values(); 
	}
}