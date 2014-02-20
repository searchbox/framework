package com.searchbox.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import com.searchbox.core.dm.PresetFieldAttribute;
import com.searchbox.ref.ReflectionUtils;

@Entity
public class PresetFieldAttributeDefinition {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
	
	@Version
	@Column(name="OPTLOCK")
	private long version;
	
	@ManyToOne(targetEntity=PresetDefinition.class)
	private PresetDefinition preset;
	
	@ManyToOne
	private FieldDefinition field;
		
	private String label = "";
	
	private Boolean searchable = false;
	
	private Boolean highlight = false;
	
	private Boolean sortable = false;
	
	private Boolean spelling = false;
	
	private Boolean suggestion = false;
	
	private Float boost = 1.0f;
 
	public PresetFieldAttributeDefinition(){
		
	}
	
	public PresetFieldAttributeDefinition(FieldDefinition field){
		this.field = field;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public PresetDefinition getPreset() {
		return preset;
	}

	public void setPreset(PresetDefinition preset) {
		this.preset = preset;
	}

	public FieldDefinition getField() {
		return field;
	}

	public void setField(FieldDefinition field) {
		this.field = field;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Boolean getSearchable() {
		return searchable;
	}

	public void setSearchable(Boolean searchable) {
		this.searchable = searchable;
	}

	public Boolean getHighlight() {
		return highlight;
	}

	public void setHighlight(Boolean highlight) {
		this.highlight = highlight;
	}

	public Boolean getSortable() {
		return sortable;
	}

	public void setSortable(Boolean sortable) {
		this.sortable = sortable;
	}

	public Boolean getSpelling() {
		return spelling;
	}

	public void setSpelling(Boolean spelling) {
		this.spelling = spelling;
	}

	public Boolean getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(Boolean suggestion) {
		this.suggestion = suggestion;
	}

	public Float getBoost() {
		return boost;
	}

	public void setBoost(Float boost) {
		this.boost = boost;
	}

	public PresetFieldAttribute toPresetFieldAttribute() {
			PresetFieldAttribute element = new PresetFieldAttribute();
			ReflectionUtils.copyAllFields(this, element);
			element.setField(this.field.toField());
			return element;
	}
}
