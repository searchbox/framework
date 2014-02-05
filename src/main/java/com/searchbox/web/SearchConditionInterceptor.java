package com.searchbox.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

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
		
		List<SearchCondition> conditions = new ArrayList<SearchCondition>();
		for(String paramName:parameters.keySet()){
			if(searchComponentService.isSearchConditionParam(paramName)){
//				List<SearchCondition> currentConditions = searchComponentService.getSearchCondition(paramName, parameters.get(paramName));
//				conditions.addAll(conditions);
//				request.removeAttribute(paramName);
			}
		}
		//TODO extract literal
		request.setAttribute("conditions", conditions);
		
		return true;
	}
}