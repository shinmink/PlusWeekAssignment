package com.sparta.plusweekassignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PlusWeekAssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlusWeekAssignmentApplication.class, args);
    }

}
