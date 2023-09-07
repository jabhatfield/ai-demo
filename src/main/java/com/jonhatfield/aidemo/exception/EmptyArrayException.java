package com.jonhatfield.aidemo.exception;

public class EmptyArrayException extends RuntimeException {

    public EmptyArrayException(String fieldName) {
        super(String.format("Array '%s' must be populated", fieldName));
    }
}