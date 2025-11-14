package com.example.producer1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Producer1Controller {

    @Autowired
    private Producer1Repository producerRepository;

    @GetMapping("ping")
    public String ping() {
        return "Pong from server 1";
    }

    @GetMapping("transactions")
    public List<Transaction1> getTransactions(@RequestParam String account) {
        return producerRepository.findById(account);
    }

}