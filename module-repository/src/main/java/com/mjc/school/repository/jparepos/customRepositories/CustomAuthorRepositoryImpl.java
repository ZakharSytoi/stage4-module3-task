package com.mjc.school.repository.jparepos.customRepositories;

import com.mjc.school.repository.jparepos.customRepositories.CustomAuthorRepository;
import com.mjc.school.repository.model.impl.AuthorModel;
import com.mjc.school.repository.model.impl.NewsModel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

public class CustomAuthorRepositoryImpl implements CustomAuthorRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<AuthorModel> getByNewsId(Long newsId) {
        NewsModel news = entityManager.find(NewsModel.class, newsId);
        if (news == null) return Optional.empty();
        return Optional.ofNullable(news.getAuthor());
    }

}
