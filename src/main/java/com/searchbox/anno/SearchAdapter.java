package com.searchbox.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Scope("singleton")
public @interface SearchAdapter {
	Time execute() default Time.BEFORE;
	public enum Time {BEFORE, AFTER}
}
