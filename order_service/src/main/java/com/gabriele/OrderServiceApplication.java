package com.gabriele;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = "com.gabriele.*")
@EnableR2dbcRepositories(basePackages = "com.gabriele.repository")

public class OrderServiceApplication {

    public static void main(String[] args) {
        //System.setProperty("server.servlet.context-path", "/order-service");
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        log.info("ORDER SERVICE STARTED");
    }

}
