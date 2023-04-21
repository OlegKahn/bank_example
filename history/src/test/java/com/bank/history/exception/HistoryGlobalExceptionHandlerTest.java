package com.bank.history.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;

class HistoryGlobalExceptionHandlerTest {

    HistoryGlobalExceptionHandler exceptionHandler;


    @BeforeEach
    void initHandler() {
        exceptionHandler = new HistoryGlobalExceptionHandler();
    }

    @Test
    void handlerException() {
        NoSuchHistoryException exception = new NoSuchHistoryException("test");
        ResponseEntity<HistoryIncorrectData> response
                = exceptionHandler.handlerException(exception);
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getInfo()).isEqualTo(exception.getMessage());
    }

    @Test
    void testHandlerException() {
        Exception exception = new Exception("test 2");
        ResponseEntity<HistoryIncorrectData> response
                = exceptionHandler.handlerException(exception);
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getInfo()).isEqualTo(exception.getMessage());
    }
}