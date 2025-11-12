package com.example.aggregator;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AggregatorController {

    @GetMapping("ping")
    public List<String> ping() {
        RestTemplate restTemplate = new RestTemplate();
        List<String> list = new ArrayList<>();
        List<String> apiUrls = List.of(
                "http://localhost:8888/ping",
                "http://localhost:8889/ping"
        );
        for (String apiUrl : apiUrls) {
            ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
            list.add(response.getBody());
        }

        return list;
    }

    @GetMapping("aggregate")
    public List<Transaction> aggregate(@RequestParam String account) {
        RestTemplate restTemplate = new RestTemplate();
        List<Transaction> list = new ArrayList<>();
        List<String> apiUrls = List.of(
                "http://localhost:8888/transactions?account={account}",
                "http://localhost:8889/transactions?account={account}"
        );
        Map<String, String> params = new HashMap<>();
        params.put("account", account);

        for (String apiUrl : apiUrls) {
            ResponseEntity<List<Transaction>> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Transaction>>() {},
                    params
            );
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null && !response.getBody().isEmpty()) {
                list.addAll(response.getBody());
            }
        }

        return list;
    }
}