package com.bank.history.util;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class AuditorAwareImplTest {

    @Test
    void getCurrentAuditor() {
        AuditorAwareImpl auditorAware = new AuditorAwareImpl();

        assertThat(auditorAware).isNotNull();
        assertThat(auditorAware.getCurrentAuditor()).isPresent();
    }
}