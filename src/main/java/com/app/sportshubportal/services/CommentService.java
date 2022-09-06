package com.app.sportshubportal.services;

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

//        TODO: check if user is logged-in?

        return commentsRepository.getCommentsByArticleId(articleId);
    }

    public Comment getArticleComment(Long articleId, Long commentId) {
        return commentsRepository.getCommentById(commentId);
    }

    public Comment addComment(Long id, User user, String comment) {
        //        TODO: add if user is logged-in
        //              time
        //              id generation
        //              error if smth doesn't exist

        Article article = articleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Comment commentCreated = new Comment(comment, article, user);   // user ? test it
        return commentsRepository.save(commentCreated);
    }

    public Comment editComment(Long articleId, User user, Long commentId, String newContent) {
        Comment comment = commentsRepository.getCommentById(commentId);
        if (user != comment.getCreatedBy()) throw new ResponseStatusException(HttpStatus.FORBIDDEN);    // ?

        comment.setText(newContent);
        return commentsRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        if (commentsRepository.deleteCommentById(commentId) == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
