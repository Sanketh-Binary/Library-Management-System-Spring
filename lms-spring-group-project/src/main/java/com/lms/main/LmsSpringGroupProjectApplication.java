package com.lms.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.lms")
public class LmsSpringGroupProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(LmsSpringGroupProjectApplication.class, args);
	}
}
