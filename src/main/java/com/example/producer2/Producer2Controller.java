package com.example.producer2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Producer2Controller {

    @Autowired
    private Producer2Repository producerRepository;

    @GetMapping("ping")
    public String ping() {
        return "Pong from server 2";
    }

    @GetMapping("transactions")
    public List<Transaction2> getTransactions(@RequestParam String account) {
        return producerRepository.findById(account);
    }

}