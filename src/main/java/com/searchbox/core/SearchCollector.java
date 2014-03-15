package com.searchbox.core;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class SearchCollector {
  
  private Map<String, SortedSet<Comparable>> items;
  
  public SearchCollector(){
    items = new HashMap<String, SortedSet<Comparable>>();
  }
  
  public SortedSet<Comparable> getCollectedItems(String key){
    if(!this.items.containsKey(key)){
      this.items.put(key, new TreeSet<Comparable>());
    }
    return this.items.get(key);
  }

  public Map<String, SortedSet<Comparable>> getItems() {
    return items;
  }

  public void setItems(Map<String, SortedSet<Comparable>> items) {
    this.items = items;
  }
}
