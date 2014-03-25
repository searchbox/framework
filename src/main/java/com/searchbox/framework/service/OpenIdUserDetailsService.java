package com.searchbox.framework.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;

import com.searchbox.framework.domain.Role;
import com.searchbox.framework.domain.UserRole;
import com.searchbox.framework.model.UserEntity;
import com.searchbox.framework.repository.UserRepository;

public class OpenIdUserDetailsService implements 
  AuthenticationUserDetailsService<OpenIDAuthenticationToken> {
  
  private UserRepository repository;

  @Autowired
  public OpenIdUserDetailsService(UserRepository repository) {
    this.repository = repository;
  }
  
  private static final Logger LOGGER = LoggerFactory
      .getLogger(OpenIdUserDetailsService.class);
  
  @Override
  public UserDetails loadUserDetails(OpenIDAuthenticationToken token)
      throws UsernameNotFoundException {
    LOGGER.info("Got OpenIdAuth with token {}",token);
    
    Map<String, String> attributes = new HashMap<>();
        
    UserEntity user = null;
    for(OpenIDAttribute attribute:token.getAttributes()){
      attributes.put(attribute.getName().toLowerCase(), StringUtils.join(attribute.getValues(), " "));
      if(attribute.getName().equalsIgnoreCase("email")){
        for(String attributeValue:attribute.getValues()){
          user = repository.findByEmail(attributeValue);
          if(user != null){
            break;
          }
        }
        break;
      }
    }
    
    if(user == null){
      user = new UserEntity();
      user.setUsername(attributes.get("email"));
      user.setEmail(attributes.get("email"));
//      user.setFirstName(attributes.get("firstname"));
//      user.setFirstName(attributes.get("lastname"));
      user.getRoles().add(Role.USER);
      
      user = repository.save(user);
    } 
    
    return user;
  }
}
