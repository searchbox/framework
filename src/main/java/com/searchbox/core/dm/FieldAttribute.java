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
package com.searchbox.core.dm;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.searchbox.core.SearchAttribute;

public class FieldAttribute {
	
	@SearchAttribute
	protected String key = "";
	
	@SearchAttribute
	protected String label = "";
	
	@SearchAttribute
	protected Boolean searchable = false;

	@SearchAttribute
	protected Boolean highlight = false;
	
	@SearchAttribute
	protected Boolean sortable = false;
	
	@SearchAttribute
	protected Boolean spelling = false;
	
	@SearchAttribute
	protected Boolean suggestion = false;
	
	@SearchAttribute
	protected Boolean id = false;
	
	@SearchAttribute
	protected Float boost = 1f;

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Boolean getSearchable() {
        return this.searchable;
    }

	public void setSearchable(Boolean searchable) {
        this.searchable = searchable;
    }

	public Boolean getHighlight() {
        return this.highlight;
    }

	public void setHighlight(Boolean highlight) {
        this.highlight = highlight;
    }

	public Boolean getSortable() {
        return this.sortable;
    }

	public void setSortable(Boolean sortable) {
        this.sortable = sortable;
    }

	public Boolean getSpelling() {
        return this.spelling;
    }

	public void setSpelling(Boolean spelling) {
        this.spelling = spelling;
    }

	public Boolean getSuggestion() {
        return this.suggestion;
    }

	public void setSuggestion(Boolean suggestion) {
        this.suggestion = suggestion;
    }

	public Boolean getId() {
		return id;
	}

	public void setId(Boolean isId) {
		this.id = isId;
	}

	public Float getBoost() {
        return this.boost;
    }

	public void setBoost(Float boost) {
        this.boost = boost;
    }
}
