package com.mjc.school.service.dto.securiryDtos;

public record RegistrationUserDtoRequest(String firstName,
                                         String lastName,
                                         String email,
                                         String password) {
}
