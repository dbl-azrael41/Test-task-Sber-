package com.task.crud.exceptions;

public class NoSuchPhoneException extends RuntimeException {
    public NoSuchPhoneException(String message) {
        super(message);
    }
}
