package com.health.registerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class RegisterserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegisterserviceApplication.class, args);
	}

}
