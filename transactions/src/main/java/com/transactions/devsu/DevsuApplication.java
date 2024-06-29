package com.transactions.devsu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan
@EnableJpaRepositories
@EnableFeignClients(basePackages = "com.transactions.devsu.services")
public class DevsuApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevsuApplication.class, args);
	}

}
