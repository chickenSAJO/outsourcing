package xyz.tomorrowlearncamp.outsourcing.domain.comment.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.tomorrowlearncamp.outsourcing.domain.comment.dto.request.WriteCommentRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.comment.service.OwnerWriteCommentService;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class OwnerWriteCommentController {

    private final OwnerWriteCommentService ownerWriteCommentService;


    @PostMapping("/{reviewId}")
    public ResponseEntity<Void> saveComment(
            /* todo : auth*/
            @NotNull @Positive @PathVariable Long reviewId,
            @Valid @RequestBody WriteCommentRequestDto requestDto
    ) {

        ownerWriteCommentService.saveComment(/*userId,*/reviewId, requestDto.getComment());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            /*auth*/
            @NotNull @Positive @PathVariable Long commentId
    ) {

        ownerWriteCommentService.deleteComment(/*userId,*/ commentId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
