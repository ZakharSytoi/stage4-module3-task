package com.mjc.school.service.impl;

import com.mjc.school.repository.jparepos.AuthorRepository;
import com.mjc.school.repository.model.impl.AuthorModel;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ValidatorException;
import com.mjc.school.service.mappers.AuthorMapper;
import com.mjc.school.service.validation.validators.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.mjc.school.service.exceptions.ServiceErrorCode.AUTHOR_DOES_NOT_EXIST_FOR_NEWS_ID;
import static com.mjc.school.service.exceptions.ServiceErrorCode.AUTHOR_ID_DOES_NOT_EXIST;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorMapper authorMapper = AuthorMapper.INSTANCE;

    private final AuthorRepository jpaAuthorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository jpaAuthorRepository) {
        this.jpaAuthorRepository = jpaAuthorRepository;
    }

    @Override
    @Transactional
    public AuthorDtoResponse readByNewsId(Long newsId) {
        return authorMapper.authorModelToAuthorDtoResponse(
                jpaAuthorRepository.getByNewsId(newsId).orElseThrow(() ->
                        new NotFoundException(String.format(AUTHOR_DOES_NOT_EXIST_FOR_NEWS_ID.getMessage(), newsId))
                )
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuthorDtoResponse> readAll(Pageable pageRequest) {
        return jpaAuthorRepository.findAll(pageRequest).map(authorMapper::authorModelToAuthorDtoResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorDtoResponse readById(Long id) {
        return authorMapper.authorModelToAuthorDtoResponse(
                jpaAuthorRepository.findById(id)
                        .orElseThrow(() -> new ValidatorException(String.format(
                                AUTHOR_ID_DOES_NOT_EXIST.getMessage(),
                                id
                        ))));
    }

    @Override
    @Transactional
    public AuthorDtoResponse create(AuthorDtoRequest createRequest) {
        RequestValidator.validateDTORequestForCreate(createRequest);
        return authorMapper.authorModelToAuthorDtoResponse(jpaAuthorRepository
                .save(authorMapper
                        .authorModelDtoToAuthorModel(createRequest)));
    }

    @Override
    @Transactional
    public AuthorDtoResponse update(AuthorDtoRequest updateRequest) {
        RequestValidator.validateDTORequestForUpdate(updateRequest);
        if (jpaAuthorRepository.existsById(updateRequest.id())) {
            AuthorModel authorRef = jpaAuthorRepository.findById(updateRequest.id()).get();

            authorMapper.updateAuthorModelFromDto(authorRef, updateRequest);

            return authorMapper.authorModelToAuthorDtoResponse(
                    jpaAuthorRepository.save(authorRef));
        } else {
            throw new NotFoundException(String.format(AUTHOR_ID_DOES_NOT_EXIST.getMessage(), updateRequest.id()));
        }

    }

    @Override
    @Transactional
    public Boolean deleteById(Long id) {
        if (jpaAuthorRepository.existsById(id)) {
            jpaAuthorRepository.deleteById(id);
            return true;
        } else {
            throw new NotFoundException(String.format(AUTHOR_ID_DOES_NOT_EXIST.getMessage(), id));
        }
    }
}
