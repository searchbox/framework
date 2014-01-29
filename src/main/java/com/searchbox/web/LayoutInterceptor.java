package com.searchbox.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LayoutInterceptor extends HandlerInterceptorAdapter {
	
	private static Logger logger = LoggerFactory.getLogger(LayoutInterceptor.class);

	private static final String DEFAULT_LAYOUT_BASE = "layouts/";
	private static final String DEFAULT_LAYOUT = "default";
	private static final String DEFAULT_VIEW_ATTRIBUTE_NAME = "view";

	private String defaultLayout = DEFAULT_LAYOUT;
	private String viewAttributeName = DEFAULT_VIEW_ATTRIBUTE_NAME;

	public void setDefaultLayout(String defaultLayout) {
		Assert.hasLength(defaultLayout);
		this.defaultLayout = defaultLayout;
	}

	public void setViewAttributeName(String viewAttributeName) {
		Assert.hasLength(defaultLayout);
		this.viewAttributeName = viewAttributeName;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		if (modelAndView==null || !modelAndView.hasView()) {
			return;
		}
		String originalViewName = modelAndView.getViewName();
		if (isRedirectOrForward(originalViewName)) {
			return;
		}
		
		logger.debug("PRE: Layout solves to: " + modelAndView.getViewName() + " with view: " +  modelAndView.getModel().get(this.viewAttributeName));
		String layoutName = getLayoutName(handler);
		modelAndView.setViewName(DEFAULT_LAYOUT_BASE+layoutName);
		//modelAndView.addObject(this.viewAttributeName, "/WEB-INF/views/"+originalViewName+".jspx");
		modelAndView.addObject(this.viewAttributeName, originalViewName);
		logger.debug("POST: Layout solves to: " + modelAndView.getViewName() + " with view: " +  modelAndView.getModel().get(this.viewAttributeName));
	}

	private boolean isRedirectOrForward(String viewName) {
		return viewName.startsWith("redirect:") || viewName.startsWith("forward:");
	}

	private String getLayoutName(Object handler) {
		try{
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Layout layout = getMethodOrTypeAnnotation(handlerMethod);
		if (layout == null) {
			return this.defaultLayout;
		} else {
			return layout.value();
		}
		} catch (Exception e){
			//e.printStackTrace();
			return this.defaultLayout;
		}
	}

	private Layout getMethodOrTypeAnnotation(HandlerMethod handlerMethod) {
		Layout layout = handlerMethod.getMethodAnnotation(Layout.class);
		if (layout == null) {
			return handlerMethod.getBeanType().getAnnotation(Layout.class);
		}
		return layout;
	}
}