package com.health.patient;

import com.netflix.appinfo.EurekaInstanceConfig;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EurekaPortConfig {

    @Bean
    public ApplicationListener<WebServerInitializedEvent> webServerListener(EurekaInstanceConfigBean config) {
        return event -> {
            int port = event.getWebServer().getPort();
            config.setNonSecurePort(port);
        };
    }
}
