package com.example.aggregator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class AggregatorController {

    @GetMapping("aggregate")
    public String aggregate() {
        RestTemplate restTemplate = new RestTemplate();

        String apiUrl = "http://localhost:8889/ping";
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

        return response.getBody();
    }
}