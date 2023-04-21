package com.bank.history.validator;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class CheckHistoryDataValidatorTest {

    @Test
    void isValidSuccess() {
        CheckHistoryDataValidator validator = new CheckHistoryDataValidator();
        boolean isValid = validator.isValid("11111", null);

        assertThat(isValid).isTrue();
    }

    @Test
    void isValidFail() {
        CheckHistoryDataValidator validator = new CheckHistoryDataValidator();
        boolean isValid = validator.isValid("test", null);

        assertThat(isValid).isFalse();
    }
}