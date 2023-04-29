package com.bank.history.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HistoryRevInfoTest {

    @Test
    public void test() {
        HistoryRevInfo historyRevInfo = new HistoryRevInfo();
        historyRevInfo.setUsername("Bob");
        assertThat(historyRevInfo.getUsername()).isEqualTo("Bob");
    }
}