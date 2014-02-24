package com.searchbox.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.searchbox.anno.SearchAdapter.Time;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@SearchAdapter(execute=Time.AFTER)
public @interface PostSearchAdapter {

}
