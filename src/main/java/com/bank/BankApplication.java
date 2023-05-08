package com.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.bank.repository")
@EntityScan(basePackages={"com.bank.entity"})
public class BankApplication {
	public static void main(final String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}
}
