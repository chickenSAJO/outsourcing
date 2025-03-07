package xyz.tomorrowlearncamp.outsourcing.domain.comment.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import xyz.tomorrowlearncamp.outsourcing.domain.comment.entity.CommentEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.comment.enums.ErrorCommentMessage;
import xyz.tomorrowlearncamp.outsourcing.domain.comment.repository.CommentRepository;
import xyz.tomorrowlearncamp.outsourcing.domain.review.entity.ReviewEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.review.service.UserReadReviewService;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.user.service.UserService;
import xyz.tomorrowlearncamp.outsourcing.global.exception.InvalidRequestException;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerWriteCommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserReadReviewService reviewService;

    @Mock
    private UserService userService;

    @InjectMocks
    private OwnerWriteCommentService commentService;

    @Test
    void comment_저장() {
        //given
        Long userId = 1L;
        Long reviewId = 1L;
        String comment = "comment";

        UserEntity user = new UserEntity();
        ReflectionTestUtils.setField(user, "id", userId);

        ReviewEntity review = new ReviewEntity();
        ReflectionTestUtils.setField(review, "id", reviewId);

        given(userService.getUserEntity(anyLong())).willReturn(user);
        given(reviewService.findReviewEntity(anyLong())).willReturn(review);

        // when
        commentService.saveComment(userId, reviewId, comment);

        // then
        verify(commentRepository, times(1)).save(any(CommentEntity.class));
    }

    @Test
    void comment_삭제() {
        //given
        Long userId = 1L;
        Long commentId = 1L;

        UserEntity user = new UserEntity();
        ReflectionTestUtils.setField(user, "id", userId);

        CommentEntity comment = new CommentEntity();
        ReflectionTestUtils.setField(comment, "id", commentId);
        ReflectionTestUtils.setField(comment, "user", user);

        given(commentRepository.findById(anyLong())).willReturn(Optional.of(comment));

        // when
        commentService.deleteComment(userId, commentId);

        // then
        verify(commentRepository, times(1)).delete(any(CommentEntity.class));
    }

    @Test
    void comment_삭제_실패_없는comment() {
        //given
        Long userId = 1L;
        Long commentId = 1L;

        given(commentRepository.findById(anyLong())).willReturn(Optional.empty());

        // when && then
        InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                () -> commentService.deleteComment(userId, commentId));

        // then
        assertEquals(ErrorCommentMessage.NOT_FOUND_COMMENT.getErrorMessage(), exception.getMessage());
    }

    @Test
    void comment_삭제_실패_다른사용자() {
        //given
        Long user2 = 2L;
        Long userId = 1L;
        Long commentId = 1L;

        UserEntity user = new UserEntity();
        ReflectionTestUtils.setField(user, "id", userId);

        CommentEntity comment = new CommentEntity();
        ReflectionTestUtils.setField(comment, "id", commentId);
        ReflectionTestUtils.setField(comment, "user", user);

        given(commentRepository.findById(anyLong())).willReturn(Optional.of(comment));

        // when && then
        InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                () -> commentService.deleteComment(user2, commentId));

        // then
        assertEquals(ErrorCommentMessage.UNAUTHORIZED.getErrorMessage(), exception.getMessage());
    }
}