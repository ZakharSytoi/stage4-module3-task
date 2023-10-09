package com.mjc.school.repository.jparepos;

import com.mjc.school.repository.jparepos.customRepositories.CustomTagRepository;
import com.mjc.school.repository.model.impl.TagModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<TagModel, Long>, CustomTagRepository {
}
