package org.telran.bankproject.com.exceptions;

public class NotEnoughFieldsAreFilledException extends RuntimeException {

    public NotEnoughFieldsAreFilledException(String message) {
        super(message);
    }
}