package xyz.tomorrowlearncamp.outsourcing.domain.comment.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import xyz.tomorrowlearncamp.outsourcing.domain.comment.entity.CommentEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.comment.repository.CommentRepository;
import xyz.tomorrowlearncamp.outsourcing.domain.review.entity.ReviewEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.review.service.UserReadReviewService;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerWriteCommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserReadReviewService reviewService;

    @InjectMocks
    private OwnerWriteCommentService commentService;

    @Test
    public void comment_저장한다() {
        //given
        Long reviewId = 1L;
        Long commentId = 1L;

        ReviewEntity review = new ReviewEntity();
        ReflectionTestUtils.setField(review, "id", reviewId);

        CommentEntity comment = new CommentEntity();
        ReflectionTestUtils.setField(comment, "id", commentId);

        given(reviewService.findReview(anyLong())).willReturn(review);
        given(commentRepository.save(any())).willReturn(comment);

        //when
//        when( commentService.saveComment() )
    }

}