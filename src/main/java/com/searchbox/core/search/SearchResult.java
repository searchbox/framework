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
package com.searchbox.core.search;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SearchResult {

	public SearchResult(){
		
	}
	
	List<String> fields = new ArrayList<String>();
	SortedSet<SearchElement> elements = new TreeSet<SearchElement>();

	public void addElement(SearchElement element) {
		this.elements.add(element);
	}
	
	public SortedSet<SearchElement> getElements(SearchElement.Type type){
		TreeSet<SearchElement> typedElements = new TreeSet<SearchElement>();
		for(SearchElement element:this.elements){
			if(element.type.equals(type)){
				typedElements.add(element);
			}
		}
		return typedElements;
	}

	public SortedSet<SearchElement> getElements() {
		return this.elements;
	}
	
	@Override
	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
