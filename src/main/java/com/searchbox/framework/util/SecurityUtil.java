package com.searchbox.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.searchbox.framework.domain.User;

public class SecurityUtil {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(SecurityUtil.class);

  public static void logInUser(User user) {
    LOGGER.info("Logging in user: {}", user);

    LOGGER.debug("Logging in principal: {}", user);

    Authentication authentication = new UsernamePasswordAuthenticationToken(
        user, null, user.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);

    LOGGER.info("User: {} has been logged in.", user);
  }
}