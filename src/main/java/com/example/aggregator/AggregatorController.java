package com.example.aggregator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RestController
public class AggregatorController {
    private static final Logger logger = LoggerFactory.getLogger(AggregatorController.class);
    private static final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("ping")
    public List<String> ping() {
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
        List<Transaction> list = new ArrayList<>();
        List<String> apiUrls = List.of(
                "http://localhost:8888/transactions?account={account}",
                "http://localhost:8889/transactions?account={account}"
        );
        Map<String, String> params = new HashMap<>();
        params.put("account", account);

        try {
            ExecutorService executor = Executors.newFixedThreadPool(2);
            List<Callable<ResponseEntity<List<Transaction>>>> tasks = new ArrayList<>();
            for (String apiUrl : apiUrls) {
                tasks.add(new MyTask(apiUrl, params));
            }
            List<Future<ResponseEntity<List<Transaction>>>> futures = executor.invokeAll(tasks);
            for (Future<ResponseEntity<List<Transaction>>> future : futures) {
                ResponseEntity<List<Transaction>> response = future.get();
                list.addAll(Objects.requireNonNull(response.getBody()));
            }
            executor.shutdown();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        return list;
    }

    static class MyTask implements Callable<ResponseEntity<List<Transaction>>> {
        private final String apiUrl;
        private final Map<String, String> params;

        public MyTask(String apiUrl, Map<String, String> params) {
            this.apiUrl = apiUrl;
            this.params = params;
        }

        @Override
        public ResponseEntity<List<Transaction>> call() {
            for (int i = 0; i < 5; i++) {
                try {
                    ResponseEntity<List<Transaction>> response = restTemplate.exchange(
                            apiUrl,
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<>() {
                            },
                            params
                    );
                    if (response.getStatusCode() == HttpStatus.OK) {
                        logger.info("Attempt {} success {} {}", i + 1, apiUrl, response.getBody());
                        return response;
                    }
                } catch (HttpServerErrorException | HttpClientErrorException e) {
                    logger.info("Attempt {} fail {} {}", i + 1, apiUrl, e.getStatusCode());
                }

            }
            return ResponseEntity.ok(Collections.emptyList());
        }
    }
}