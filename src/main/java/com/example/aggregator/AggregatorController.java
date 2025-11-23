package com.example.aggregator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class AggregatorController {
private final TransactionService transactionService;

    public AggregatorController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("aggregate")
    public List<Transaction> aggregate(@RequestParam String account) {
        return transactionService.getTransactions(account);
    }
}