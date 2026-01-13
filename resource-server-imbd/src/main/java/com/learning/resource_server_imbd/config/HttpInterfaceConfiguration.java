package com.learning.resource_server_imbd.config;

import com.learning.resource_server_imbd.communication.BoiHttpInterface;
import com.learning.resource_server_imbd.communication.RottenTomatoHttpInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpInterfaceConfiguration {

    @Autowired
    @Qualifier(value = "boiRestClient")
    RestClient boiRestClient;

    @Autowired
    @Qualifier(value = "rottenTomatoRestClient")
    RestClient rottenTomatoRestClient;

    @Bean
    BoiHttpInterface boiHttpInterface(){
        RestClientAdapter adapter = RestClientAdapter.create(boiRestClient);

        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(BoiHttpInterface.class);
    }

    @Bean
    RottenTomatoHttpInterface rottenTomatoHttpInterface(){
        RestClientAdapter adapter = RestClientAdapter.create(rottenTomatoRestClient);

        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(RottenTomatoHttpInterface.class);
    }
}
