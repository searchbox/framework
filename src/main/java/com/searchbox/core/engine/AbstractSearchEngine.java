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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;

import com.searchbox.core.dm.Collection;
import com.searchbox.core.search.AbstractSearchCondition;
import com.searchbox.core.search.SearchElement;

@Configurable
public abstract class AbstractSearchEngine<Q, R> implements SearchEngine<Q, R> {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(AbstractSearchEngine.class);

  protected String name;

  protected String description;

  protected Class<Q> queryClass;
  protected Class<R> responseClass;

  protected AbstractSearchEngine(Class<Q> queryClass, Class<R> responseClass) {
    this.queryClass = queryClass;
    this.responseClass = responseClass;
  }

  protected AbstractSearchEngine(String name, Class<Q> queryClass,
      Class<R> responseClass) {
    this.name = name;
    this.queryClass = queryClass;
    this.responseClass = responseClass;
  }

  @Override
  public Class<Q> getQueryClass() {
    return this.queryClass;
  }

  @Override
  public Class<R> getResponseClass() {
    return this.responseClass;
  }

  @Override
  public abstract Q newQuery(Collection collection);

  @Override
  public List<SearchElement> getSupportedElements() {
    return null;
  }

  @Override
  public Boolean supportsElement(SearchElement element) {
    // FIXME check if searchegine can actually use Element
    return true;
  }

  @Override
  public Boolean supportsCondition(AbstractSearchCondition condition) {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setQueryClass(Class<Q> queryClass) {
    this.queryClass = queryClass;
  }

  protected void setResponseClass(Class<R> responseClass) {
    this.responseClass = responseClass;
  }
}
