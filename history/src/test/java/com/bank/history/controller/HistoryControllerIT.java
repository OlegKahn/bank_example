package com.bank.history.controller;

import com.bank.history.dto.HistoryDto;
import com.bank.history.exception.NoSuchHistoryException;
import com.bank.history.entity.History;
import com.bank.history.entity.HistoryTest;
import com.bank.history.repository.HistoryDao;
import com.bank.history.service.HistoryService;
import com.bank.history.service.HistoryServiceImpl;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.history.RevisionMetadata;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HistoryControllerIT {

    ModelMapper modelMapper;

    HistoryController historyController;

    HistoryControllerAudit historyControllerAudit;

    HistoryDto historyDtoTest;


    @Autowired
    HistoryControllerIT(ModelMapper modelMapper, HistoryDao historyDao,
                        MeterRegistry registry, AtomicInteger atomic) {
        this.modelMapper = modelMapper;
        HistoryService historyService = new HistoryServiceImpl(historyDao, registry, atomic);
        this.historyController = new HistoryController(modelMapper, historyService);
        this.historyControllerAudit = new HistoryControllerAudit(historyService, modelMapper);
    }


    @Test
    @Order(1)
    void saveNew() {
        History history = HistoryTest.getHistoryWithoutId(100);
        HistoryDto historyDto = modelMapper.map(history, HistoryDto.class);

        assertThat(history.getId()).isEqualTo(0);

        ResponseEntity<HistoryDto> entity
                = historyController.saveNew(historyDto);

        assertThat(entity.getBody()).isNotNull();
        assertThat(entity.getBody().getId()).isGreaterThan(0);

        historyDtoTest = entity.getBody();
    }

    @Test
    @Order(2)
    void getById() {
        long id = historyDtoTest.getId();

        ResponseEntity<HistoryDto> response = historyController.getById(id);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(historyDtoTest);
    }

    @Test
    @Order(3)
    void update() {
        historyDtoTest.setAccountAuditId(1000);

        ResponseEntity<HistoryDto> old = historyController.saveOld(historyDtoTest);

        assertThat(old).isNotNull();
        assertThat(old.getBody()).isEqualTo(historyDtoTest);
    }

    @Test
    @Order(4)
    void getLastAuditById() {
        Map<RevisionMetadata<Long>, HistoryDto> lastAuditById
                = historyControllerAudit.getLastAuditById(historyDtoTest.getId());

        assertThat(lastAuditById).isNotNull();
        assertThat(lastAuditById.containsValue(historyDtoTest)).isTrue();
    }

    @Test
    @Order(5)
    void delete() {
        long id = historyDtoTest.getId();

        historyController.delete(id);

        assertThrows(NoSuchHistoryException.class, () -> historyController.getById(id));
    }
}
