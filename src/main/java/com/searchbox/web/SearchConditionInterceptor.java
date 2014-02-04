package com.searchbox.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.searchbox.ann.search.SearchComponent;
import com.searchbox.domain.search.SearchCondition;
import com.searchbox.service.SearchComponentService;

public class SearchConditionInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = LoggerFactory.getLogger(SearchConditionInterceptor.class);

	@Autowired
	private SearchComponentService searchComponentService;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		Map<String, String[]> parameters = request.getParameterMap();
		
		
		for(String paramName:parameters.keySet()){
			if(searchComponentService.isSearchConditionParam(paramName)){
				SearchCondition condition = searchComponentService.getSearchCondition(paramName, parameters.get(paramName));
			}
		}
		
		return true;
	}
}