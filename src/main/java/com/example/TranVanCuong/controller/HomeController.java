package com.example.TranVanCuong.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<?> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome to Inventory Management API");
        response.put("version", "1.0.0");
        response.put("endpoints", new HashMap<String, String>() {{
            put("products", "/api/products");
            put("inventory", "/api/inventory");
        }});
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<?> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("application", "Inventory Management Service");
        return ResponseEntity.ok(response);
    }
}
