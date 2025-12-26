package com.learning.resource_server_imbd.config;

import com.learning.resource_server_imbd.communication.BoiHttpInterface;
import com.learning.resource_server_imbd.communication.RottenTomatoHttpInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestTemplateAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class HttpInterfaceConfiguration {

    @Autowired
    @Qualifier(value = "restTemplateBoi")
    RestTemplate restTemplateBoi;

    @Autowired
    @Qualifier(value = "restTemplateRottenTomato")
    RestTemplate restTemplateRottenTomato;

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

    @Bean
    BoiHttpInterface boiHttpInterface(){
        restTemplateBoi.setUriTemplateHandler(
                new DefaultUriBuilderFactory("lb://RESOURCE-SERVER-BOI")
        );
        restTemplateBoi.getInterceptors().add(bearerTokenInterceptor());

        RestTemplateAdapter adapter = RestTemplateAdapter.create(restTemplateBoi);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(BoiHttpInterface.class);
    }

    @Bean
    RottenTomatoHttpInterface rottenTomatoHttpInterface(){
        restTemplateRottenTomato.setUriTemplateHandler(
                new DefaultUriBuilderFactory("lb://RESOURCE-SERVER-ROTTENTOMATO")
        );
        restTemplateRottenTomato.getInterceptors().add(bearerTokenInterceptor());

        RestTemplateAdapter adapter = RestTemplateAdapter.create(restTemplateRottenTomato);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(RottenTomatoHttpInterface.class);
    }
}
