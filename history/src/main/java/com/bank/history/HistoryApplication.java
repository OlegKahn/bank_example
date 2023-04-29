package com.bank.history;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Олег Кан
 * @version 0.1.3
 *
 * Класс предназначен для запуска приложения и регистрации бинов
 */
@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class,
        basePackages = {"com.bank.history.repository" })
public class HistoryApplication {

    /**
     * Стандартный метод выполняющий запуск приложения
     *
     * @param args В Java args содержит предоставленные
     *             аргументы командной строки в виде массива String объектов.
     */
    public static void main(String[] args) {
        SpringApplication.run(HistoryApplication.class, args);
    }

    /**
     * @return возвращает бин ModelMapper
     */
    @Bean(name = "myEntityMapper")
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
