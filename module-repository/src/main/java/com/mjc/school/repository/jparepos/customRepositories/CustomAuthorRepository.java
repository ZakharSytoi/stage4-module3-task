package com.mjc.school.repository.jparepos.customRepositories;


import com.mjc.school.repository.model.impl.AuthorModel;

import java.util.Optional;

public interface CustomAuthorRepository {
    Optional<AuthorModel> getByNewsId(Long newsId);
}
