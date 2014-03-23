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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hit implements Comparable<Hit> {

  public Map<String, Object> fieldValues;
  public Map<String, List<String>> highlights;

  private Float score;

  private String idFieldName;

  private String titleFieldName;

  private String urlFieldName;

  private String displayTemplate;

  // Prevent building a Hit with no score.
  private Hit() {
  }

  public Hit(Float score) {
    this.score = score;
    this.fieldValues = new HashMap<String, Object>();
    this.highlights = new HashMap<String, List<String>>();
  }

  public String getIdFieldName() {
    return idFieldName;
  }

  public void setIdFieldName(String idFieldName) {
    this.idFieldName = idFieldName;
  }

  public String getTitleFieldName() {
    return titleFieldName;
  }

  public void setTitleFieldName(String titleFieldName) {
    this.titleFieldName = titleFieldName;
  }

  public String getUrlFieldName() {
    return urlFieldName;
  }

  public void setUrlFieldName(String urlFieldName) {
    this.urlFieldName = urlFieldName;
  }

  public String getDisplayTemplate() {
    return displayTemplate;
  }

  public void setDisplayTemplate(String displayTemplate) {
    this.displayTemplate = displayTemplate;
  }

  public Float getScore() {
    return score;
  }

  public void setFieldValues(Map<String, Object> fieldValues) {
    this.fieldValues = fieldValues;
  }

  public void setHighlights(Map<String, List<String>> highlights) {
    this.highlights = highlights;
  }

  public void addFieldValue(String name, Object value) {
    this.fieldValues.put(name, value);
  }

  @SuppressWarnings("unchecked")
  public String getId() {
    Object id = this.fieldValues.get(this.idFieldName);
    if (List.class.isAssignableFrom(id.getClass())) {
      return ((List<String>) id).get(0);
    } else {
      return (String) id;
    }
  }

  @SuppressWarnings("unchecked")
  public String getTitle() {
    Object title = this.fieldValues.get(this.titleFieldName);
    if (List.class.isAssignableFrom(title.getClass())) {
      return ((List<String>) title).get(0);
    } else {
      return (String) title;
    }
  }

  @SuppressWarnings("unchecked")
  public String getUrl() {
    Object url = this.fieldValues.get(this.urlFieldName);
    if (List.class.isAssignableFrom(url.getClass())) {
      return ((List<String>) url).get(0);
    } else {
      return (String) url;
    }
  }

  public Map<String, Object> getFieldValues() {
    return this.fieldValues;
  }

  /**
   * @return the highlights
   */
  public Map<String, List<String>> getHighlights() {
    return highlights;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((idFieldName == null) ? 0 : idFieldName.hashCode());
    result = prime * result + ((score == null) ? 0 : score.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Hit other = (Hit) obj;
    if (idFieldName == null) {
      if (other.idFieldName != null)
        return false;
    } else if (!idFieldName.equals(other.idFieldName))
      return false;
    if (score == null) {
      if (other.score != null)
        return false;
    } else if (!score.equals(other.score))
      return false;
    return true;
  }

  @Override
  public int compareTo(Hit other) {
    return score.compareTo(other.getScore() + 0.001f) * -1;
  }
}
