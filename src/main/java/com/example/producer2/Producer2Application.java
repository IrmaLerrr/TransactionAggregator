package com.example.producer2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Producer2Application {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Producer2Application.class);
		app.setAdditionalProfiles("producer2");
		app.run(args);
	}
}


