package xyz.tomorrowlearncamp.outsourcing.domain.review.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;
import xyz.tomorrowlearncamp.outsourcing.domain.review.dto.response.ReadReviewResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.review.entity.ReviewEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.review.enums.ErrorReviewMessage;
import xyz.tomorrowlearncamp.outsourcing.domain.review.repository.ReviewRepository;
import xyz.tomorrowlearncamp.outsourcing.global.exception.InvalidRequestException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserReadReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private UserReadReviewService reviewService;

    @Test
    void review_조회_성공() {
        // given
        Long id = 1L;

        ReviewEntity review = new ReviewEntity();
        ReflectionTestUtils.setField(review, "id", id);

        given(reviewRepository.findById(anyLong())).willReturn(Optional.of(review));

        // when
        ReviewEntity getReview = reviewService.findReviewEntity(id);

        // then
        assertEquals(id, getReview.getId());
    }

    @Test
    void review_조회_실패() {
        // given
        Long id = 1L;

        given(reviewRepository.findById(anyLong())).willReturn(Optional.empty());

        // when && then
        InvalidRequestException exception = assertThrows(InvalidRequestException.class,
                () -> reviewService.findReviewEntity(id));

        // then
        assertEquals(ErrorReviewMessage.NOT_FOUND_REVIEW.getErrorMessage(), exception.getMessage());
    }


    @Test
    void review_전체_조회_storeId_없는경우() {
        // given
        Page<ReadReviewResponseDto> result = Page.empty();

        given(reviewRepository.findAllByStar(anyInt(), anyInt(), any(Pageable.class))).willReturn(result);

        // when
        // default 값을 넣어준 경우
        Page<ReadReviewResponseDto> getReviews = reviewService.getReviews(null, 1, 5, 1, 10);

        // then
        assertEquals(Page.empty(), getReviews);
        verify(reviewRepository, times(1)).findAllByStar(anyInt(), anyInt(), any(Pageable.class));
    }

    @Test
    void review_전체_조회_storeId_있는경우() {
        // given
        Long storeId = 1L;
        Page<ReadReviewResponseDto> result = Page.empty();

        given(reviewRepository.findAllByStore_Id(anyLong(), any(Pageable.class))).willReturn(result);

        // when
        // default 값을 넣어준 경우
        Page<ReadReviewResponseDto> getReviews = reviewService.getReviews(storeId, 1, 5, 1, 10);

        // then
        assertEquals(Page.empty(), getReviews);
        verify(reviewRepository, times(1)).findAllByStore_Id(anyLong(), any(Pageable.class));
    }

}