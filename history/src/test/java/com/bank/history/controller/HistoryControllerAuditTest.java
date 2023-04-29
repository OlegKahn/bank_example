package com.bank.history.controller;

import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.History;
import com.bank.history.entity.HistoryTest;
import com.bank.history.service.HistoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.history.RevisionMetadata;
import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class HistoryControllerAuditTest {

    @Mock
    HistoryService historyService;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    HistoryControllerAudit historyControllerAudit;

    @Test
    void getAllAuditById() {
        HashMap<RevisionMetadata<Long>, History> revisions = new HashMap<>();
        History history = HistoryTest.getHistory(300);
        HistoryDto historyDto = HistoryTest.getHistoryDto(300);
        revisions.put(null, history);

        doReturn(revisions).when(historyService).getAllAuditById(300);
        doReturn(historyDto).when(modelMapper).map(history, HistoryDto.class);

        Map<RevisionMetadata<Long>, HistoryDto> allAuditById
                = historyControllerAudit.getAllAuditById(300);

        assertThat(allAuditById).isNotNull();
        assertThat(allAuditById.get(null)).isEqualTo(historyDto);
    }

    @Test
    void getLastAuditByIdSuccess() {
        HashMap<RevisionMetadata<Long>, History> revision = new HashMap<>();
        History history = HistoryTest.getHistory(300);
        HistoryDto historyDto = HistoryTest.getHistoryDto(300);
        revision.put(null, history);

        doReturn(revision).when(historyService).getLastAuditById(400);
        doReturn(historyDto).when(modelMapper).map(history, HistoryDto.class);

        Map<RevisionMetadata<Long>, HistoryDto> lastAuditById
                = historyControllerAudit.getLastAuditById(400);

        assertThat(lastAuditById).isNotNull();
        assertThat(lastAuditById.get(null)).isEqualTo(historyDto);
    }

    @Test
    void getLastAuditByIdFail() {
        doReturn(null).when(historyService).getLastAuditById(500);

         assertThrows(NullPointerException.class,
                 () -> historyControllerAudit.getLastAuditById(500));
    }
}
