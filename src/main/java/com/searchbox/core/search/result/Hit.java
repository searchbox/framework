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

import com.searchbox.core.search.ValueElement;

public class Hit extends ValueElement  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6419221783189375788L;
	
	public Map<String, Object> fieldValues;
	
	private Float score;
	
	private String IDFieldName;
	
	private String TitleFieldName;
	
	private String URLFieldName;
	
	public Hit(Float score){
		super("");
		this.score = score;
		this.fieldValues = new HashMap<String, Object>();
	}
	
	public String getIDFieldName() {
		return IDFieldName;
	}

	public void setIDFieldName(String iDFieldName) {
		IDFieldName = iDFieldName;
	}

	public String getTitleFieldName() {
		return TitleFieldName;
	}

	public void setTitleFieldName(String titleFieldName) {
		TitleFieldName = titleFieldName;
	}

	public String getURLFieldName() {
		return URLFieldName;
	}

	public void setURLFieldName(String uRLFieldName) {
		URLFieldName = uRLFieldName;
	}

	public Float getScore(){
		return this.score;
	}

	public void setScore(Float score) {
		this.score = score;
	}
	
	public void addFieldValue(String name, Object value){
		this.fieldValues.put(name, value);
	}
	
	@SuppressWarnings("unchecked")
	public String getId(){
		Object id = this.fieldValues.get(this.IDFieldName);
		if(List.class.isAssignableFrom(id.getClass())){
			return ((List<String>)id).get(0);
		} else {
			return (String)id;
		}
	}
	
	@SuppressWarnings("unchecked")
	public String getTitle(){
		Object title = this.fieldValues.get(this.TitleFieldName);
		if(List.class.isAssignableFrom(title.getClass())){
			return ((List<String>)title).get(0);
		} else {
			return (String)title;
		}
	}
	
	@SuppressWarnings("unchecked")
	public String getUrl(){
		Object url = this.fieldValues.get(this.URLFieldName);
		if(List.class.isAssignableFrom(url.getClass())){
			return ((List<String>)url).get(0);
		} else {
			return (String)url;
		}
	}
	
	public Map<String, Object> getFieldValues(){
		return this.fieldValues;
	}

	@Override
	public int compareTo(ValueElement other) {
		return score.compareTo(((Hit)other).getScore()+0.001f) * -1;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((IDFieldName == null) ? 0 : IDFieldName.hashCode());
		result = prime * result
				+ ((TitleFieldName == null) ? 0 : TitleFieldName.hashCode());
		result = prime * result
				+ ((URLFieldName == null) ? 0 : URLFieldName.hashCode());
		result = prime * result
				+ ((fieldValues == null) ? 0 : fieldValues.hashCode());
		result = prime * result + ((score == null) ? 0 : score.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Hit))
			return false;
		Hit other = (Hit) obj;
		if (IDFieldName == null) {
			if (other.IDFieldName != null)
				return false;
		} else if (!IDFieldName.equals(other.IDFieldName))
			return false;
		if (TitleFieldName == null) {
			if (other.TitleFieldName != null)
				return false;
		} else if (!TitleFieldName.equals(other.TitleFieldName))
			return false;
		if (URLFieldName == null) {
			if (other.URLFieldName != null)
				return false;
		} else if (!URLFieldName.equals(other.URLFieldName))
			return false;
		if (fieldValues == null) {
			if (other.fieldValues != null)
				return false;
		} else if (!fieldValues.equals(other.fieldValues))
			return false;
		if (score == null) {
			if (other.score != null)
				return false;
		} else if (!score.equals(other.score))
			return false;
		return true;
	}
}
