package com.example.producer1;

import lombok.Data;

@Data
public class Transaction1 {
    private String id;
    private String serverId;
    private String account;
    private String amount;
    private String timestamp;
}
