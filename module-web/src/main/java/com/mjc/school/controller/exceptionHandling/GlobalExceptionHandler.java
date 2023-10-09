package com.mjc.school.controller.exceptionHandling;

import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.UserAlreadyExistsException;
import com.mjc.school.service.exceptions.ValidatorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import static com.mjc.school.service.exceptions.ServiceErrorCode.*;
import static com.mjc.school.service.exceptions.ServiceErrorCode.UNEXPECTED_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = ValidatorException.class)
    protected ResponseEntity<ErrorResponse> handleValidationException(Exception exc) {
        return buildErrorResponse(VALIDATION.getErrorCode(), String.format(VALIDATION.getMessage(), exc.getMessage()), exc.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleResourceNotFoundException(Exception exc, WebRequest request) {
        return buildErrorResponse(RESOURCE_NOT_FOUND.getErrorCode(), RESOURCE_NOT_FOUND.getMessage(),
                exc.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = UserAlreadyExistsException.class)
    protected ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(Exception exc, WebRequest request) {
        return buildErrorResponse(REGISTRATION_ERROR.getErrorCode(), REGISTRATION_ERROR.getMessage(),
                exc.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<ErrorResponse> handleUnexpectedException(Exception exc) {
        return buildErrorResponse(UNEXPECTED_ERROR.getErrorCode(), UNEXPECTED_ERROR.getMessage(),
                exc.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(String code, String errorMessage,
                                                             String errorDetails, HttpStatus status) {
        return new ResponseEntity<>(new ErrorResponse(code, errorMessage, errorDetails), status);
    }
}
