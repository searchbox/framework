package com.searchbox.framework.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

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
	private User user;
	
	@ManyToOne
	private Searchbox searchbox;
	
	private Role role;
	
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


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
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


	@Override
	public String getAuthority() {
		return role.name();
	}
}
