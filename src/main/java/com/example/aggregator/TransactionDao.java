package com.example.aggregator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionDao {
    private final ExternalApiService apiService;
    private static final Logger logger = LoggerFactory.getLogger(TransactionDao.class);

    public TransactionDao(ExternalApiService apiService) {
        this.apiService = apiService;
    }

    @Cacheable(cacheNames = "transactions", key = "#account")
    public List<Transaction> getTransactions(String account) {
        return apiService.aggregateTransactions(account);
    }
}
