package com.mjc.school.service.dto.securiryDtos;


import com.mjc.school.repository.model.impl.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User userDtoRequestToUser(RegistrationUserDtoRequest dto);

}
