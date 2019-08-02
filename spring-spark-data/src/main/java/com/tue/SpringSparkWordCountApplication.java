package com.tue;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;

@SpringBootApplication
@EnableAdminServer
@EnableScheduling
@Slf4j
public class SpringSparkWordCountApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringSparkWordCountApplication.class, args);
    }

    @Scheduled(initialDelay = 1000, fixedDelay = 5000)
    public void processText() {
        log.info("Execute by cron job {}", Instant.now());
    }

}
