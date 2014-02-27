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
package com.searchbox.core.search.stat;

import org.apache.solr.client.solrj.response.QueryResponse;

import com.searchbox.core.PostSearchAdapter;
import com.searchbox.core.SearchAdapter;
import com.searchbox.core.SearchAttribute;
import com.searchbox.core.SearchComponent;
import com.searchbox.core.search.SearchElement;

@SearchComponent
public class BasicSearchStats extends SearchElement {

	private Long hitCount = 0l;
	private Long searchTime = 0l;
	
	@SearchAttribute
	private Boolean viewTime = true;
	
	@SearchAttribute
	private Boolean viewCount = true;
	
	public Boolean getViewTime() {
		return viewTime;
	}

	public void setViewTime(Boolean viewTime) {
		this.viewTime = viewTime;
	}

	public Boolean getViewCount() {
		return viewCount;
	}

	public void setViewCount(Boolean viewCount) {
		this.viewCount = viewCount;
	}

	public long getHitCount() {
		return hitCount;
	}

	public void setHitCount(long hitCount) {
		this.hitCount = hitCount;
	}

	public long getSearchTime() {
		return searchTime;
	}

	public void setSearchTime(long searchTime) {
		this.searchTime = searchTime;
	}
	
	public BasicSearchStats(){
		super(null,SearchElement.Type.STAT);
	}

	public BasicSearchStats(String label) {
		super(label,SearchElement.Type.STAT);
	}
}

@SearchAdapter
class BasicSearchStatsAdapter {

	@PostSearchAdapter
	public BasicSearchStats getBasicStats(BasicSearchStats searchElement,
			QueryResponse response) {
		if(response.getResults() != null){
			searchElement.setHitCount(response.getResults().getNumFound());
		}
		searchElement.setSearchTime(response.getElapsedTime());
		return searchElement;
	}
}
