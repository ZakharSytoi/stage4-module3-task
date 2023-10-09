package com.mjc.school.repository.jparepos;

import com.mjc.school.repository.jparepos.customRepositories.CustomNewsRepository;
import com.mjc.school.repository.model.impl.NewsModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<NewsModel, Long>, CustomNewsRepository {
}
