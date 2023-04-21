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
    void getAllFail() {
        doReturn(null).when(historyDao).findAll();
        List<History> historyServiceAll = historyService.getAll();
        assertThat(historyServiceAll).isNull();
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
        History test = HistoryTest.getHistory(4);
        doReturn(test).when(historyDao).save(test);
        History result = historyService.saveNew(test);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(test);
    }

    @RepeatedTest(3)
    void saveNewFail() {
        History test = HistoryTest.getHistory(4);
        doReturn(null).when(historyDao).save(any());
        History result = historyService.saveNew(test);
        assertThat(result).isNull();
    }

    @RepeatedTest(3)
    void deleteSuccess() {
        historyService.delete(1);
        verify(historyDao).deleteById(1L);
    }

    @RepeatedTest(3)
    void deleteFail() {
        historyService.delete(anyInt());
        verify(historyDao, never()).deleteById(1L);
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
