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
package com.searchbox.core.search.result;

import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchbox.core.SearchAttribute;
import com.searchbox.core.SearchComponent;
import com.searchbox.core.search.SearchElement;
import com.searchbox.core.search.UseCollector;

@SearchComponent
public class TemplateElement extends SearchElement implements UseCollector {

  private static final Logger LOGGER = LoggerFactory.getLogger(TemplateElement.class);
  
  private static final String COLLECTOR_KEY = "hits";
  private static final String DEFAUTL_TEMPLATE = "/WEB-INF/templates/_defaultHitView.jspx";

  @SearchAttribute
  protected Set<String> fields;

  @SearchAttribute
  private String templateFile;

  @SearchAttribute
  String titleField;

  @SearchAttribute
  String urlField;

  @SearchAttribute
  String idField;

  public TemplateElement() {
    super("Result Set with Template", SearchElement.Type.VIEW);
    this.fields = new TreeSet<String>();
  }
  
  public boolean hasTempalte(){
    return this.templateFile != null && !this.templateFile.isEmpty();
  }

  public Set<String> getRequiredFields() {
    Set<String> fields = this.getFields();
    if (idField != null && !idField.isEmpty()) {
      fields.add(idField);
    }
    if (titleField != null && !titleField.isEmpty()) {
      fields.add(titleField);
    }
    if (urlField != null && !urlField.isEmpty()) {
      fields.add(urlField);
    }
    return fields;
  }
  
  public void setTemplateFile(String templateFile) {
    this.templateFile = templateFile;
  }

  public String getTemplateFile() {
    if(hasTempalte()){
      return this.templateFile;
    } else {
      return TemplateElement.DEFAUTL_TEMPLATE;
    }
  }

  public String getTitleField() {
    return titleField;
  }

  public void setTitleField(String titleField) {
    this.titleField = titleField;
  }

  public String getUrlField() {
    return urlField;
  }

  public void setUrlField(String urlField) {
    this.urlField = urlField;
  }

  public String getIdField() {
    return idField;
  }

  public void setIdField(String idField) {
    this.idField = idField;
  }

  public void setFields(Set<String> fields) {
    this.fields = fields;
  }

  public Set<String> getFields() {
    return this.fields;
  }

  @Override
  public String getCollectorKey() {
    if(this.getLabel().isEmpty()){
      return TemplateElement.COLLECTOR_KEY;
    } else {
      return this.getLabel();
    }
  }
}
