package com.teto.planner.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/health")
    @SecurityRequirements
    public Map<String, String> health() {
        return Map.of("status", "ok");
    }
}
