package com.jonhatfield.aidemo.exception;

public class MissingFieldException extends RuntimeException {

    public MissingFieldException(String fieldName) {
        super(String.format("Field '%s' is required", fieldName));
    }
}