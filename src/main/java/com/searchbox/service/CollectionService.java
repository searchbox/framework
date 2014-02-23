package com.searchbox.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.searchbox.core.EngineReadyEvent;
import com.searchbox.core.engine.SearchEngine;

@Service
public class CollectionService implements ApplicationListener<EngineReadyEvent> {
	
	private static Logger logger = LoggerFactory.getLogger(CollectionService.class);

	@Override
	public void onApplicationEvent(EngineReadyEvent event) {
		// TODO here we have to get the collection of the engine
		// and update their fields :)
		
		SearchEngine engine = (SearchEngine)event.getSource();
		logger.info("SearchEngine " + engine.getName() + " is ready for some action!!!");
		
	}
}
