package com.mjc.school.service.mappers;

import com.mjc.school.repository.model.impl.TagModel;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-01T15:33:20+0200",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.2.jar, environment: Java 17.0.6 (Oracle Corporation)"
)
@Component
public class TagMapperImpl implements TagMapper {

    @Override
    public List<TagDtoResponse> tagModelListToTagDtoResponseList(List<TagModel> tagModelList) {
        if ( tagModelList == null ) {
            return null;
        }

        List<TagDtoResponse> list = new ArrayList<TagDtoResponse>( tagModelList.size() );
        for ( TagModel tagModel : tagModelList ) {
            list.add( tagModelToTagDtoResponse( tagModel ) );
        }

        return list;
    }

    @Override
    public TagDtoResponse tagModelToTagDtoResponse(TagModel tagModel) {
        if ( tagModel == null ) {
            return null;
        }

        Long id = null;
        String name = null;

        id = tagModel.getId();
        name = tagModel.getName();

        TagDtoResponse tagDtoResponse = new TagDtoResponse( id, name );

        return tagDtoResponse;
    }

    @Override
    public TagModel tagModelDtoToTagModel(TagDtoRequest TagDtoRequest) {
        if ( TagDtoRequest == null ) {
            return null;
        }

        TagModel tagModel = new TagModel();

        tagModel.setId( TagDtoRequest.id() );
        tagModel.setName( TagDtoRequest.name() );

        return tagModel;
    }

    @Override
    public TagModel updateTagModelFromDtoTagModel(TagModel tagModel, TagDtoRequest dtoRequest) {
        if ( dtoRequest == null ) {
            return null;
        }

        if ( dtoRequest.id() != null ) {
            tagModel.setId( dtoRequest.id() );
        }
        if ( dtoRequest.name() != null ) {
            tagModel.setName( dtoRequest.name() );
        }

        return tagModel;
    }
}
