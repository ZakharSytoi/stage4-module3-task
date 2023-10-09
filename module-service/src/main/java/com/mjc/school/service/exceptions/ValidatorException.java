package com.mjc.school.service.exceptions;

import java.util.Set;

public class ValidatorException extends RuntimeException {
    public ValidatorException(String message) {
        super(message);
    }

    private Set<String> errorMessages;

    public ValidatorException(Set<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    @Override
    public String getMessage() {
        return String.join("\n", errorMessages);
    }
}
