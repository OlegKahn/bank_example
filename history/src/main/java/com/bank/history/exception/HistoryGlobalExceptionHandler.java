package com.bank.history.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Exception Handler class - designed to catch Exceptions
 */
@Slf4j
@ControllerAdvice
public class HistoryGlobalExceptionHandler {

    /**
     * The method catches NoSuchHistoryException
     * @param e is itself NoSuchHistoryException
     * @return ResponseEntity<HistoryIncorrectData>
     */
    @ExceptionHandler
    public ResponseEntity<HistoryIncorrectData> handlerException(NoSuchHistoryException e) {
        final HistoryIncorrectData incorrectData = new HistoryIncorrectData();
        incorrectData.setInfo(e.getMessage());
        log.error(e.getMessage());
        return new ResponseEntity<>(incorrectData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<HistoryIncorrectData> handlerException(Exception e) {
        final HistoryIncorrectData incorrectData = new HistoryIncorrectData();
        incorrectData.setInfo(e.getMessage());
        log.error(e.getMessage());
        return new ResponseEntity<>(incorrectData, HttpStatus.BAD_REQUEST);
    }
}
