package com.mjc.school.service.impl;

import com.mjc.school.repository.jparepos.AuthorRepository;
import com.mjc.school.repository.jparepos.customRepositories.CustomAuthorRepository;
import com.mjc.school.repository.jparepos.customRepositories.CustomNewsRepository;
import com.mjc.school.repository.jparepos.customRepositories.CustomTagRepository;
import com.mjc.school.repository.model.impl.NewsModel;
import com.mjc.school.repository.query.NewsSearchQueryParams;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.mappers.NewsMapper;
import com.mjc.school.service.query.NewsQueryParams;
import com.mjc.school.service.validation.validators.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mjc.school.service.exceptions.ServiceErrorCode.*;

@Service
public class NewsServiceImpl implements NewsService {
    private final com.mjc.school.repository.jparepos.NewsRepository jpaNewsRepository;
    private final AuthorRepository jpaAuthorRepository;
    private final com.mjc.school.repository.jparepos.TagRepository jpaTagRepository;
    private final CustomNewsRepository newsRepository;
    private final CustomAuthorRepository authorRepository;
    private final CustomTagRepository tagRepository;
    private final NewsMapper newsMapper;

    @Autowired
    public NewsServiceImpl(
            CustomNewsRepository newsRepository,
            com.mjc.school.repository.jparepos.NewsRepository jpaNewsRepository,
            AuthorRepository jpaAuthorRepository,
            com.mjc.school.repository.jparepos.TagRepository jpaTagRepository,
            CustomAuthorRepository authorRepository,
            CustomTagRepository tagRepository,
            NewsMapper newsMapper
    ) {
        this.newsRepository = newsRepository;
        this.jpaNewsRepository = jpaNewsRepository;
        this.jpaAuthorRepository = jpaAuthorRepository;
        this.jpaTagRepository = jpaTagRepository;
        this.authorRepository = authorRepository;
        this.tagRepository = tagRepository;
        this.newsMapper = newsMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NewsDtoResponse> readAll(Pageable pageRequest) {
        return jpaNewsRepository.findAll(pageRequest).map(newsMapper::newsModelToNewsDtoResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public NewsDtoResponse readById(Long id) {
        return newsMapper.newsModelToNewsDtoResponse(
                jpaNewsRepository.findById(id).orElseThrow(() -> new NotFoundException(
                        String.format(
                                NEWS_ID_DOES_NOT_EXIST.getMessage(),
                                id
                        ))
                ));
    }

    @Override
    @Transactional
    public NewsDtoResponse create(NewsDtoRequest createRequest) {
        RequestValidator.validateDTORequestForCreate(createRequest);
        if (!jpaAuthorRepository.existsById(createRequest.authorId())) {
            throw new NotFoundException(String.format(AUTHOR_ID_DOES_NOT_EXIST.getMessage(), createRequest.authorId()));
        }

        createRequest.tagIds().forEach((tagId) -> {
            if (!jpaTagRepository.existsById(tagId))
                throw new NotFoundException(String.format(TAG_ID_DOES_NOT_EXIST.getMessage(), tagId));
        });

        return newsMapper.newsModelToNewsDtoResponse(
                jpaNewsRepository
                        .save(newsMapper
                                .newsDtoRequestToNewsModel(createRequest))
        );
    }

    @Override
    @Transactional
    public NewsDtoResponse update(NewsDtoRequest updateRequest) {
        RequestValidator.validateDTORequestForUpdate(updateRequest);
        if (!jpaAuthorRepository.existsById(updateRequest.authorId())) {
            throw new NotFoundException(String.format(AUTHOR_ID_DOES_NOT_EXIST.getMessage(), updateRequest.authorId()));
        }

        if (!jpaNewsRepository.existsById(updateRequest.id())) {
            throw new NotFoundException(String.format(NEWS_ID_DOES_NOT_EXIST.getMessage(), updateRequest.id()));
        }

        updateRequest.tagIds().forEach((tagId) -> {
            if (!jpaTagRepository.existsById(tagId))
                throw new NotFoundException(String.format(TAG_ID_DOES_NOT_EXIST.getMessage(), tagId));
        });

        NewsModel newsModel = jpaNewsRepository.getReferenceById(updateRequest.id());
        newsMapper.updateNewsModelFromDto(newsModel, updateRequest);
        return newsMapper.newsModelToNewsDtoResponse(
                jpaNewsRepository.save(newsModel));
    }

    @Override
    @Transactional
    public Boolean deleteById(Long id) {
        if (jpaNewsRepository.existsById(id)) {
            jpaNewsRepository.deleteById(id);
            return true;
        } else {
            throw new NotFoundException(String.format(NEWS_ID_DOES_NOT_EXIST.getMessage(), id));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<NewsDtoResponse> readByQueryParams(NewsQueryParams queryParams) {
        NewsSearchQueryParams searchQueryParams = new NewsSearchQueryParams(
                queryParams.tagNames(),
                queryParams.tagIds(),
                queryParams.authorName(),
                queryParams.title(),
                queryParams.content());
        return newsMapper.newsModelListToNewsDtoResponseList(newsRepository.findByParams(searchQueryParams));
    }
}
