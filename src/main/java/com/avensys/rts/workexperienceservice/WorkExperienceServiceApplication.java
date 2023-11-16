package com.avensys.rts.workexperienceservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class WorkExperienceServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkExperienceServiceApplication.class, args);
	}

}
