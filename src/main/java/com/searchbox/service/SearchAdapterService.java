package com.searchbox.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.searchbox.anno.SearchAdaptor;
import com.searchbox.core.adaptor.SearchConditionAdapter;
import com.searchbox.core.adaptor.SearchElementAdapter;
import com.searchbox.core.search.SearchCondition;
import com.searchbox.core.search.SearchElement;

@Service
public class SearchAdapterService implements ApplicationListener<ContextRefreshedEvent> {

	private static Logger logger = LoggerFactory.getLogger(SearchAdapterService.class);

	@Autowired
	ApplicationContext context;
	
	//TODO for now only discovering with SolrQuery
		private Map<Class<?>, SearchElementAdapter<?,SolrQuery, SolrResponse>> elementAdapters;
		private Map<Class<?>, SearchConditionAdapter<?,SolrQuery>> conditionAdapters;	

	public SearchAdapterService(){
		
	}
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		this.elementAdapters = new HashMap<Class<?>, SearchElementAdapter<?,SolrQuery, SolrResponse>>();
		this.conditionAdapters = new HashMap<Class<?>, SearchConditionAdapter<?,SolrQuery>>();

		for(Entry<String, Object> bean:context.getBeansWithAnnotation(SearchAdaptor.class).entrySet()){
			for(Type type:bean.getValue().getClass().getGenericInterfaces()){
				ParameterizedType pBaseType = (ParameterizedType)type;
				Class<?> parent = (Class<?>)pBaseType.getActualTypeArguments()[0];
				//TODO integrate SearchQuery type which is [1]
				if(SearchElement.class.isAssignableFrom(parent)){
					logger.info("Adding Element Adapter for " + parent.getSimpleName());
					this.elementAdapters.put(parent, (SearchElementAdapter<?, SolrQuery, SolrResponse>) bean.getValue());
				} else  if(SearchCondition.class.isAssignableFrom(parent)){
					logger.info("Adding Condition Adapter for " + parent.getSimpleName());
					this.conditionAdapters.put(parent, (SearchConditionAdapter<?, SolrQuery>) bean.getValue());
				}
			}
		}
	}
	
	public SearchElementAdapter getAdapter(SearchElement element){
		return this.elementAdapters.get(element.getClass());
	}
	
	public SearchConditionAdapter getAdapter(SearchCondition condition){
//		logger.info("Fetching adaptor for: " + condition.getClass());
//		logger.info("Adaptor exists: " + condition.getClass());
		
		return this.conditionAdapters.get(condition.getClass());
	}
	
}
