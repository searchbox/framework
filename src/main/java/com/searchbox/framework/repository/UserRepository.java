package com.searchbox.framework.repository;

import org.springframework.data.repository.CrudRepository;

import com.searchbox.framework.domain.User;

public interface UserRepository extends CrudRepository<User, Long>{
	
	public User getUserByUsername(String username);
}
