package com.bank.history.service;

import com.bank.history.exception.NoSuchHistoryException;
import com.bank.history.entity.History;
import com.bank.history.entity.HistoryTest;
import com.bank.history.repository.HistoryDao;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.history.RevisionMetadata;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HistoryServiceIT {

    HistoryService historyService;

    History history;


    @Autowired
    HistoryServiceIT(HistoryDao dao) {
        historyService = new HistoryServiceImpl(dao);
    }


    @Test
    @Order(1)
    void saveNew() {
        history = HistoryTest.getHistoryWithoutId(200);
        History aNew = historyService.saveNew(history);

        assertThat(aNew).isNotNull();
        assertThat(aNew).isEqualTo(history);
    }

    @Test
    @Order(2)
    void getById() {
        History byId = historyService.getById(history.getId());

        assertThat(byId).isNotNull();
        assertThat(byId).isEqualTo(history);
    }

    @Test
    @Order(3)
    void update() {
        history.setProfileAuditId(300);
        history.setAccountAuditId(300);
        history.setTransferAuditId(300);
        history.setAntiFraudAuditId(300);
        history.setAuthorizationAuditId(300);
        history.setPublicBankInfoAuditId(300);

        History updatedHistory = historyService.saveOld(history);

        assertThat(updatedHistory.getProfileAuditId())
                .isEqualTo(history.getProfileAuditId());

        assertThat(updatedHistory.getAccountAuditId())
                .isEqualTo(history.getAccountAuditId());

        assertThat(updatedHistory.getTransferAuditId())
                .isEqualTo(history.getTransferAuditId());

        assertThat(updatedHistory.getAntiFraudAuditId())
                .isEqualTo(history.getAntiFraudAuditId());

        assertThat(updatedHistory.getAuthorizationAuditId())
                .isEqualTo(history.getAuthorizationAuditId());

        assertThat(updatedHistory.getPublicBankInfoAuditId())
                .isEqualTo(history.getPublicBankInfoAuditId());
    }

    @Test
    @Order(4)
    void getLastAuditById() {
        Map<RevisionMetadata<Long>, History> lastAuditById
                = historyService.getLastAuditById(history.getId());

        assertThat(lastAuditById).isNotNull();
        assertThat(lastAuditById.containsValue(history)).isTrue();
    }

    @Test
    @Order(5)
    void delete() {
        long id = history.getId();
        historyService.delete(id);

        assertThrows(NoSuchHistoryException.class, ()-> historyService.delete(id));
    }
}
