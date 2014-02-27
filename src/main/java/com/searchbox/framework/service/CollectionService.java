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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.searchbox.collection.PubmedCollection;
import com.searchbox.core.engine.SearchEngine;
import com.searchbox.framework.event.EngineReadyEvent;

@Service
public class CollectionService implements ApplicationListener<EngineReadyEvent> {
	
	private static Logger logger = LoggerFactory.getLogger(CollectionService.class);

	@Autowired
	AutowireCapableBeanFactory factory;
	
	@Override
	public void onApplicationEvent(EngineReadyEvent event) {
		// TODO here we have to get the collection of the engine
		// and update their fields :)
		
		SearchEngine<?, ?> engine = (SearchEngine<?, ?>)event.getSource();
		logger.info("SearchEngine " + engine.getName() + " is ready for some action!!!");
		
		
		PubmedCollection pubmecCollection = factory.createBean(PubmedCollection.class);
		pubmecCollection.importCollection();
		
	}
}
