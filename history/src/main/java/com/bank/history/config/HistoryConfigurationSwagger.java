package com.bank.history.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for creating the required bean for Swagger
 * title - artifact header for swagger
 * version - current version of the artifact
 * description - brief description of the artifact
 * *
 * Absolute path to Swagger: <a href="http://localhost:8088/api/history/swagger-ui/index.html">.Swagger.</a>
 * Absolute path for Swagger: /api/history/v3/api-docs
 */
@OpenAPIDefinition
@Configuration
public class HistoryConfigurationSwagger {

    @Bean
    public OpenAPI baseOpenApi() {
        return new OpenAPI().info(new Info().title("History swagger")
                .version("0.1.6").description("Open Api for History"));
    }
}
