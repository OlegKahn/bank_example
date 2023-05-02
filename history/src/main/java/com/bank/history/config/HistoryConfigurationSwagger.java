package com.bank.history.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Класс настройка для создания необходимого бина для Swagger
 * title - заголовок артефакта для сваггера
 * version - текущая версия артефакта
 * description - краткое описание артефакта
 * *
 * Абсолютный путь к Swagger: <a href="http://localhost:8088/api/history/swagger-ui/index.html">.Swagger.</a>
 * Абсолютный путь для Swagger: /api/history/v3/api-docs
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
