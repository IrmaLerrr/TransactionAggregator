package com.example.producer1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
public class Producer1Controller {

    @Autowired
    private Producer1Repository producerRepository;

    @GetMapping("ping")
    public String ping() {
        return "Pong from server 1";
    }

    @GetMapping("transactions")
    public ResponseEntity<List<Transaction1>> getTransactions(@RequestParam String account) {
        Random random = new Random();
        return switch(random.nextInt(3)) {
            case 0 -> ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
            case 1 -> ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
            default -> ResponseEntity.ok(producerRepository.findById(account));
        };
    }

}