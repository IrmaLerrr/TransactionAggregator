package com.example.aggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AggregatorApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(AggregatorApplication.class);
		app.setAdditionalProfiles("aggregator");
		app.run(args);
	}

}
