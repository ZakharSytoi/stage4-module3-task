package com.mjc.school.repository.jparepos.customRepositories;

import com.mjc.school.repository.jparepos.customRepositories.CustomCommentRepository;
import com.mjc.school.repository.model.impl.CommentModel;
import com.mjc.school.repository.model.impl.NewsModel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class CustomCommentRepositoryImpl implements CustomCommentRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<CommentModel> getCommentsByNewsId(Long newsId) {
        NewsModel news = entityManager.find(NewsModel.class, newsId);
        if (news == null) return null;
        return news.getComments();
    }
}
