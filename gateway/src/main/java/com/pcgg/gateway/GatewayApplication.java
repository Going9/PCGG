package com.pcgg.gateway;

import com.netflix.discovery.EurekaClient;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayApplication {

	@Autowired
	private EurekaClient eurekaClient;

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	// 애플리케이션이 종료되는 시점에 메소드 호출
	// Eureka Client를 종료해 등록 해제
	@PreDestroy
	private void unregister() {
		eurekaClient.shutdown();

	}

}
