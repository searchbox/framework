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

public class Preset implements Comparable<Preset> {

	/**
     */
	private String slug;

	/**
     */
	private String label;

	/**
     */
	private String description;

	/**
     */
	private Boolean global;

	/**
     */
	private Boolean visible;

	/**
     */
	private Integer position;
	
	public Preset(){
		
	}
	
	public Preset(String slug, String label){
		this.slug = slug;
		this.label = label;
	}

	public String getSlug() {
        return this.slug;
    }

	public void setSlug(String slug) {
        this.slug = slug;
    }

	public String getLabel() {
        return this.label;
    }

	public void setLabel(String label) {
        this.label = label;
    }

	public String getDescription() {
        return this.description;
    }

	public void setDescription(String description) {
        this.description = description;
    }

	public Boolean getGlobal() {
        return this.global;
    }

	public void setGlobal(Boolean global) {
        this.global = global;
    }

	public Boolean getVisible() {
        return this.visible;
    }

	public void setVisible(Boolean visible) {
        this.visible = visible;
    }

	public Integer getPosition() {
        return this.position;
    }

	public void setPosition(Integer position) {
        this.position = position;
    }

	@Override
	public int compareTo(Preset o) {
		return o.getPosition().compareTo((this.getPosition()));
	}
	
	@Override
	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
