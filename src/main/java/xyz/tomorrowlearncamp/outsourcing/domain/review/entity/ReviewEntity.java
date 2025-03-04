package xyz.tomorrowlearncamp.outsourcing.domain.review.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.tomorrowlearncamp.outsourcing.domain.review.dto.response.ReadReviewResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.review.repository.ReviewRepository;
import xyz.tomorrowlearncamp.outsourcing.global.entity.BaseEntity;

@Getter
@Entity
@NoArgsConstructor
public class ReviewEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // todo : Entity 연결하기
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private UserEntity user;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "order_id")
//    private OrderEntity order;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "store_id")
//    private StoreEntity store;

    // temp
    private Long storeId;

    @Column(nullable = false)
    private String contents;

    private Integer star;

    private String reviewImageUrl;

    @Builder
    public ReviewEntity(/*UserEntity user, OrderEntity order*/Long storeId, String contents, Integer star, @Nullable String reviewImageUrl) {
        this.storeId = storeId;
        this.contents = contents;
        this.star = star;
        this.reviewImageUrl = reviewImageUrl;
    }

    public ReadReviewResponseDto toDto() {
        return ReadReviewResponseDto.builder()
                .review(this)
                .build();
    }
}
