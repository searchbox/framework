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

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import com.searchbox.framework.user.model.SocialMediaService;


@Entity
public class User {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2384552120318639487L;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
	
	@Version
	@Column(name="OPTLOCK")
	private long version;
	
	@Column(nullable=false, unique=true)
	private String email;
	
	private String password;
	
	private SocialMediaService SignInProvider;
	
	@OneToMany(cascade = CascadeType.ALL)
	private Map<Searchbox, UserRole> roles = new HashMap<Searchbox,UserRole>();

	public User(){}
	
	public User(String email, String password){
		this.email = email;
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Map<Searchbox, UserRole> getRoles() {
		return roles;
	}

	public void setRoles(Map<Searchbox, UserRole> roles) {
		this.roles = roles;
	}

	public SocialMediaService getSignInProvider() {
		return SignInProvider;
	}

	public void setSignInProvider(SocialMediaService signInProvider) {
		SignInProvider = signInProvider;
	}
}
