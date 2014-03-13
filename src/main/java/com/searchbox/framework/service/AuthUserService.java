package com.searchbox.framework.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.searchbox.framework.domain.User;
import com.searchbox.framework.repository.UserRepository;

public class AuthUserService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(AuthUserService.class);

    private UserRepository repository;

    @Autowired
    public AuthUserService(UserRepository repository) {
        this.repository = repository;
    }

    /**
     * Loads the user information.
     * 
     * @param username
     *            The username of the requested user.
     * @return The information of the user.
     * @throws UsernameNotFoundException
     *             Thrown if no user is found with the given username.
     */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        LOGGER.info("Loading user by username: {}", username);

        User user = repository.findByEmail(username);
        LOGGER.info("Found user: {}", user);

        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: "
                    + username);
        }

        return user;
    }
}
