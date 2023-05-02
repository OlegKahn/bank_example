package com.bank.history.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;

class HistoryRevInfoTest {

    @Test
    public void testSuccess() {
        HistoryRevInfo historyRevInfo = new HistoryRevInfo();
        historyRevInfo.setUsername("Bob");
        assertThat(historyRevInfo.getUsername()).isEqualTo("Bob");
    }

    @Test
    public void testFail() {
        HistoryRevInfo historyRevInfo = new HistoryRevInfo();
        Object o = anyLong();
        assertThrows(ClassCastException.class, () -> historyRevInfo.setUsername((String) o));
    }
}