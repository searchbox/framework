package com.searchbox.framework.domain;

import java.util.Collection;
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

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.searchbox.framework.domain.UserRole.Role;


@Entity
public class User implements UserDetails{

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
	
	private String username;
	
	private String password;
	
	@OneToMany(cascade = CascadeType.ALL)
	private Map<Searchbox, UserRole> roles;
	
	public User(){
		roles = new HashMap<Searchbox,UserRole>();
	}

	public User(String username, String password){
		this.username = username;
		this.password = password;
		roles = new HashMap<Searchbox,UserRole>();
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	public void addRole(Searchbox searchbox, Role role) {
		this.roles.put(searchbox, new UserRole(this, role));
	}	
}