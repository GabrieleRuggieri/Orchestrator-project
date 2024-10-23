package com.patroclos.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerOpenApiConfig {

    private static final String API_TITLE = "SpringBoot API";
    private static final String API_DESCRIPTION = "SpringBoot sample application";
    private static final String API_VERSION = "v0.0.1";
    private static final String LICENSE_NAME = "Apache 2.0";
    private static final String LICENSE_URL = "https://opensource.org/licenses/Apache-2.0";
    private static final String WIKI_URL = "https://springboot.wiki.github.org/docs";
    private static final String CONTACT_NAME = "Support Team";
    private static final String CONTACT_EMAIL = "support@example.com";

    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(API_TITLE)
                        .description(API_DESCRIPTION)
                        .version(API_VERSION)
                        .license(new License().name(LICENSE_NAME).url(LICENSE_URL))
                        .contact(new Contact()
                                .name(CONTACT_NAME)
                                .email(CONTACT_EMAIL)))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringBoot Wiki Documentation")
                        .url(WIKI_URL));
    }
}