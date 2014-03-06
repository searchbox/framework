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
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.searchbox.collection.SynchronizedCollection;
import com.searchbox.core.dm.Collection;
import com.searchbox.core.engine.ManagedSearchEngine;
import com.searchbox.core.engine.SearchEngine;
import com.searchbox.framework.domain.CollectionDefinition;
import com.searchbox.framework.domain.FieldAttributeDefinition;
import com.searchbox.framework.domain.PresetDefinition;
import com.searchbox.framework.event.SearchboxReady;
import com.searchbox.framework.repository.CollectionRepository;

@Service
public class CollectionService implements ApplicationListener<SearchboxReady> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CollectionService.class);

	@Autowired
	CollectionRepository repository;
	

	
	public Map<String, String> synchronizeData(CollectionDefinition collectiondef){
		Map<String, String> result = new HashMap<String, String>();
		com.searchbox.core.dm.Collection collection = collectiondef.getInstance();
		if(SynchronizedCollection.class.isAssignableFrom(collection.getClass())){
			try {
				((SynchronizedCollection)collection).synchronize();
			} catch (Exception e){
				result.put("status","KO");
				result.put("message",e.getMessage());
				return result;
			}
			result.put("status","OK");
			result.put("message","Synchronization started");
		} else {
			result.put("status","KO");
			result.put("message","Collection is not synchronizable");
		}
		return result;
	}
	
	public Map<String, String> synchronizeDm(CollectionDefinition collectiondef) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onApplicationEvent(SearchboxReady event) {
		
		LOGGER.info("Searchbox is ready. Loading autoStart collections");
		
		
		List<CollectionDefinition> collectionDefs = repository.findAllByAutoStart(true);
		
		for(CollectionDefinition collectionDef:collectionDefs){
			Collection collection = collectionDef.getInstance();
			SearchEngine<?,?> engine = collection.getSearchEngine();
			if(ManagedSearchEngine.class.isAssignableFrom(engine.getClass())){
				LOGGER.info("Starting DM synchronization for \"" + collection.getName()+"\"");
				for(PresetDefinition presetDef:collectionDef.getPresets()){
					for(FieldAttributeDefinition fieldAttr:presetDef.getFieldAttributes()){
						((ManagedSearchEngine)engine).updateForField(fieldAttr.getInstance());
					}
				}	
			}
			
			
			if(SynchronizedCollection.class.isAssignableFrom(collection.getClass())){
				LOGGER.info("Starting DATA synchronization for \"" + collection.getName()+"\"");
				try {
					((SynchronizedCollection)collection).synchronize();
				} catch (Exception e) {
					LOGGER.error("Could not synchronize collection: " + collection.getName(),e);
				}
			}
		}		
	}
}
