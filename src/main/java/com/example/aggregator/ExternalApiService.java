package com.example.aggregator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@EnableAsync
public class ExternalApiService {
    private final List<String> apiUrls = List.of(
            "http://localhost:8888/",
            "http://localhost:8889/"
    );
    private static final Logger logger = LoggerFactory.getLogger(ExternalApiService.class);
    private static final RestTemplate restTemplate = new RestTemplate();

    public List<Transaction> aggregateTransactions(String account){
        List<Transaction> list = new ArrayList<>();
        List<CompletableFuture<ResponseEntity<List<Transaction>>>> completableFuture = new ArrayList<>();

        for (String apiUrl : apiUrls) {
            apiUrl = apiUrl + "transactions?account={account}";
            Map<String, String> params = new HashMap<>();
            params.put("account", account);
            completableFuture.add(getTransactionsAsync(account, apiUrl, params));
        }

        try {
            for (CompletableFuture<ResponseEntity<List<Transaction>>> responseEntityCompletableFuture : completableFuture) {
                list.addAll(Objects.requireNonNull(responseEntityCompletableFuture.get().getBody()));
            }
        } catch (InterruptedException | ExecutionException ex) {
            logger.error(ex.getMessage());
        }

        return list;
    }

    @Async
    public CompletableFuture<ResponseEntity<List<Transaction>>> getTransactionsAsync(String account, String apiUrl, Map<String, String> params){
        for (int i = 0; i < 5; i++) {
            try {
                ResponseEntity<List<Transaction>> response = restTemplate.exchange(
                        apiUrl,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {},
                        params
                );
                if (response.getStatusCode() == HttpStatus.OK) {
                    logger.info("Attempt {} success {} {}", i + 1, apiUrl, response.getBody());
                    return CompletableFuture.completedFuture(response);
                }
            } catch (HttpServerErrorException | HttpClientErrorException e) {
                logger.info("Attempt {} fail {} {}", i + 1, apiUrl, e.getStatusCode());
            }

        }
        return CompletableFuture.completedFuture(ResponseEntity.ok(Collections.emptyList()));
    }
}
