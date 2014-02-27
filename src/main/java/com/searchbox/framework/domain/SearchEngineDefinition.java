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
package com.searchbox.framework.domain;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.searchbox.core.engine.AbstractSearchEngine;
import com.searchbox.core.engine.SearchEngine;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class SearchEngineDefinition extends UnknownClassDefinition 
	implements ElementFactory<SearchEngine<?,?>>{
	
	protected String name;
		
	public SearchEngineDefinition(){
		super();
	}
	public SearchEngineDefinition(Class<?> searchEngineClass, String name){
		super(searchEngineClass);
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public SearchEngine<?, ?> getInstance() {
		AbstractSearchEngine<?, ?> engine = (AbstractSearchEngine<?, ?>) super.toObject();
		engine.setName(this.getName());
		return engine;
	}
}
