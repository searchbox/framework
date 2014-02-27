package com.searchbox.framework.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.searchbox.framework.domain.User;
import com.searchbox.framework.repository.UserRepository;

public class RepositoryUserDetailsService implements UserDetailsService {

	private UserRepository repository;
	
	public RepositoryUserDetailsService(UserRepository repository){
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User user = repository.getUserByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("No user found with username: "
					+ username);
		}

		// User principal = ExampleUserDetails.getBuilder()
		// .firstName(user.getFirstName())
		// .id(user.getId())
		// .lastName(user.getLastName())
		// .password(user.getPassword())
		// .role(user.getRole())
		// .socialSignInProvider(user.getSignInProvider())
		// .username(user.getEmail())
		// .build();

		return user;
	}

}
