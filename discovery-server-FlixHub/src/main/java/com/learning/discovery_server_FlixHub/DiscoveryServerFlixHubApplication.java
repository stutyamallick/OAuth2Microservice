package com.learning.discovery_server_FlixHub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerFlixHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscoveryServerFlixHubApplication.class, args);
	}

}
