package com.mjc.school.repository.jparepos.customRepositories;

import com.mjc.school.repository.model.impl.CommentModel;

import java.util.List;

public interface CustomCommentRepository{
    List<CommentModel> getCommentsByNewsId(Long newsId);
}
