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
package com.searchbox.framework.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;

@Entity
public class UserRole implements GrantedAuthority{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9173416138785318191L;

	public enum Role {
		SYSTEM,
		ADMIN,
		USER
	}
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
	
	@Version
	@Column(name="OPTLOCK")
	private long version;
	
	@ManyToOne
	private Searchbox searchbox;
	
	@ManyToOne
	private User user;
	
	private Role role;
	
	public UserRole(Role role){
		this.role = role;
	}
	
	public UserRole(User user, Role role){
		this.user = user;
		this.role = role;
	}
	
	public UserRole(){
		
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

	public Searchbox getSearchbox() {
		return searchbox;
	}

	public void setSearchbox(Searchbox searchbox) {
		this.searchbox = searchbox;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String getAuthority() {
		return role.name();
	}
	
	@Override
	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }	
}
