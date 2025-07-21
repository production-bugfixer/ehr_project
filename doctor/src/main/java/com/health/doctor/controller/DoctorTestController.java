package com.health.doctor.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("info")
public class DoctorTestController {

    private final Environment environment;

    
    private String appName="Doctor";

    public DoctorTestController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping
    public String getInfo() {
        String port = environment.getProperty("local.server.port");
        return appName + " is running on port " + port;
    }
}
