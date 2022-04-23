package com.bank.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main launcher class
 */
@SpringBootApplication
public class BankSystemApplication {
	/**
	 * Main launcher function
	 * @param args command line args
	 */
	public static void main(String[] args) {
		SpringApplication.run(BankSystemApplication.class, args);
	}
}
