package com.homebanking.payments.app.api.discovery.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class PaymentsAppDiscoveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentsAppDiscoveryServiceApplication.class, args);
	}

}
