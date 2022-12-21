package com.dag.customer.exception;

import java.net.UnknownHostException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dag.customer.model.ApiExceptionResponse;

/**
 *
 * Standard http communication have five levels of response codes
 *
 * 100-level (Informational) — Server acknowledges a request, it mean that
 * request was received and understood, it is transient response , alert client
 * for awaiting response
 * 200-level (Success) — Server completed the request as expected
 * 300-level (Redirection) — Client needs to perform further actions to complete
 * the request
 * 400-level (Client error) — Client sent an invalid request
 * 500-level (Server error) — Server failed to fulfill a valid request due to an
 * error with server
 * 
 * The goal of handler exception is provide to customer with appropriate code
 * and additional comprehensible information to help troubleshoot the problem.
 * The message portion of the body should be present as user interface, event if
 * customer send an Accept-Language header (en or french ie) we should translate
 * the title part to customer language if we support internationalization,
 * detail is intended for developer of clients, so the translation is not
 * necessary. If more than one error is need to report , we can response a list
 * of errors.
 * 
 */
@RestControllerAdvice // Indicate that this class assist a controller class and can have a body in response
public class ApiExceptionHandler {

    @ExceptionHandler(UnknownHostException.class)
    public ResponseEntity<ApiExceptionResponse> handleUnknownHostException(
            UnknownHostException ex) {
        ApiExceptionResponse response = new ApiExceptionResponse(
                "Connection error", "error-1024", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.PARTIAL_CONTENT);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ApiExceptionResponse> handleBusinessRuleException(
            BusinessRuleException ex) {
        ApiExceptionResponse response = new ApiExceptionResponse(
                "Validation error", ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.PARTIAL_CONTENT);
    }

}