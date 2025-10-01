package com.learning.resource_server_boi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerAuthenticationManagerResolver;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests(c -> c.anyRequest().authenticated());

        JwtIssuerAuthenticationManagerResolver authenticationManagerResolver =
                JwtIssuerAuthenticationManagerResolver.fromTrustedIssuers("http://localhost:8100");

        http.oauth2ResourceServer(
                oAuth2 -> oAuth2.authenticationManagerResolver(authenticationManagerResolver)
        );

        return http.build();
    }
}
