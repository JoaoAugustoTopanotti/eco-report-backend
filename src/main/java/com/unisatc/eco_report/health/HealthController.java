package com.unisatc.eco_report.health;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Controlador REST que informa se a API está no ar. */
@RestController 
@RequestMapping("/api/health")
public class HealthController {
    
    @GetMapping 
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }
}
