package com.jonhatfield.aidemo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final HttpHeaders httpHeaders;

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        log.info("JSON content type initialized");
    }

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException runtimeException, WebRequest request) {
        ErrorMapping errorMapping = ErrorMapping.getMapping(runtimeException.getClass().getName());

        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("errorCode", errorMapping.getCode());

        String errorMessage = runtimeException.getMessage();
        if(!errorMapping.isClientError()) {
            errorMessage += ". See log for details";
        }
        objectNode.put("errorMessage", errorMessage);

        HttpStatus httpStatus = errorMapping.isClientError() ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR;

        return handleExceptionInternal(runtimeException, objectNode.toString(), httpHeaders, httpStatus, request);
    }
}