package com.bank.history.entity;

import com.bank.history.dto.HistoryDto;
import java.util.ArrayList;
import java.util.List;

public class HistoryTest {

    public static List<History> getHistories() {
        List<History> historyList = new ArrayList<>();
        historyList.add(getHistory(1));
        historyList.add(getHistory(2));
        historyList.add(getHistory(3));
        return historyList;
    }

    public static History getHistory(long l) {
        return  History.builder()
                .id(l)
                .accountAuditId(l)
                .antiFraudAuditId(l)
                .authorizationAuditId(l)
                .profileAuditId(l)
                .transferAuditId(l)
                .publicBankInfoAuditId(l)
                .build();
    }

    public static History getHistoryWithoutId(long l) {
        return  History.builder()
                .accountAuditId(l)
                .antiFraudAuditId(l)
                .authorizationAuditId(l)
                .profileAuditId(l)
                .transferAuditId(l)
                .publicBankInfoAuditId(l)
                .build();
    }

    public static HistoryDto getHistoryDto(long l) {
        return  HistoryDto.builder()
                .id(l)
                .accountAuditId(l)
                .antiFraudAuditId(l)
                .authorizationAuditId(l)
                .profileAuditId(l)
                .transferAuditId(l)
                .publicBankInfoAuditId(l)
                .build();
    }
}
