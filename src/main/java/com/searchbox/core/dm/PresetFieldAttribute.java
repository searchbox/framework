package com.searchbox.core.dm;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
public class PresetFieldAttribute {
	
	private Field field;
	
	private String label;
	
	private Boolean searchable;
	
	private Boolean highlight;
	
	private Boolean sortable;
	
	private Boolean spelling;
	
	private Boolean suggestion;
	
	private Float boost;

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public Field getField() {
        return this.field;
    }

	public void setField(Field field) {
        this.field = field;
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
