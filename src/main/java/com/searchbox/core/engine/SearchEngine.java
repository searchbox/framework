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
package com.searchbox.core.engine;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.searchbox.core.dm.Collection;
import com.searchbox.core.search.AbstractSearchCondition;
import com.searchbox.core.search.SearchElement;

public interface SearchEngine<Q, R> {

  String getName();

  String getDescription();

  Class<Q> getQueryClass();

  Class<R> getResponseClass();

  Q newQuery(Collection collection);

  R execute(Collection collection, Q query);

  boolean indexFile(Collection collection, File file);

  boolean indexMap(Collection collection, Map<String, Object> fields);

  List<SearchElement> getSupportedElements();

  Boolean supportsElement(SearchElement element);

  Boolean supportsCondition(AbstractSearchCondition condition);

}
