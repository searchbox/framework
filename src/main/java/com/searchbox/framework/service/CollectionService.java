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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.searchbox.collection.SynchronizedCollection;
import com.searchbox.core.dm.Collection;
import com.searchbox.framework.domain.CollectionDefinition;
import com.searchbox.framework.event.SearchboxReady;
import com.searchbox.framework.repository.CollectionRepository;

@Service
public class CollectionService implements ApplicationListener<SearchboxReady> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CollectionService.class);

	@Autowired
	CollectionRepository repository;
	
	public List<Collection> findAutoStartCollection(){
		List<Collection> collections = new ArrayList<Collection>();
		for(CollectionDefinition collDef:repository.findAllByAutoStart(true)){
			collections.add(collDef.getInstance());
		}
		return collections;
	}
	
	@Override
	public void onApplicationEvent(SearchboxReady event) {
		
		LOGGER.info("Searchbox is ready. Loading autoStart collections");
		
		for(Collection collection:findAutoStartCollection()){
			if(SynchronizedCollection.class.isAssignableFrom(collection.getClass())){
				LOGGER.info("Starting synchronization for \"" + collection.getName()+"\"");
				try {
					((SynchronizedCollection)collection).synchronize();
				} catch (Exception e) {
					LOGGER.error("Could not synchronize collection: " + collection.getName(),e);
				}
			}
		}		
	}
}
