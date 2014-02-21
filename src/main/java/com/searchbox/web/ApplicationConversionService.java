package com.searchbox.web;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.stereotype.Service;

import com.searchbox.anno.SearchComponent;
import com.searchbox.app.domain.PresetDefinition;
import com.searchbox.app.domain.Searchbox;
import com.searchbox.app.repository.PresetDefinitionRepository;
import com.searchbox.app.repository.SearchboxRepository;
import com.searchbox.core.adaptor.SearchConditionAdapter;
import com.searchbox.core.adaptor.SearchElementAdapter;
import com.searchbox.core.search.SearchCondition;

@Service("conversionService")
@Configurable
public class ApplicationConversionService  { 

	@Autowired
    private ApplicationContext context;
	
	@Autowired
	private DefaultFormattingConversionService conversionService;
	
	@Autowired
	private PresetDefinitionRepository presetRepository;
	
	@Autowired
	private SearchboxRepository searchboxRepository;
	
	private static Logger logger = LoggerFactory.getLogger(ApplicationConversionService.class);
	
	private Map<String, Class<?>> searchComponents;
	private Map<String, Class<?>> searchConditions;
	private Map<String, Class<?>> conditionConverters;
	
	
    public ApplicationConversionService(){        
        this.searchComponents = new HashMap<String, Class<?>>();
		this.searchConditions = new HashMap<String, Class<?>>();
		this.conditionConverters = new HashMap<String, Class<?>>();
    }
    
    @PostConstruct
    public void init(){
		
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
		
						conversionService.addConverter((Converter<?, ?>) conditionConverter.newInstance());
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
		
		conversionService.addConverter(new Converter<String, PresetDefinition>() {
            public PresetDefinition convert(String id) {
                return presetRepository.findOne(Long.parseLong(id));
            }
        });
		
		conversionService.addConverter(new Converter<Long, PresetDefinition>() {
            public PresetDefinition convert(java.lang.Long id) {
                return presetRepository.findOne(id);
            }
        });
		
		conversionService.addConverter(new Converter<PresetDefinition, String>() {
            public String convert(PresetDefinition presetDefinition) {
                return new StringBuilder().append(presetDefinition.getSlug()).append(' ').append(presetDefinition.getLabel()).append(' ').append(presetDefinition.getDescription()).append(' ').append(presetDefinition.getPosition()).toString();
            }
        });
		
		conversionService.addConverter(new Converter<Class<?>, String>(){
			@Override
			public String convert(Class<?> source) {
				return source.getName();
			}
		});
		
		conversionService.addConverter(new Converter<String, Class<?>>(){

			@Override
			public Class<?> convert(String source) {
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