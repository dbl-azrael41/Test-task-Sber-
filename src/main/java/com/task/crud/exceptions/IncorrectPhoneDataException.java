package com.task.crud.exceptions;

public class IncorrectPhoneDataException extends RuntimeException {
    public IncorrectPhoneDataException(String message) {
        super(message);
    }
}
