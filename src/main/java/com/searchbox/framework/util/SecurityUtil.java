package com.searchbox.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.searchbox.framework.domain.User;
import com.searchbox.framework.user.ApplicationUser;

public class SecurityUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityUtil.class);

    public static void logInUser(User user) {
        LOGGER.info("Logging in user: {}", user);

        ApplicationUser userDetails = ApplicationUser.getBuilder()
                //.firstName(user.getFirstName())
                .id(user.getId())
                //.lastName(user.getLastName())
                .password(user.getPassword())
                //.role(user.getRole())
                //.socialSignInProvider(user.getSignInProvider())
                .username(user.getEmail())
                .build();
        LOGGER.debug("Logging in principal: {}", userDetails);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        LOGGER.info("User: {} has been logged in.", userDetails);
    }
}