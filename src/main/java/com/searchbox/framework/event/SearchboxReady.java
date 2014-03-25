package com.searchbox.framework.event;

import org.springframework.context.ApplicationEvent;

public class SearchboxReady extends ApplicationEvent {

  public SearchboxReady(Object source) {
    super(source);
  }
}
