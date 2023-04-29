package com.bank.history.repository;

import com.bank.history.entity.History;
import com.bank.history.entity.HistoryTest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.history.Revision;
import org.springframework.data.history.Revisions;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HistoryDaoIT {

    HistoryDao historyDao;

    History history1;
    History history2;
    History history3;

    History update1;
    History update2;


    @Autowired
    HistoryDaoIT(HistoryDao historyDao) {
        this.historyDao = historyDao;
    }


    @Test
    @Order(1)
    void save() {
        history1 = HistoryTest.getHistoryWithoutId(11);
        history2 = HistoryTest.getHistoryWithoutId(12);
        history3 = HistoryTest.getHistoryWithoutId(13);

        historyDao.save(history1);
        historyDao.save(history2);
        historyDao.save(history3);

        assertThat(history1.getId()).isGreaterThan(0);
        assertThat(history2.getId()).isGreaterThan(0);
        assertThat(history3.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    void getByIdSuccess() {
        Optional<History> history4 = historyDao.findById(history1.getId());
        Optional<History> history5 = historyDao.findById(history2.getId());
        Optional<History> history6 = historyDao.findById(history3.getId());

        assertThat(history4).isPresent();
        assertThat(history5).isPresent();
        assertThat(history6).isPresent();

        assertThat(history4.orElseThrow().getId()).isEqualTo(history1.getId());
        assertThat(history5.orElseThrow().getId()).isEqualTo(history2.getId());
        assertThat(history6.orElseThrow().getId()).isEqualTo(history3.getId());
    }

    @RepeatedTest(3)
    void getByIdFail() {
        Optional<History> historyOptional = historyDao.findById(anyLong());

        assertThat(historyOptional).isEmpty();
    }

    @Test
    @Order(3)
    void getAll() {
        List<History> historyList = historyDao.findAll();

        assertThat(historyList.size()).isGreaterThan(3);

        List<Long> longList = historyList.stream().map(History::getId).toList();

        assertThat(longList).contains(history1.getId(), history2.getId(), history3.getId());
    }

    @Test
    @Order(4)
    void update() {
        History history4 = historyDao.findById(history1.getId()).orElseThrow();
        History history5 = historyDao.findById(history2.getId()).orElseThrow();
        History history6 = historyDao.findById(history3.getId()).orElseThrow();

        history4.setAccountAuditId(10);
        history5.setAntiFraudAuditId(20);
        history6.setAuthorizationAuditId(30);

        update1 = historyDao.save(history4);
        update2 = historyDao.save(history5);
        History update3 = historyDao.save(history6);

        assertThat(update1.getAccountAuditId()).isEqualTo(10);
        assertThat(update2.getAntiFraudAuditId()).isEqualTo(20);
        assertThat(update3.getAuthorizationAuditId()).isEqualTo(30);
    }

    @Test
    @Order(5)
    void getAllAuditById() {
        Revisions<Long, History> revisions = historyDao.findRevisions(history1.getId());

        assertThat(revisions).isNotNull();

        List<Revision<Long, History>> revisionsContent = revisions.getContent();

        assertThat(revisionsContent.get(revisionsContent.size()-1)
                .getEntity()).isEqualTo(update1);
    }

    @Test
    @Order(6)
    void getLastAuditByIdSuccess() {
        Optional<Revision<Long, History>> revision
                = historyDao.findLastChangeRevision(history2.getId());

        assertThat(revision).isPresent();
        assertThat(revision.orElseThrow().getEntity()).isEqualTo(update2);

    }

    @Test
    @Order(7)
    void getLastAuditByIdFail() {
        Optional<Revision<Long, History>> revision
                = historyDao.findLastChangeRevision(anyLong());

        assertThat(revision).isEmpty();
    }

    @Test
    @Order(8)
    void deleteSuccess() {
        List<History> historyList = historyDao.findAll();

        List<Long> longList = historyList.stream().map(History::getId).toList();

        assertThat(longList).contains(history1.getId(), history2.getId(), history3.getId());

        long idOne = history1.getId();
        long idTwo = history2.getId();
        long idThree = history3.getId();

        historyDao.deleteById(idOne);
        historyDao.deleteById(idTwo);
        historyDao.deleteById(idThree);

        List<History> actualHistoryList = historyDao.findAll();

        List<Long> actualLongList = actualHistoryList.stream().map(History::getId).toList();

        assertThat(actualLongList).doesNotContain(idOne, idTwo, idThree);
    }

    @RepeatedTest(3)
    void deleteFail() {
        assertThrows(EmptyResultDataAccessException.class, () -> historyDao.deleteById(anyLong()));
    }
}