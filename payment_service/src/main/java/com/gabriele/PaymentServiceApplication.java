package com.gabriele;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = "com.gabriele.*")
public class PaymentServiceApplication {

    public static void main(String[] args) {
        //System.setProperty("server.servlet.context-path", "/payment-service");
        SpringApplication.run(PaymentServiceApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        log.info("PAYMENT SERVICE STARTED");
    }

}
