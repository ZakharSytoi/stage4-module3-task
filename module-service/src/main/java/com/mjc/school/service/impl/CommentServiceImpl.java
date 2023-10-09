package com.mjc.school.service.impl;

import com.mjc.school.repository.jparepos.customRepositories.CustomCommentRepository;
import com.mjc.school.repository.model.impl.CommentModel;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ValidatorException;
import com.mjc.school.service.mappers.CommentMapper;
import com.mjc.school.service.validation.validators.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mjc.school.service.exceptions.ServiceErrorCode.COMMENT_ID_DOES_NOT_EXIST;

@Service
public class CommentServiceImpl implements CommentService {
    private final com.mjc.school.repository.jparepos.CommentRepository jpaCommentRepository;

    private final CommentMapper commentMapper;

    @Autowired
    public CommentServiceImpl(CustomCommentRepository commentRepository,
                              com.mjc.school.repository.jparepos.CommentRepository jpaCommentRepository,
                              CommentMapper commentMapper) {
        this.jpaCommentRepository = jpaCommentRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommentDtoResponse> readAll(Pageable pageRequest) {
        return jpaCommentRepository.findAll(pageRequest).map(commentMapper::commentModelToCommentDtoResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDtoResponse readById(Long id) {
        return commentMapper
                .commentModelToCommentDtoResponse(jpaCommentRepository
                        .findById(id).orElseThrow(
                                () -> new ValidatorException(String.format(
                                        COMMENT_ID_DOES_NOT_EXIST.getMessage(),
                                        id
                                ))));
    }

    @Override
    @Transactional
    public CommentDtoResponse create(CommentDtoRequest createRequest) {
        RequestValidator.validateDTORequestForCreate(createRequest);
        return commentMapper
                .commentModelToCommentDtoResponse(jpaCommentRepository
                        .save(commentMapper
                                .commentDotRequestToCommentModel(createRequest)
                        )
                );
    }

    @Override
    @Transactional
    public CommentDtoResponse update(CommentDtoRequest updateRequest) {
        RequestValidator.validateDTORequestForUpdate(updateRequest);
        CommentModel commentModel = jpaCommentRepository.getReferenceById(updateRequest.id());
        commentMapper.updateCommentModelFromDto(commentModel, updateRequest);
        if (jpaCommentRepository.existsById(updateRequest.id())) {
            return commentMapper
                    .commentModelToCommentDtoResponse(jpaCommentRepository
                            .save(commentModel));
        } else {
            throw new NotFoundException(String.format(COMMENT_ID_DOES_NOT_EXIST.getMessage(), updateRequest.id()));
        }
    }

    @Override
    @Transactional
    public Boolean deleteById(Long id) {
        if (jpaCommentRepository.existsById(id)) {
            jpaCommentRepository.deleteById(id);
            return true;
        } else {
            throw new NotFoundException(String.format(COMMENT_ID_DOES_NOT_EXIST.getMessage(), id));
        }
    }


    @Override
    public List<CommentDtoResponse> readByNewsId(Long id) {
        return commentMapper.commentModelListToCommentDtoList(jpaCommentRepository.getCommentsByNewsId(id));
    }
}
