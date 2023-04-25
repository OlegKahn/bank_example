package com.bank.history.validator;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class CheckHistoryDataValidatorTest {

    @Test
    void isValidSuccess() {
        CheckHistoryDataValidator validator = new CheckHistoryDataValidator();
        String o = "11111";
        assertThat(o).isNotNull();
        boolean isValid = validator.isValid(o, null);
        assertThat(isValid).isTrue();
    }

    @Test
    void isValidFail() {
        CheckHistoryDataValidator validator = new CheckHistoryDataValidator();
        String test = "test";
        assertThat(test).isNotNull();
        boolean isValid = validator.isValid(test, null);
        assertThat(isValid).isFalse();
    }
}