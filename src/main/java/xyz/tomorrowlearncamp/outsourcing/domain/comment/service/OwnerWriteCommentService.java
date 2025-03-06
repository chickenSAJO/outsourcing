package xyz.tomorrowlearncamp.outsourcing.domain.comment.service;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import xyz.tomorrowlearncamp.outsourcing.domain.comment.entity.CommentEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.comment.repository.CommentRepository;
import xyz.tomorrowlearncamp.outsourcing.domain.comment.enums.ErrorCommentMessage;
import xyz.tomorrowlearncamp.outsourcing.domain.review.entity.ReviewEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.review.service.UserReadReviewService;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.user.service.UserService;
import xyz.tomorrowlearncamp.outsourcing.global.exception.InvalidRequestException;

@Service
@RequiredArgsConstructor
@Validated
public class OwnerWriteCommentService {

    private final CommentRepository commentRepository;

    private final UserService userService;

    private final UserReadReviewService reviewService;

    @Transactional
    public void saveComment(Long userId, @NotNull @Positive Long reviewId, @NotNull @Size(min = 1, max = 100) String comment) {

        UserEntity user = userService.getUserEntity(userId);

        ReviewEntity review = reviewService.findReviewEntity(reviewId);

        CommentEntity commentEntity = new CommentEntity(user, review, comment);

        commentRepository.save(commentEntity);
    }

    @Transactional
    public void deleteComment(Long userId, @NotNull @Positive Long commentId) {
        CommentEntity deleteComment = commentRepository.findById(commentId).orElseThrow(
                () -> new InvalidRequestException(ErrorCommentMessage.NOT_FOUND_COMMENT.getErrorMessage())
        );

        if (!ObjectUtils.nullSafeEquals(deleteComment.getUser().getId(), userId)) {
            throw new InvalidRequestException(ErrorCommentMessage.UNAUTHORIZED.getErrorMessage());
        }

        commentRepository.delete(deleteComment);
    }
}
