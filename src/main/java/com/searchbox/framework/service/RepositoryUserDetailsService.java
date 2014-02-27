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
