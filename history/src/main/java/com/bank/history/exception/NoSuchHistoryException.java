package com.bank.history.exception;

/**
 * NoSuchHistoryException - является классом Exception,
 * предназначенный для таких событий как попытка найти несуществующий History
 */
public class NoSuchHistoryException extends RuntimeException {

    public NoSuchHistoryException(String message) {
        super(message);
    }
}
