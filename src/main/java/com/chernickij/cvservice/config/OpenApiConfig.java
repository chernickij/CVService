package com.chernickij.cvservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Chernickij Daniil",
                        email = "chernickijd@gmail.com"
                ),
                description = "Service for CV manage",
                title = "CV Service API",
                version = "1.0"
        )
)
public class OpenApiConfig {
}
