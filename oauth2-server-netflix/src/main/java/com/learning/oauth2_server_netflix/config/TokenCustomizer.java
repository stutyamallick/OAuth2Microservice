package com.learning.oauth2_server_netflix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TokenCustomizer {
    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> customizeToken() {
        return context -> {
            var principal = context.getPrincipal();
            if(principal == null)
                return;

            Set<String> authorities = principal.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

            if (context.getTokenType().getValue().equals("access_token"))
                context.getClaims().claim("authorities", authorities);

            if (context.getTokenType().getValue().equals("id_token"))
                context.getClaims().claim("authorities", authorities);

            context.getClaims().claim("username", principal.getName());
        };
    }
}
