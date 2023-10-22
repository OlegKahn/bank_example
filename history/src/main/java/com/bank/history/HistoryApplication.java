package com.bank.history;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Kan Oleg
 * @version 0.1.6
 *
 * The class is designed to run the application and register beans
 */
@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class,
        basePackages = {"com.bank.history.repository" })
public class HistoryApplication {

    /**
     * Standard method for launching an application
     *
     * @param args In Java, args contains the provided ones
     *             Command line arguments, specified as an array of String objects.
     */
    public static void main(String[] args) {
        SpringApplication.run(HistoryApplication.class, args);
    }

    /**
     * @return returns the ModelMapper bean
     */
    @Bean(name = "myEntityMapper")
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
