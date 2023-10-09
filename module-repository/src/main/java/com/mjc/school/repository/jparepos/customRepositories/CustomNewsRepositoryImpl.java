package com.mjc.school.repository.jparepos.customRepositories;

import com.mjc.school.repository.model.impl.AuthorModel;
import com.mjc.school.repository.model.impl.NewsModel;
import com.mjc.school.repository.model.impl.TagModel;
import com.mjc.school.repository.query.NewsSearchQueryParams;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.LinkedList;
import java.util.List;

public class CustomNewsRepositoryImpl implements CustomNewsRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<NewsModel> findByParams(NewsSearchQueryParams searchQueryParams) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<NewsModel> query = criteriaBuilder.createQuery(NewsModel.class);
        boolean atLeastOneNotNull = false;
        LinkedList<Predicate> predicates = new LinkedList<>();

        Root<NewsModel> root = query.from(NewsModel.class);

        if (searchQueryParams.authorName() != null && !searchQueryParams.authorName().isBlank()) {
            Join<NewsModel, AuthorModel> author = root.join("author");
            predicates.add(criteriaBuilder.equal(author.get("name"), searchQueryParams.authorName()));
            atLeastOneNotNull =true;

        }
        if( searchQueryParams.tagIds()!=null && searchQueryParams.tagNames()!=null){
            Join<NewsModel, TagModel> tag = root.join("tags");
            if(!searchQueryParams.tagIds().isEmpty()){
                predicates.add(tag.get("id").in(searchQueryParams.tagIds()));
                atLeastOneNotNull =true;
            }
            if(!searchQueryParams.tagNames().isEmpty()){
                predicates.add((tag.get("name").in(searchQueryParams.tagNames())));
                atLeastOneNotNull =true;
            }
        }
        if(searchQueryParams.title() != null && !searchQueryParams.title().isBlank()){
            predicates.add(criteriaBuilder.like(root.get("title"), searchQueryParams.title()));
            atLeastOneNotNull =true;
        }
        if(searchQueryParams.content() != null && !searchQueryParams.content().isBlank()){
            predicates.add(criteriaBuilder.like(root.get("content"), searchQueryParams.content()));
            atLeastOneNotNull =true;
        }
        if(atLeastOneNotNull){
            query.select(root).distinct(true).where(predicates.toArray(new Predicate[0]));
            return entityManager.createQuery(query).getResultList();
        }
        else return null;

    }
}
