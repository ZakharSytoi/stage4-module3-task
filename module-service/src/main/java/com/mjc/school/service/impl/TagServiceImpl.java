package com.mjc.school.service.impl;


import com.mjc.school.repository.model.impl.TagModel;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ValidatorException;
import com.mjc.school.service.mappers.TagMapper;
import com.mjc.school.service.validation.validators.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mjc.school.service.exceptions.ServiceErrorCode.TAG_ID_DOES_NOT_EXIST;

@Service
public class TagServiceImpl implements TagService {

    private final com.mjc.school.repository.jparepos.TagRepository jpaTagRepository;

    private final TagMapper tagMapper = TagMapper.INSTANCE;

    @Autowired
    public TagServiceImpl(com.mjc.school.repository.jparepos.TagRepository jpaTagRepository) {
        this.jpaTagRepository = jpaTagRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TagDtoResponse> readAll(Pageable pageRequest) {
        return jpaTagRepository.findAll(pageRequest).map(tagMapper::tagModelToTagDtoResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public TagDtoResponse readById(Long id) {
        return tagMapper
                .tagModelToTagDtoResponse(jpaTagRepository
                        .findById(id).orElseThrow(
                                () -> new ValidatorException(String.format(
                                        TAG_ID_DOES_NOT_EXIST.getMessage(),
                                        id
                                ))));
    }

    @Override
    @Transactional(readOnly = true)
    public TagDtoResponse create(TagDtoRequest createRequest) {
        RequestValidator.validateDTORequestForCreate(createRequest);
        return tagMapper
                .tagModelToTagDtoResponse(jpaTagRepository
                        .save(tagMapper
                                .tagModelDtoToTagModel(createRequest)
                        )
                );
    }

    @Override
    @Transactional
    public TagDtoResponse update(TagDtoRequest updateRequest) {
        RequestValidator.validateDTORequestForUpdate(updateRequest);
        if (jpaTagRepository.existsById(updateRequest.id())) {
            TagModel tagModel = jpaTagRepository.getReferenceById(updateRequest.id());
            tagMapper.updateTagModelFromDtoTagModel(tagModel, updateRequest);
            return tagMapper
                    .tagModelToTagDtoResponse(jpaTagRepository.save(tagModel));
        } else {
            throw new NotFoundException(String.format(TAG_ID_DOES_NOT_EXIST.getMessage(), updateRequest.id()));
        }
    }

    @Override
    @Transactional
    public Boolean deleteById(Long id) {
        if (jpaTagRepository.existsById(id)) {
            jpaTagRepository.deleteById(id);
            return true;
        } else {
            throw new NotFoundException(String.format(TAG_ID_DOES_NOT_EXIST.getMessage(), id));
        }
    }

    @Override
    @Transactional
    public List<TagDtoResponse> readByNewsId(Long newsId) {
        return tagMapper.tagModelListToTagDtoResponseList(jpaTagRepository.getByNewsId(newsId));
    }
}
