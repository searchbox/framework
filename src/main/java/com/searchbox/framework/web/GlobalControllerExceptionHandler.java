//package com.searchbox.framework.web;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.annotation.AnnotationUtils;
//import org.springframework.social.ResourceNotFoundException;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.servlet.ModelAndView;
//
//@ControllerAdvice
//@Component
//class GlobalDefaultExceptionHandler {
//    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);
//    public static final String DEFAULT_ERROR_VIEW = "error/error";
//
//    
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public String handleResourceNotFoundException() {
//        return "error/404";
//    }
//    
//    
//    @ExceptionHandler(value = Exception.class)
//    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
//    	LOGGER.debug("entering defaultErrorHandler");
//        // If the exception is annotated with @ResponseStatus rethrow it and let
//        // the framework handle it - like the OrderNotFoundException example
//        // at the start of this post.
//        // AnnotationUtils is a Spring Framework utility class.
//        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
//            throw e;
//
//        // Otherwise setup and send the user to a default error-view.
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("exception", e);
//        mav.addObject("url", req.getRequestURL());
//        mav.setViewName("error/404");
//        return mav;
//    }
//}