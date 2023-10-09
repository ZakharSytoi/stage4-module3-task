package com.mjc.school.service.mappers;

import com.mjc.school.repository.jparepos.AuthorRepository;
import com.mjc.school.repository.jparepos.TagRepository;
import com.mjc.school.repository.model.impl.NewsModel;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper (componentModel = "spring")
public abstract class NewsMapper {
    @Autowired
    protected AuthorRepository authorRepository;
    @Autowired
    protected TagRepository tagRepository;


    public abstract List<NewsDtoResponse> newsModelListToNewsDtoResponseList(List<NewsModel> newsModelList);

    @Mappings(value = {@Mapping(target = "authorId", source = "newsModel.author.id")})
    public abstract NewsDtoResponse newsModelToNewsDtoResponse(NewsModel newsModel);

    @Mappings(value = {
            @Mapping(target = "createdDate", ignore = true),
            @Mapping(target = "lastUpdatedDate", ignore = true),
            @Mapping(target = "author", expression = "java(authorRepository.getReferenceById(newsDtoRequest.authorId()))"),
            @Mapping(target = "tags", expression = "java(newsDtoRequest.tagIds().stream().map(id -> tagRepository.getReferenceById(id)).toList())"),
    })
    public abstract NewsModel newsDtoRequestToNewsModel(NewsDtoRequest newsDtoRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract NewsModel updateNewsModelFromDto(@MappingTarget NewsModel newsModel,
                                                     NewsDtoRequest dtoRequest);
}
