package com.bank.history.exception;

// exception for events, when client tried to get history by id, but such history does not exist
public class NoSuchHistoryException extends RuntimeException{

    public NoSuchHistoryException(String message) {
        super(message);
    }
}
