package com.searchbox.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SearchComponent {
	
	String prefix() default "";
	
	Class<?> condition() default Object.class;
	
	Class<?> converter() default Object.class;
}