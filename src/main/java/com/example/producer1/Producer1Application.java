package com.example.producer1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Producer1Application {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Producer1Application.class);
		app.setAdditionalProfiles("producer1");
		app.run(args);
	}
}


