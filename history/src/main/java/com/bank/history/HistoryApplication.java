package com.bank.history;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HistoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(HistoryApplication.class, args);
    }

    // bean for dto
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
