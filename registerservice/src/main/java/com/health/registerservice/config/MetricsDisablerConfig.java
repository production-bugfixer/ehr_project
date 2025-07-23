package com.health.registerservice.config;

import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsDisablerConfig {

    @Bean
    public ProcessorMetrics processorMetrics() {
        // Return null to disable this system binder that causes cgroup errors
        return null;
    }
}

