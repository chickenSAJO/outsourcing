package xyz.tomorrowlearncamp.outsourcing.domain.comment.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.tomorrowlearncamp.outsourcing.domain.comment.dto.request.WriteCommentRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.comment.service.CommentService;
import xyz.tomorrowlearncamp.outsourcing.auth.annotaion.Auth;
import xyz.tomorrowlearncamp.outsourcing.auth.dto.AuthUser;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/v1/comments/{reviewId}")
    public ResponseEntity<Void> saveComment(
            @Auth AuthUser user,
            @NotNull @Positive @PathVariable Long reviewId,
            @Valid @RequestBody WriteCommentRequestDto requestDto
    ) {
        commentService.saveComment(user.getId(), reviewId, requestDto.getComment());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/v1/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @Auth AuthUser user,
            @NotNull @Positive @PathVariable Long commentId
    ) {
        commentService.deleteComment(user.getId(), commentId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
