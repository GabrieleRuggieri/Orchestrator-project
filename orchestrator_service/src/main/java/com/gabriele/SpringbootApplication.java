package com.gabriele;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import io.r2dbc.spi.ConnectionFactory;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = "com.gabriele.*")
public class SpringbootApplication {

    public static void main(String[] args) {
        //System.setProperty("server.servlet.context-path", "/orchestrator-service");
        SpringApplication.run(SpringbootApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        log.info("ORCHESTRATOR SERVICE STARTED");
    }

}
