package com.learning.resource_server_imbd.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplateBoi(){ return new RestTemplate(); }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplateRottenTomato(){ return new RestTemplate(); }
}
