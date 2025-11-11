package com.scheduleprojectskilled;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ScheduleProjectSkilledApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScheduleProjectSkilledApplication.class, args);
	}

}
