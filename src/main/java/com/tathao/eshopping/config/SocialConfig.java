package com.tathao.eshopping.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;

@Configuration
@EnableSocial
@PropertySource("classpath:social-cfg.properties")
public class SocialConfig implements SocialConfigurer {

    private boolean autoSignUp = false;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
        //Facebook
        FacebookConnectionFactory facebookFactory = new FacebookConnectionFactory(
                environment.getProperty("facebook.app.id"),
                environment.getProperty("facebook.app.secret")
        );
        facebookFactory.setScope(environment.getProperty("facebook.scope"));
        connectionFactoryConfigurer.addConnectionFactory(facebookFactory);
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository usersConnectionRepository = new JdbcUsersConnectionRepository(dataSource,
                connectionFactoryLocator,
                Encryptors.noOpText());

        if(autoSignUp) {
            ConnectionSignUp connectionSignUp = new MyConnectionSignUp(myUserAccountDAO);
            usersConnectionRepository.setConnectionSignUp(connectionSignUp);
        } else {
            usersConnectionRepository.setConnectionSignUp(null);
        }
        return usersConnectionRepository;
    }
}
