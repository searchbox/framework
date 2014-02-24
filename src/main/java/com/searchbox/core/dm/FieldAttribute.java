package com.searchbox.core.dm;

import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@MappedSuperclass
public class FieldAttribute {
	
	
	protected String key = "";
	
	protected String label = "";
	
	protected Boolean searchable = false;

	protected Boolean highlight = false;
	
	protected Boolean sortable = false;
	
	protected Boolean spelling = false;
	
	protected Boolean suggestion = false;
	
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

	public Float getBoost() {
        return this.boost;
    }

	public void setBoost(Float boost) {
        this.boost = boost;
    }
}
