package com.mjc.school.repository.jparepos.customRepositories;

import com.mjc.school.repository.model.impl.NewsModel;
import com.mjc.school.repository.model.impl.TagModel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


public class CustomTagRepositoryImpl implements CustomTagRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<TagModel> getByNewsId(Long newsId) {
        NewsModel news = entityManager.find(NewsModel.class, newsId);
        if (news == null) return null;
        return news.getTags();
    }
}
