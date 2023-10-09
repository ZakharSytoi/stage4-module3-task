package com.mjc.school.service.mappers;

import com.mjc.school.repository.model.impl.AuthorModel;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    List<AuthorDtoResponse> authorModelListToAuthorDtoResponseList(List<AuthorModel> authorModelList);

    AuthorDtoResponse authorModelToAuthorDtoResponse(AuthorModel authorModel);

    @Mapping(target = "news", ignore = true)
    AuthorModel authorModelDtoToAuthorModel(AuthorDtoRequest authorDtoRequest);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AuthorModel updateAuthorModelFromDto(@MappingTarget AuthorModel authorModel,
                                         AuthorDtoRequest authorDtoRequest);
}
