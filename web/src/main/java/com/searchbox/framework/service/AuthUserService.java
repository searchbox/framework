package com.searchbox.framework.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAuthenticationToken;

import com.searchbox.framework.domain.Role;
import com.searchbox.framework.model.UserEntity;
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
   *          The username of the requested user.
   * @return The information of the user.
   * @throws UsernameNotFoundException
   *           Thrown if no user is found with the given username.
   */
  @Override
  public UserDetails loadUserByUsername(String username)
      throws UsernameNotFoundException {
    LOGGER.info("Loading user by username: {}", username);

    UserEntity user = repository.findByEmail(username);
    LOGGER.info("Found user: {}", user);
    
    //TODO: remove this - users were imported without any role.
    if(user.getRoles().size() == 0){
      user.getRoles().add(Role.USER);
      user = repository.save(user);
    }

    return user;
  }
}
