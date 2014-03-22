package com.searchbox.core;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SearchCollector {

  private Map<String, PriorityQueue<Comparable<?>>> items;

  public SearchCollector() {
    items = new HashMap<>();
  }

  public PriorityQueue<Comparable<?>> getCollectedItems(String key) {
    if (!this.items.containsKey(key)) {
      this.items.put(key, new PriorityQueue<Comparable<?>>());
    }
    return this.items.get(key);
  }

  public Map<String, PriorityQueue<Comparable<?>>> getItems() {
    return items;
  }

  public void setItems(Map<String, PriorityQueue<Comparable<?>>> items) {
    this.items = items;
  }

  @Override
  public String toString() {
    // FIXME. Do not reflection on toString (breaks Lazy INiti)
    return ReflectionToStringBuilder.toString(this,
        ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
