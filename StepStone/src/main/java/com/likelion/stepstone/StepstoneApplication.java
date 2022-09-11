package com.likelion.stepstone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class StepstoneApplication {

	public static void main(String[] args) {
		SpringApplication.run(StepstoneApplication.class, args);
	}


}
