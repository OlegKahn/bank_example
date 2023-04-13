package com.bank.history.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// exception handler which caught all exception
@ControllerAdvice
public class HistoryGlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<HistoryIncorrectData> handlerException(NoSuchHistoryException e) {
        HistoryIncorrectData incorrectData = new HistoryIncorrectData();
        incorrectData.setInfo(e.getMessage());
        return new ResponseEntity<>(incorrectData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<HistoryIncorrectData> handlerException(Exception e) {
        HistoryIncorrectData incorrectData = new HistoryIncorrectData();
        incorrectData.setInfo(e.getMessage());
        return new ResponseEntity<>(incorrectData, HttpStatus.BAD_REQUEST);
    }
}
