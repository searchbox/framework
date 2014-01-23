package com.searchbox.domain.search;

import java.util.HashMap;
import java.util.Map;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
public class Hit implements Comparable<Hit>{
	
	private Float score;

	private Map<String, String> fields = new HashMap<String, String>();
	
	@Override
	public int compareTo(Hit hit) {	
		return score.compareTo(hit.getScore());
	}
}
