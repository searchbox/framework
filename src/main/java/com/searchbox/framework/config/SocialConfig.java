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
package com.searchbox.framework.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

/**
 * @author Stephane Gamard
 *         http://www.petrikainulainen.net/programming/spring-framework
 *         /adding-social-sign-in-to-a-spring-mvc-web-application-configuration/
 */
@Configuration
@EnableSocial
public class SocialConfig implements SocialConfigurer {

    @Autowired
    private DataSource dataSource;

    /**
     * Configures the connection factories for Facebook and Twitter.
     * 
     * @param cfConfig
     * @param env
     */
    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig,
            Environment env) {
        if (env.getProperty("twitter.consumer.key") != null
                && env.getProperty("twitter.consumer.secret") != null) {
            cfConfig.addConnectionFactory(new TwitterConnectionFactory(env
                    .getProperty("twitter.consumer.key"), env
                    .getProperty("twitter.consumer.secret")));
        }
        if (env.getProperty("facebook.app.id") != null
                && env.getProperty("facebook.app.secret") != null) {
            cfConfig.addConnectionFactory(new FacebookConnectionFactory(env
                    .getProperty("facebook.app.id"), env
                    .getProperty("facebook.app.secret")));
        }
    }

    /**
     * The UserIdSource determines the account ID of the user. The example
     * application uses the username as the account ID.
     */
    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(
            ConnectionFactoryLocator connectionFactoryLocator) {
        return new JdbcUsersConnectionRepository(dataSource,
                connectionFactoryLocator,
                /**
                 * The TextEncryptor object encrypts the authorization details
                 * of the connection. In our example, the authorization details
                 * are stored as plain text. DO NOT USE THIS IN PRODUCTION.
                 */
                Encryptors.noOpText());
    }

    /**
     * This bean manages the connection flow between the account provider and
     * the example application.
     */
    @Bean
    public ConnectController connectController(
            ConnectionFactoryLocator connectionFactoryLocator,
            ConnectionRepository connectionRepository) {
        return new ConnectController(connectionFactoryLocator,
                connectionRepository);
    }
}
