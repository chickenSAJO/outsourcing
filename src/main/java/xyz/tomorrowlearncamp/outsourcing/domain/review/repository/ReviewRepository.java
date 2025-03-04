package xyz.tomorrowlearncamp.outsourcing.domain.review.repository;

import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xyz.tomorrowlearncamp.outsourcing.domain.review.entity.ReviewEntity;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    @Query(
            "SELECT r FROM ReviewEntity r " +
                    "WHERE r.storeId = :storeId"
    )
    Page<ReviewEntity> findAllByStore_Id(@Positive Long storeId, Pageable pageable);

    @Query(
            "SELECT r FROM ReviewEntity r " +
                    "WHERE r.star BETWEEN :minstar AND :maxStar"
    )
    Page<ReviewEntity> findAllByStar(Integer minStar, Integer maxStar, Pageable pageable);
}
