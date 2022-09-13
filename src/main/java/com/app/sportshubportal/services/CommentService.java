package com.app.sportshubportal.services;

import com.app.sportshubportal.dto.CommentDTO;
import com.app.sportshubportal.entities.Article;
import com.app.sportshubportal.entities.Comment;
import com.app.sportshubportal.entities.User;
import com.app.sportshubportal.exception.EntityNotFoundException;
import com.app.sportshubportal.repositories.ArticleRepository;
import com.app.sportshubportal.repositories.CommentsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class CommentService {
    private final CommentsRepository commentsRepository;
    private final ArticleRepository articleRepository;

    @Autowired
    public CommentService(CommentsRepository commentsRepository, ArticleRepository articleRepository) {
        this.commentsRepository = commentsRepository;
        this.articleRepository = articleRepository;
    }

    public List<Comment> getArticleComments(Long articleId) {
        if (!articleRepository.existsById(articleId)) {
            throw new EntityNotFoundException("Article not found");
        }
        return commentsRepository.getCommentsByArticleId(articleId);
    }

    public Comment getArticleComment(Long articleId, Long commentId) {
        Comment temp = commentsRepository.getCommentById(commentId);
        if(temp == null) {
            throw new EntityNotFoundException("Comment not found");
        }

        if (!Objects.equals(temp.getArticle().getId(), articleId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return commentsRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Comment add(Long id, User user, CommentDTO inputComment) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Comment commentCreated = new Comment(inputComment.getText(), article, user);
        return commentsRepository.save(commentCreated);
    }

    public Comment update(Long id, User user, CommentDTO inputComment) {
        Comment temp = commentsRepository.getCommentById(id);
        if (!Objects.equals(user.getId(), temp.getCreatedBy().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        temp.setText(inputComment.getText());
        return commentsRepository.save(temp);
    }

    @Transactional
    public void deleteComment(Long commentId, User user) {
        if (!commentsRepository.existsById(commentId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Comment temp = commentsRepository.getCommentById(commentId);
        if (!Objects.equals(user.getId(), temp.getCreatedBy().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        if (commentsRepository.deleteCommentById(commentId) == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
