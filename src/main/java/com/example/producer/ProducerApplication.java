package com.example.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProducerApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ProducerApplication.class);
		app.setAdditionalProfiles("producer");
		app.run(args);
	}

}


