package com.mjc.school.service.dto.securiryDtos;

import com.mjc.school.repository.model.impl.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-01T15:34:15+0200",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.2.jar, environment: Java 17.0.6 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User userDtoRequestToUser(RegistrationUserDtoRequest dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setPassword( dto.password() );
        user.setEmail( dto.email() );
        user.setFirstName( dto.firstName() );
        user.setLastName( dto.lastName() );

        return user;
    }
}
