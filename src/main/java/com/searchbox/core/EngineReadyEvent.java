package com.searchbox.core;

import org.springframework.context.ApplicationEvent;

public class EngineReadyEvent extends ApplicationEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public EngineReadyEvent(Object source) {
		super(source);
	}
}
