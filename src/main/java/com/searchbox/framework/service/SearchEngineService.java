/*******************************************************************************
 * Copyright Searchbox - http://www.searchbox.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.searchbox.framework.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.searchbox.core.engine.SearchEngine;
import com.searchbox.framework.event.EngineReadyEvent;

@Service
public class SearchEngineService {
	
	private static Logger logger = LoggerFactory.getLogger(SearchEngineService.class);
	
	@Autowired
	private ApplicationEventPublisher publisher;

	private Map<String, SearchEngine<?, ?>> engines;
	
	public SearchEngineService(){
		this.engines = new HashMap<String, SearchEngine<?, ?>>();
	}
	
	public void load(final SearchEngine<?, ?> searchEngine) {
		if (engines.containsKey(searchEngine.getName())) {
			logger.error("SearchService allready contains engine: "
					+ searchEngine.getName());
		} else {
			if (!searchEngine.isLoaded()) {
				new Thread(new Runnable(){
					@Override
					public void run() {
						if(searchEngine.load()){
							logger.info("Loaded engine: " + searchEngine.getName());
							publisher.publishEvent(new EngineReadyEvent(searchEngine));
						} else {
							logger.error("Could not load engine: "+ searchEngine.getName());
						}
					}
				}).start();;
			}
			engines.put(searchEngine.getName(), searchEngine);
		}
	}
	
	public SearchEngine<?,?> getSearchEngine(String name){
		SearchEngine<?, ?> engine = this.engines.get(name);
		while(!engine.isLoaded()){
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return engine;
	}
}
