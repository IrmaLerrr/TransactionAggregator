package com.example.producer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

    @GetMapping("ping")
    public String ping() {
        return "Pong from server 1";
    }

}