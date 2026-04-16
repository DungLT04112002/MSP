package com.pizza.discovery_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer; // Dòng này cực kỳ quan trọng
@EnableEurekaServer // Kích hoạt tính năng làm server tổng đài
@SpringBootApplication
public class DiscoveryServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscoveryServerApplication.class, args);
	}

}
