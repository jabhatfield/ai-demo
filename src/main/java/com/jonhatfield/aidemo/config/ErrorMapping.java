package com.jonhatfield.aidemo.config;

import com.jonhatfield.aidemo.exception.EmptyArrayException;
import com.jonhatfield.aidemo.exception.MissingFieldException;

import java.util.HashMap;
import java.util.Map;

public enum ErrorMapping {

    MISSING_FIELD(MissingFieldException.class.getName(), "001_MISSING_FIELD", true),
    EMPTY_ARRAY(EmptyArrayException.class.getName(), "002_EMPTY_ARRAY", true),
    UNKNOWN("UNKNOWN", "100_UNKNOWN_ERROR", false);//OPENNLP_ERROR_001

    private final String exceptionName;
    private final String code;
    private final boolean isClientError;

    private static final Map<String, ErrorMapping> CACHE_BY_EXCEPTION_NAME = new HashMap<>();
    static {
        for (ErrorMapping errorMapping : values()) {
            CACHE_BY_EXCEPTION_NAME.put(errorMapping.exceptionName, errorMapping);
        }
    }

    ErrorMapping(String exceptionName, String code, boolean isClientError) {
        this.exceptionName = exceptionName;
        this.code = code;
        this.isClientError = isClientError;
    }

    public static ErrorMapping getMapping(String exceptionName) {
        ErrorMapping errorMapping = CACHE_BY_EXCEPTION_NAME.get(exceptionName);
        return errorMapping != null ? errorMapping : ErrorMapping.UNKNOWN;
    }

    public String getCode() {
        return code;
    }

    public boolean isClientError() {
        return isClientError;
    }
}