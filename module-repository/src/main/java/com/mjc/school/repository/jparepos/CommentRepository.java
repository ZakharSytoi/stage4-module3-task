package com.mjc.school.repository.jparepos;

import com.mjc.school.repository.jparepos.customRepositories.CustomCommentRepository;
import com.mjc.school.repository.model.impl.CommentModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentModel, Long>, CustomCommentRepository {
}
