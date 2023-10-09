package com.mjc.school.service.dto.securiryDtos;

import lombok.Data;

@Data
public class LoginUserDtoRequest {
    private String username;
    private String password;
}
