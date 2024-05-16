package com.sparta.calendar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CalendarApplication {

    public static void main(String[] args) {
        SpringApplication.run( CalendarApplication.class, args );
    }

}
