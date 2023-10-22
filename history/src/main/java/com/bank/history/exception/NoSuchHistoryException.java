package com.bank.history.exception;

/**
 * NoSuchHistoryException - is an Exception class,
 * intended for events such as trying to find a non-existent History
 */
public class NoSuchHistoryException extends RuntimeException {

    public NoSuchHistoryException(String message) {
        super(message);
    }
}
