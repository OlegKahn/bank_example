package com.bank.history.util;

import io.micrometer.core.lang.NonNullApi;
import org.springframework.data.domain.AuditorAware;
import java.util.Optional;

/**
 * Класс, который необходим для аудирования, возможно...
 */
@NonNullApi
public class AuditorAwareImpl implements AuditorAware<String> {

    /**
     * Метод возвращает имя текущего аудитора
     * @return Optional<String>
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        final String auditorName = "Just me";
        return Optional.of(auditorName);
    }
}
