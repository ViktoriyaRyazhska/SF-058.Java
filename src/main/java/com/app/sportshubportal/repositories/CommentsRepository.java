package com.app.sportshubportal.repositories;

import com.app.sportshubportal.entities.Article;
import com.app.sportshubportal.entities.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends CrudRepository<Comment, Long> {
    List<Comment> getCommentsByArticleId(Long id);
    Comment getCommentById(Long id);
    int deleteCommentById(Long id);
}
