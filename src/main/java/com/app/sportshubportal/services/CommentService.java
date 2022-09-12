package com.app.sportshubportal.services;

import com.app.sportshubportal.dto.CountDTO;
import com.app.sportshubportal.entities.Article;
import com.app.sportshubportal.entities.Comment;
import com.app.sportshubportal.entities.User;
import com.app.sportshubportal.exceptions.EntityNotFoundException;
import com.app.sportshubportal.repositories.ArticleRepository;
import com.app.sportshubportal.repositories.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

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
        return commentsRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
    }

    public CountDTO getCount() {
        int count = (int) commentsRepository.count();
        return new CountDTO(count);
    }

    public Comment add(Long id, User user, String comment) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Comment commentCreated = new Comment(comment, article, user);   // user ? test it
        return commentsRepository.save(commentCreated);
    }

    public Comment update(Long id, User user, Comment comment) {
        if (user != comment.getCreatedBy()) throw new ResponseStatusException(HttpStatus.FORBIDDEN);    // ?

        Comment temp = commentsRepository.getCommentById(id);
        temp.setText(comment.getText());
        return commentsRepository.save(temp);
    }

    @Transactional
    public void deleteComment(Long commentId, User user) {
        if (!commentsRepository.existsById(commentId)) throw new EntityNotFoundException("Comment not found");

        Comment temp = commentsRepository.getCommentById(commentId);
        if (user != temp.getCreatedBy()) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        if (commentsRepository.deleteCommentById(commentId) == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
