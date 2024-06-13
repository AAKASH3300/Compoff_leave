package com.itime.compoff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CompoffApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompoffApplication.class, args);
	}

}
