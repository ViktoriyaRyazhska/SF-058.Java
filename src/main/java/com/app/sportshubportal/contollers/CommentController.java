package com.app.sportshubportal.contollers;

import com.app.sportshubportal.entities.Comment;
import com.app.sportshubportal.entities.User;
import com.app.sportshubportal.services.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/articles")
public class CommentController {
    private CommentService commentService;

    @GetMapping("/{article-id}/comments")
    public List<Comment> getAll(@PathVariable("article-id") Long articleId) {    // tested for article
        return commentService.getArticleComments(articleId);
    }

    @GetMapping("/{article-id}/comments/{comment-id}")
    public Comment getComment(@PathVariable("article-id") Long articleId,
                              @PathVariable("comment-id") Long commentId) {
        return commentService.getArticleComment(articleId, commentId);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{article-id}/comments")
    public ResponseEntity<?> addComment(@PathVariable("article-id") Long articleId,
                                        @AuthenticationPrincipal User user,
                                        @RequestBody String content) {      // todo: create DTO comment
        var comment = commentService.add(articleId, user, content);

//        todo: response entity - generated comment entity
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(comment.getId())
                .toUri();
        return ResponseEntity.created(location).body(comment);
    }

    @PatchMapping("/{comment-id}")
    public ResponseEntity<?> updateComment(@PathVariable("comment-id") Long commentId,
                                           @AuthenticationPrincipal User user,
                                           @RequestBody Comment comment) {
        var updatedComment = commentService.update(commentId, user, comment);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{comment-id}")
                .buildAndExpand(updatedComment.getId())
                .toUri();
        return ResponseEntity.created(location).body(updatedComment);
    }

    @DeleteMapping("/{comment-id}")
    public ResponseEntity<?> deleteComment(@PathVariable("comment-id") Long commentId,
                                           @AuthenticationPrincipal User user) {
        commentService.deleteComment(commentId, user);
        return ResponseEntity.noContent().build();
    }
}
