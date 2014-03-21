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
package com.searchbox.framework.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.security.SocialUserDetails;

import com.searchbox.framework.domain.UserRole;
import com.searchbox.framework.web.user.SocialMediaService;

@Entity
public class UserEntity extends BaseEntity<Long>
  implements SocialUserDetails, UserDetails, Comparable<UserEntity> {

  @Column(nullable = false, unique = true)
  private String email;

  private String password;

  private boolean accountNonExpired = true;
  private boolean accountNonLocked = true;
  private boolean credentialsNonExpired = true;
  private boolean enabled = true;

  private SocialMediaService SignInProvider;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  @LazyCollection(LazyCollectionOption.FALSE)
  List<UserRole> roles = new ArrayList<UserRole>();

  // private Map<Searchbox, UserRole> roles = new
  // HashMap<Searchbox,UserRole>();

  public UserEntity() {
  }

  public UserEntity(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<UserRole> getRoles() {
    return roles;
  }

  public void setRoles(List<UserRole> roles) {
    this.roles = roles;
  }

  public SocialMediaService getSignInProvider() {
    return SignInProvider;
  }

  public void setSignInProvider(SocialMediaService signInProvider) {
    SignInProvider = signInProvider;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles;
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  public String setUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return this.accountNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    // TODO Auto-generated method stub
    return this.accountNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    // TODO Auto-generated method stub
    return this.credentialsNonExpired;
  }

  @Override
  public boolean isEnabled() {
    return this.enabled;
  }

  @Override
  public String getUserId() {
    return getUsername();
  }

  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this,
        ToStringStyle.SHORT_PREFIX_STYLE);
  }

  @Override
  public int compareTo(UserEntity o) {
    return this.getEmail()
        .compareTo(o.getEmail());
  }
}
