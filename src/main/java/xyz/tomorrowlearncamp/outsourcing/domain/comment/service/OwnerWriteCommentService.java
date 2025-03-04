package xyz.tomorrowlearncamp.outsourcing.domain.comment.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.tomorrowlearncamp.outsourcing.domain.comment.dto.request.WriteCommentRequestDto;
import xyz.tomorrowlearncamp.outsourcing.domain.comment.entity.CommentEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.comment.repository.CommentRepository;
import xyz.tomorrowlearncamp.outsourcing.domain.enums.CommentErrorMessage;
import xyz.tomorrowlearncamp.outsourcing.domain.review.entity.ReviewEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.review.service.UserReadReviewService;
import xyz.tomorrowlearncamp.outsourcing.global.exception.InvalidRequestException;

@Service
@RequiredArgsConstructor
@Validated
public class OwnerWriteCommentService {

    private final CommentRepository commentRepository;

    private final UserReadReviewService reviewService;

    @Transactional
    public void saveComment(@NotNull @Positive Long reviewId, @NotNull @Size(min = 1, max = 100) String comment) {

        ReviewEntity review = reviewService.findReview(reviewId);

        CommentEntity commentEntity = new CommentEntity(review, comment);

        commentRepository.save(commentEntity);
    }

    @Transactional
    public void deleteComment(@NotNull @Positive Long commentId) {
        CommentEntity deleteComment = commentRepository.findById(commentId).orElseThrow(
                () -> new InvalidRequestException(CommentErrorMessage.NOT_FOUND_COMMENT.getErrorMessage())
        );

        commentRepository.delete(deleteComment);
    }
}
