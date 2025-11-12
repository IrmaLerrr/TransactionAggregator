package com.example.aggregator;

import lombok.Data;

@Data
public class Transaction {
    private String id;
    private String serverId;
    private String account;
    private String amount;
    private String timestamp;
}
