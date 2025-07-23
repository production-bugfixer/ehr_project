package com.health.registerservice.config;

import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {

    @Bean
    public ProcessorMetrics processorMetrics() {
        // Prevent Micrometer from trying to load internal JDK classes
        return null;
    }
}
