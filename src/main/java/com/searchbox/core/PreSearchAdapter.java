package com.searchbox.core;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.searchbox.core.SearchAdapter.Time;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@SearchAdapter(execute=Time.BEFORE)
public @interface PreSearchAdapter{

}
