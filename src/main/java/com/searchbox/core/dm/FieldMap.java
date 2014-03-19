package com.searchbox.core.dm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class FieldMap extends HashMap<String, List<Object>> {
	  
	  /**
	   * Returns an empty list if key doesn't exist
	   * @param key String key
	   * @return List<Object>
	   */
	  public List<Object> get(String key){
		  if(super.containsKey(key)){
			  return super.get(key);
		  } else {
			  return Collections.emptyList();
		  }
	  }
	  
    /**
     * 
     */
    private static final long serialVersionUID = 6364672700946040334L;

    public void put(String key, Object number) {
      List<Object> current = get(key);
      if (current == null) {
        current = new ArrayList<Object>();
        super.put(key, current);
      }
      current.add(number);
    }
  }