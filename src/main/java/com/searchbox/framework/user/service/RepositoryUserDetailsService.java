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
package com.searchbox.framework.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.searchbox.framework.domain.User;
import com.searchbox.framework.repository.UserRepository;
import com.searchbox.framework.user.ApplicationUser;

public class RepositoryUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(RepositoryUserDetailsService.class);

    private UserRepository repository;

    @Autowired
    public RepositoryUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    /**
     * Loads the user information.
     * @param username  The username of the requested user.
     * @return  The information of the user.
     * @throws UsernameNotFoundException    Thrown if no user is found with the given username.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Loading user by username: {}", username);

        User user = repository.findByEmail(username);
        logger.debug("Found user: {}", user);

        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }

        ApplicationUser principal = ApplicationUser.getBuilder()
                //.firstName(user.getFirstName())
                .id(user.getId())
                //.lastName(user.getLastName())
                .password(user.getPassword())
                //.role(user.getRole())
                //.socialSignInProvider(user.getSignInProvider())
                .username(user.getEmail())
                .build();

        logger.debug("Returning user details: {}", principal);

        return principal;
    }
}
