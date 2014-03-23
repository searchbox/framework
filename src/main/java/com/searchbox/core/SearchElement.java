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
package com.searchbox.core;

public interface SearchElement extends Comparable<SearchElement> {

  public final static String URL_PARAM = "xoxo";

  public enum Type {
    QUERY, FACET, FILTER, VIEW, ANALYTIC, SORT, STAT, DEBUG, UNKNOWN, INSPECT
  }

  public String getLabel();
  
  public void setLabel(String label);

  public Integer getPosition();
  
  public void setPosition(Integer position);

  public Type getType();
  
  public void setType(Type type);

  public int compareTo(SearchElement searchElement);

}
