package com.mjc.school.repository.jparepos.customRepositories;


import com.mjc.school.repository.model.impl.TagModel;

import java.util.List;

public interface CustomTagRepository {
    List<TagModel> getByNewsId(Long newsId);
}
