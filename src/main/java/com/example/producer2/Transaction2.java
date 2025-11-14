package com.example.producer2;

import lombok.Data;

@Data
public class Transaction2 {
    private String id;
    private String serverId;
    private String account;
    private String amount;
    private String timestamp;
}
