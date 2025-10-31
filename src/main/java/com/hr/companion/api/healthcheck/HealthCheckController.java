package com.hr.companion.api.healthcheck;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/health-check")
public class HealthCheckController {
    @GetMapping
    public String healthCheck() {
        return "HR Companion API is up and running!";
    }
}
