package com.dag.customer.exception;

import org.springframework.http.HttpStatus;

public class BusinessRuleException extends Exception {

    private final Long id;
    private final String code;
    private final HttpStatus httpStatus;

    public BusinessRuleException(String message, Throwable cause,
            Long id, String code, HttpStatus httpStatus) {
        super(message, cause);
        this.id = id;
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public BusinessRuleException(String message,
            Long id, String code, HttpStatus httpStatus) {
        super(message);
        this.id = id;
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public BusinessRuleException(String message,
            String code, HttpStatus httpStatus) {
        this(message, null, null, code, httpStatus);
    }

    public BusinessRuleException(String message, Throwable cause) {
        this(message, cause, null, null, null);
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
