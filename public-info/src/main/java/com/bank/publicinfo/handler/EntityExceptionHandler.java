package com.bank.publicinfo.handler;

import com.bank.publicinfo.exception.EntityErrorResponse;
import com.bank.publicinfo.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<EntityErrorResponse> handleException(EntityNotFoundException exception) {
        EntityErrorResponse data = new EntityErrorResponse();
        data.setMessage(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<EntityErrorResponse> handleException(Exception exception) {
        EntityErrorResponse data = new EntityErrorResponse();
        data.setMessage(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }

}
