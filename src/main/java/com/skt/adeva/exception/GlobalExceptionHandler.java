package com.skt.adeva.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Component
public class GlobalExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Global Exception handler for all exceptions.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handle(Exception exception) {
        String message = "Unable to process this request.";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        // general exception
        LOG.error("Exception: Unable to process this request. ", exception);
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorMessage(message);
        return new ResponseEntity<>(response, status);
    }
}
