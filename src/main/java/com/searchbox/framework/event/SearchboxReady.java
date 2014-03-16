package com.searchbox.framework.event;

import org.springframework.context.ApplicationEvent;

public class SearchboxReady extends ApplicationEvent {

  public SearchboxReady(Object source) {
    super(source);
  }

  /**
	 * 
	 */
  private static final long serialVersionUID = 1749059285386449000L;

}
