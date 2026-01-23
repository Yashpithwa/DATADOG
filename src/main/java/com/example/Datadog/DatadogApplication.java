package com.example.Datadog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class DatadogApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatadogApplication.class, args);
	}

}
