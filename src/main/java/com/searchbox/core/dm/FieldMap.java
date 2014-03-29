package com.searchbox.core.dm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class FieldMap extends HashMap<String, List<Object>> {

  /**
   * Returns an empty list if key doesn't exist
   * 
   * @param key
   *          String key
   * @return List<Object>
   */
  public List<Object> get(String key) {
    if (super.containsKey(key)) {
      return super.get(key);
    } else {
      return Collections.emptyList();
    }
  }
  
  public Class<?> getClazz(String key){
    if(super.containsKey(key)){
      Object obj = super.get(key).get(0);
      if(obj == null){
        return null;
      } else {
        return super.get(key).get(0).getClass();        
      }
    } else {
      return null;
    }
  }

  /**
   * Add item of any type to the field defined by key
   * 
   * @param key
   *          String
   * @param item
   *          Object
   */
  public void put(String key, Object item) {
    if (!super.containsKey(key)) {
      super.put(key, new ArrayList<Object>());
    }
    super.get(key).add(item);
  }
}