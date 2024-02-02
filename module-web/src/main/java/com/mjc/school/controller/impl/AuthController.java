package com.mjc.school.controller.impl;

import com.mjc.school.service.dto.securiryDtos.LoginUserDtoRequest;
import com.mjc.school.service.dto.securiryDtos.RegistrationUserDtoRequest;
import com.mjc.school.service.impl.AuthService;
import com.mjc.school.service.securiryDtos.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news-management-api/v1")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserDtoRequest loginUserDto){
        return ResponseEntity.ok(new JwtResponse(authService.authenticateUser(loginUserDto)));
    }
    @PostMapping("/registration")
    public ResponseEntity<?> register(@RequestBody RegistrationUserDtoRequest userDtoRequest) {
        authService.registerUser(userDtoRequest);
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();
    }
}
