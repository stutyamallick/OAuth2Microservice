package com.learning.resource_server_imbd.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfiguration {

    @Bean
    @LoadBalanced
    public RestClient.Builder loadBalancedRestClientBuilder(){ return RestClient.builder(); }

    @Bean
    public RestClient boiRestClient(
            RestClient.Builder lbBuilder, ClientHttpRequestInterceptor bearerTokenInterceptor){
        return lbBuilder
                .baseUrl("lb://RESOURCE-SEVER-BOI")
                .requestInterceptor(bearerTokenInterceptor)
                .build();
    }

    @Bean
    public RestClient rottenTomatoRestClient(
            RestClient.Builder lbBuilder, ClientHttpRequestInterceptor bearerTokenInterceptor){
        return lbBuilder
                .baseUrl("lb://RESOURCE-SEVER-ROTTENTOMATO")
                .requestInterceptor(bearerTokenInterceptor)
                .build();
    }

    /*private ClientHttpRequestInterceptor bearerTokenInterceptor(){
        return ((request, body, execution) -> {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if(auth instanceof JwtAuthenticationToken jwtAuth)
                request.getHeaders().setBearerAuth(jwtAuth.getToken().getTokenValue());

            return execution.execute(request, body);
        });
    }*/

    @Bean
    public ClientHttpRequestInterceptor bearerTokenInterceptor(){
        return (request, body, execution) -> {
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();
            if(authentication instanceof JwtAuthenticationToken jwtAuth){
                Jwt jwt = jwtAuth.getToken();
                if(jwt != null)
                    request.getHeaders().setBearerAuth(jwt.getTokenValue());
            }

            return execution.execute(request, body);
        };
    }
}
