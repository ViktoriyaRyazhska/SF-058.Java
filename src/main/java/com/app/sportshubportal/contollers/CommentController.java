package com.app.sportshubportal.contollers;

import com.app.sportshubportal.dto.CommentDTO;
import com.app.sportshubportal.entities.Comment;
import com.app.sportshubportal.entities.User;
import com.app.sportshubportal.services.CommentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/articles/{article-id}/comments")
public class CommentController {
    private CommentService commentService;
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDTO> getAll(@PathVariable("article-id") Long articleId) {    // tested for article
        List<Comment> comments = commentService.getArticleComments(articleId);
        return comments.stream()
                .map(this::convertToDTO)
                .toList();
    }

    @GetMapping("/{comment-id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDTO getComment(@PathVariable("article-id") Long articleId,
                                 @PathVariable("comment-id") Long commentId) {
        Comment comment = commentService.getArticleComment(articleId, commentId);
        return convertToDTO(comment);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDTO addComment(@PathVariable("article-id") Long articleId,
                                 @AuthenticationPrincipal User user,
                                 @RequestBody CommentDTO newComment) {
        var comment = commentService.add(articleId, user, newComment);
        return convertToDTO(comment);
    }

    @PatchMapping("/{comment-id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDTO updateComment(@PathVariable("comment-id") Long commentId,
                                    @AuthenticationPrincipal User user,
                                    @RequestBody CommentDTO newComment) {
        Comment updatedComment = commentService.update(commentId, user, newComment);
        return convertToDTO(updatedComment);
    }

    @DeleteMapping("/{comment-id}")
    public ResponseEntity<?> deleteComment(@PathVariable("comment-id") Long commentId,
                                           @AuthenticationPrincipal User user) {
        commentService.deleteComment(commentId, user);
        return ResponseEntity.noContent().build();
    }

    private CommentDTO convertToDTO(Comment comment) {
        CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
        commentDTO.setText(comment.getText());
        commentDTO.setId(comment.getId());
        commentDTO.setCreatedAt(comment.getCreatedAt());
        commentDTO.setUpdatedAt(comment.getUpdatedAt());
        return commentDTO;
    }
}
