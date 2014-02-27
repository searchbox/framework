package com.searchbox.framework.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().antMatchers("/assets/**").permitAll()
//		
//			.anyRequest().authenticated().
//				and().formLogin()
//				.loginPage("/login").permitAll().and().logout().permitAll();
	}

	@Autowired
	//TODO change to a inMemoryUserDetailSercive
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("toto")
				.roles("USER").and().withUser("admin").password("toto")
				.roles("USER", "ADMIN");
	}
	
	@Bean
	//TODO get a real UserDetailService later on
    public UserDetailsService userDetailsServiceBean() {
        return new UserDetailsService() {
            private final Logger logger = LoggerFactory.getLogger(UserDetailsService.class);

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                List<GrantedAuthority> list = new ArrayList<>();
                String login = null;
                String password = null;

                logger.debug("Started loading user by name: " + username);
                if (username.equals("admin")) {
                    login = "admin";
                    password = "admin";
                    list.add(new SimpleGrantedAuthority("ROLE_USER"));
                    list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                }

                if (username.equals("user")) {
                    login = "user";
                    password = "user";
                    list.add(new SimpleGrantedAuthority("ROLE_USER"));
                }
                logger.debug("User " + username + ": " + login + ", " + password);

                return new User(login, password, true, true, true, true, list);
            }
        };
    }
}