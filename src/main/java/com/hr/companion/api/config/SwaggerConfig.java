package com.hr.companion.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("My HR Companion API documentation")
                        .version("v1.0.0")
                        .description("API documentation for my application")
                        .contact(new Contact()
                                .name("Orlando Whyte")
                                .email("orlandogwhyte@gmail.com")
                                .url("https://example.com"))
                        .license(new License().name("Apache 2.0").url("https://springdoc.org")));

    }
}
