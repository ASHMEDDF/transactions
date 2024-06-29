package com.clients.devsu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DevsuApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevsuApplication.class, args);
	}

}
