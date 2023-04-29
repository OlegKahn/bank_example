package com.bank.history.controller;

import com.bank.history.dto.HistoryDto;
import com.bank.history.exception.NoSuchHistoryException;
import com.bank.history.entity.History;
import com.bank.history.entity.HistoryTest;
import com.bank.history.service.HistoryService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HistoryControllerTest {

    @Mock
    ModelMapper modelMapper;

    @Mock
    HistoryService historyService;

    @InjectMocks
    HistoryController historyController;


    @Test
    void getAllSuccess() {
        doReturn(HistoryTest.getHistories()).when(historyService).getAll();
        List<HistoryDto> historyControllerAll = historyController.getAll();
        assertThat(historyControllerAll).isNotEmpty();
        assertThat(historyControllerAll.size()).isEqualTo(3);
    }

    @Test
    void getAllFail() {
        doReturn(null).when(historyService).getAll();
        assertThrows(NullPointerException.class, () -> historyController.getAll());
    }

    @Test
    void getByIdSuccess() {
        History responseHistory = HistoryTest.getHistory(4);
        doReturn(responseHistory).when(historyService).getById(4);
        HistoryDto historyDto = HistoryTest.getHistoryDto(4);
        doReturn(historyDto).when(modelMapper).map(responseHistory, HistoryDto.class);
        ResponseEntity<HistoryDto> byId = historyController.getById(4);
        assertThat(byId).isNotNull();
        assertThat(byId.getBody()).isEqualTo(historyDto);
    }

    @Test
    void getByIdFail() {
        doThrow(new NoSuchHistoryException("test")).when(historyService).getById(anyLong());
        assertThrows(NoSuchHistoryException.class, () -> historyController.getById(1000L));
    }

    @Test
    void saveNew() {
        HistoryDto historyDto = HistoryTest.getHistoryDto(111);
        History history = HistoryTest.getHistory(111);

        doReturn(history).when(modelMapper).map(historyDto, History.class);
        doReturn(history).when(historyService).saveNew(history);
        doReturn(historyDto).when(modelMapper).map(history, HistoryDto.class);

        ResponseEntity<HistoryDto> responseEntity = historyController.saveNew(historyDto);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getBody()).isEqualTo(historyDto);
    }

    @Test
    void saveOld() {
        HistoryDto historyDto = HistoryTest.getHistoryDto(111);
        History history = HistoryTest.getHistory(111);

        doReturn(history).when(modelMapper).map(historyDto, History.class);
        doReturn(history).when(historyService).saveOld(history);
        doReturn(historyDto).when(modelMapper).map(history, HistoryDto.class);

        ResponseEntity<HistoryDto> responseEntity = historyController.saveOld(historyDto);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getBody()).isEqualTo(historyDto);
    }

    @Test
    void deleteSuccess() {
        historyController.delete(123);
        verify(historyService).delete(123);
    }

    @Test
    void deleteFail() {
        historyController.delete(anyLong());
        verify(historyService, never()).delete(123);
    }
}
