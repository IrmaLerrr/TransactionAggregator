package com.example.aggregator;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionDao dao;

    public TransactionService(TransactionDao dao) {
        this.dao = dao;
    }

    public List<Transaction> getTransactions(String account) {
        return dao.getTransactions(account);
    }

}