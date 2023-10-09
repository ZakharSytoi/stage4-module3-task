package com.mjc.school.repository.jparepos.customRepositories;

import com.mjc.school.repository.model.impl.NewsModel;
import com.mjc.school.repository.query.NewsSearchQueryParams;

import java.util.List;

public interface CustomNewsRepository{
    List<NewsModel> findByParams(NewsSearchQueryParams searchQueryParams);
}
