package com.health.registerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.SystemMetricsAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
@EnableAutoConfiguration(exclude = { SystemMetricsAutoConfiguration.class })
public class RegisterserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegisterserviceApplication.class, args);
	}

}
