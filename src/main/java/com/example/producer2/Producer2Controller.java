package com.example.producer2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
public class Producer2Controller {

    @Autowired
    private Producer2Repository producerRepository;

    @GetMapping("ping")
    public String ping() {
        return "Pong from server 2";
    }


    @GetMapping("transactions")
    public ResponseEntity<List<Transaction2>> getTransactions(@RequestParam String account) {
        Random random = new Random();
        return switch(random.nextInt(3)) {
            case 0 -> ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
            case 1 -> ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
            default -> ResponseEntity.ok(producerRepository.findById(account));
        };
    }

}