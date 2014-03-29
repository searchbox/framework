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

import com.searchbox.core.SearchAdapter;
import com.searchbox.core.SearchAdapter.Time;
import com.searchbox.core.SearchAdapterMethod;
import com.searchbox.core.SearchAttribute;
import com.searchbox.core.SearchComponent;
import com.searchbox.core.SearchElement;
import com.searchbox.core.SearchElementBean;
import com.searchbox.core.response.Response;

@SearchComponent
public class BasicSearchStats extends SearchElementBean {

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

  public BasicSearchStats() {
    this("basic stats");
  }

  public BasicSearchStats(String label) {
    this.setLabel(label);
    this.setType(SearchElement.Type.STAT);
  }
}

@SearchAdapter
class BasicSearchStatsAdapter {

  @SearchAdapterMethod(execute = Time.POST)
  public BasicSearchStats getBasicStats(BasicSearchStats searchElement,
      Response response) {
    
    searchElement.setHitCount(response.getNumFound());
    searchElement.setSearchTime(response.getTotalTime());
    return searchElement;
  }
}
