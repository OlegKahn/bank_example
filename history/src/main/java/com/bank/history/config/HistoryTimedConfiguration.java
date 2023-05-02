package com.bank.history.config;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Класс, предназначен для регистрации необходимых бинов,
 * для Prometheus и Grafana
 */
@Configuration
public class HistoryTimedConfiguration {

    @Bean
    public TimedAspect timedAspect(MeterRegistry meterRegistry) {
        return new TimedAspect(meterRegistry);
    }

    @Bean
    public AtomicInteger atomicInteger() {
        return new AtomicInteger();
    }
}
