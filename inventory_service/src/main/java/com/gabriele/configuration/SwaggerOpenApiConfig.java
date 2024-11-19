package com.gabriele.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerOpenApiConfig {

    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SpringBoot API")
                        .description("SpringBoot sample application")
                        .version("v0.0.1")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://opensource.org/licenses/Apache-2.0"))
                        .contact(new Contact()
                                .name("Support Team")
                                .email("support@example.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringBoot Wiki Documentation")
                        .url("https://springboot.wiki.github.org/docs"));
    }
}

//http://localhost:8080/swagger-ui.html
//http://localhost:8080/v3/api-docs
