package xyz.tomorrowlearncamp.outsourcing.domain.review.repository;

import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import xyz.tomorrowlearncamp.outsourcing.domain.review.dto.response.ReadReviewResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.review.entity.ReviewEntity;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    @Query(
            "SELECT new xyz.tomorrowlearncamp.outsourcing.domain.review.dto.response.ReadReviewResponseDto(r, c) " +
                    "FROM ReviewEntity r " +
                    "LEFT JOIN CommentEntity c ON r.id = c.review.id " +
                    "WHERE r.storeId = :storeId"
    )
    Page<ReadReviewResponseDto> findAllByStore_Id(@Positive Long storeId, Pageable pageable);

    @Query(
            "SELECT new xyz.tomorrowlearncamp.outsourcing.domain.review.dto.response.ReadReviewResponseDto(r, c) " +
                    "FROM ReviewEntity r " +
                    "LEFT JOIN CommentEntity c ON r.id = c.review.id " +
                    "WHERE r.star BETWEEN :minStar AND :maxStar"
    )
    Page<ReadReviewResponseDto> findAllByStar(Integer minStar, Integer maxStar, Pageable pageable);
}
