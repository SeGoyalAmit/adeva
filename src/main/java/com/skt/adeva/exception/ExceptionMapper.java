package com.skt.adeva.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionMapper {
    BOOK_NOT_EXISTS_EXCEPTION(HttpStatus.NOT_FOUND, "Provided book is not exists in our database.");

    HttpStatus httpStatus;
    String message;

    ExceptionMapper(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public static ExceptionMapper findException(String name) {
        for (ExceptionMapper mapper : values()) {
            if (mapper.name().equals(name)) {
                return mapper;
            }
        }
        return null;
    }
}
