package com.mjc.school.repository.jparepos;

import com.mjc.school.repository.jparepos.customRepositories.CustomAuthorRepository;
import com.mjc.school.repository.model.impl.AuthorModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<AuthorModel, Long>, CustomAuthorRepository {
}
