package com.ssafy.pcgg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PcggApplication {

	public static void main(String[] args) {
		SpringApplication.run(PcggApplication.class, args);
	}

}
