package com.mjc.school.service.impl;

import com.mjc.school.repository.jparepos.RoleRepository;
import com.mjc.school.repository.model.impl.User;
import com.mjc.school.service.dto.securiryDtos.LoginUserDtoRequest;
import com.mjc.school.service.dto.securiryDtos.RegistrationUserDtoRequest;
import com.mjc.school.service.exceptions.UserAlreadyExistsException;
import com.mjc.school.service.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final com.mjc.school.service.impl.securityServices.UserService userService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    public String authenticateUser(LoginUserDtoRequest loginUserDtoRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDtoRequest.getUsername(),
                        loginUserDtoRequest.getPassword()
                )
        );
        UserDetails userDetails = userService.loadUserByUsername(
                loginUserDtoRequest.getUsername());
        return jwtUtils.generateJwtToken(userDetails);
    }
    public void registerUser(RegistrationUserDtoRequest registrationUserDtoRequest) throws UserAlreadyExistsException {
        if(userService.findByEmail(registrationUserDtoRequest.email()).isPresent()){
            throw new UserAlreadyExistsException(String.format("User with email %s already exists.", registrationUserDtoRequest.email()));
        }
        User user = new User();
        user.setEmail(registrationUserDtoRequest.email());
        user.setFirstName(registrationUserDtoRequest.firstName());
        user.setLastName(registrationUserDtoRequest.lastName());
        user.setRoles(Set.of(roleRepository.findByName("USER").get()));
        user.setPassword(passwordEncoder.encode(registrationUserDtoRequest.password()));
        userService.saveUser(user);
    }
}
