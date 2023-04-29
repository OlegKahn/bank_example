package com.bank.history.service;

import com.bank.history.exception.NoSuchHistoryException;
import com.bank.history.entity.History;
import com.bank.history.entity.HistoryTest;
import com.bank.history.repository.HistoryDao;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.history.Revision;
import org.springframework.data.history.RevisionMetadata;
import org.springframework.data.history.Revisions;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HistoryServiceTest {

    @Mock
    HistoryDao historyDao;

    @InjectMocks
    HistoryServiceImpl historyService;

    @MockBean
    RevisionMetadata<Long> revisionMetadata;

    @Test
    void getAllSuccess() {
        doReturn(HistoryTest.getHistories()).when(historyDao).findAll();
        List<History> historyServiceAll = historyService.getAll();
        assertThat(historyServiceAll).isNotEmpty();
        assertThat(historyServiceAll.size()).isEqualTo(3);
    }

    @Test
    void getAllFailWithNull() {
        doReturn(null).when(historyDao).findAll();
        assertThrows(NullPointerException.class,() -> historyService.getAll());
    }

    @Test
    void getAllFailWithEmpty() {
        ArrayList<History> histories = new ArrayList<>();
        doReturn(histories).when(historyDao).findAll();
        assertThat(histories).isEmpty();
        assertThrows(NoSuchHistoryException.class,() -> historyService.getAll());
    }

    @RepeatedTest(3)
    void getByIdSuccess() {
        Optional<History> optional = Optional.of(HistoryTest.getHistory(4));
        doReturn(optional).when(historyDao).findById(4L);
        History history = historyService.getById(4);
        assertThat(history).isNotNull();
        assertThat(history.getId()).isEqualTo(4);
    }

    @RepeatedTest(3)
    void getByIdFail() {
        doReturn(Optional.empty()).when(historyDao).findById(any());
        assertThrows(NoSuchHistoryException.class, () -> historyService.getById(4));
    }

    @RepeatedTest(3)
    void saveNewSuccess() {
        History test = HistoryTest.getHistoryWithoutId(4);
        assertThat(test.getId()).isEqualTo(0);
        assertThat(test.getAccountAuditId()).isGreaterThan(0);
        assertThat(test.getPublicBankInfoAuditId()).isGreaterThan(0);
        assertThat(test.getAntiFraudAuditId()).isGreaterThan(0);
        assertThat(test.getAuthorizationAuditId()).isGreaterThan(0);
        assertThat(test.getTransferAuditId()).isGreaterThan(0);
        assertThat(test.getProfileAuditId()).isGreaterThan(0);
        History testWithId = HistoryTest.getHistory(4);
        doReturn(testWithId).when(historyDao).save(test);
        History result = historyService.saveNew(test);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(testWithId);
    }

    @Test
    void saveNewFail() {
        History test = HistoryTest.getHistory(4);
        assertThat(test.getId()).isGreaterThan(0);
        assertThrows(NoSuchHistoryException.class,() -> historyService.saveNew(test));
    }

    @Test
    void saveNewFailWithValues() {
        History test = HistoryTest.getHistory(0);
        assertThat(test.getAccountAuditId()).isLessThan(1);
        assertThat(test.getPublicBankInfoAuditId()).isLessThan(1);
        assertThat(test.getAntiFraudAuditId()).isLessThan(1);
        assertThat(test.getAuthorizationAuditId()).isLessThan(1);
        assertThat(test.getTransferAuditId()).isLessThan(1);
        assertThat(test.getProfileAuditId()).isLessThan(1);
        assertThrows(NoSuchHistoryException.class,() -> historyService.saveNew(test));
    }

    @RepeatedTest(3)
    void saveOldSuccess() {
        History test = HistoryTest.getHistory(4);
        Optional<History> optional = Optional.of(test);
        doReturn(optional).when(historyDao).findById(4L);
        doReturn(test).when(historyDao).save(test);
        History result = historyService.saveOld(test);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(test);
    }

    @RepeatedTest(3)
    void saveOldFail() {
        History history = HistoryTest.getHistory(4000);
        assertThrows(NoSuchHistoryException.class, () -> historyService.saveOld(history));
    }

    @Test
    void saveOldFailWithValues() {
        History test = HistoryTest.getHistory(0);
        test.setId(1L);
        Optional<History> optional = Optional.of(test);
        assertThat(test.getAccountAuditId()).isLessThan(1);
        assertThat(test.getPublicBankInfoAuditId()).isLessThan(1);
        assertThat(test.getAntiFraudAuditId()).isLessThan(1);
        assertThat(test.getAuthorizationAuditId()).isLessThan(1);
        assertThat(test.getTransferAuditId()).isLessThan(1);
        assertThat(test.getProfileAuditId()).isLessThan(1);

        doReturn(optional).when(historyDao).findById(1L);

        assertThrows(NoSuchHistoryException.class,() -> historyService.saveOld(test));
    }

    @RepeatedTest(3)
    void deleteSuccess() {
        History history = HistoryTest.getHistory(1);
        Optional<History> optional = Optional.of(history);

        doReturn(optional).when(historyDao).findById(1L);

        historyService.delete(1);

        verify(historyDao).delete(history);
    }

    @RepeatedTest(3)
    void deleteFail() {
        assertThrows(NoSuchHistoryException.class, () -> historyService.delete(anyInt()));
    }

    @RepeatedTest(3)
    void getAllAuditById() {
        History history = HistoryTest.getHistory(1);
        List<Revision<Long, History>> list = new ArrayList<>();
        Revision<Long, History> revision = Revision.of(revisionMetadata, history);
        list.add(revision);
        Revisions<Long, History> revisions = Revisions.of(list);
        doReturn(revisions).when(historyDao).findRevisions(1L);
        Map<RevisionMetadata<Long>, History> allAuditById
                = historyService.getAllAuditById(1);

        assertThat(allAuditById).isNotNull();
        assertThat(allAuditById.containsValue(history)).isTrue();
    }

    @RepeatedTest(3)
    void getLastAuditByIdSuccess() {
        History history = HistoryTest.getHistory(1);
        Revision<Long, History> revision = Revision.of(revisionMetadata, history);

        doReturn(Optional.of(revision)).when(historyDao).findLastChangeRevision(1L);

        Map<RevisionMetadata<Long>, History> lastAuditById
                = historyService.getLastAuditById(1);

        assertThat(lastAuditById).isNotNull();
        assertThat(lastAuditById.containsValue(history)).isTrue();
    }

    @RepeatedTest(3)
    void getLastAuditByIdFail() {
        doReturn(Optional.empty()).when(historyDao).findLastChangeRevision(1L);

        assertThrows(NoSuchHistoryException.class, () -> historyService.getLastAuditById(1));
    }
}
