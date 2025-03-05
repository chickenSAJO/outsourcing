package xyz.tomorrowlearncamp.outsourcing.domain.review.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.tomorrowlearncamp.outsourcing.domain.order.entity.UserOrderEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.review.dto.response.ReadReviewResponseDto;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;
import xyz.tomorrowlearncamp.outsourcing.global.entity.BaseEntity;

@Getter
@Entity
@NoArgsConstructor
public class ReviewEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    // todo : Entity 연결하기
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_entity_id")
    private UserOrderEntity orderEntity;

    @Column(nullable = false)
    private String contents;

    private Integer star;

    private String reviewImageUrl;

    @Builder
    public ReviewEntity(UserEntity user, UserOrderEntity orderEntity, String contents, Integer star, @Nullable String reviewImageUrl) {
        this.user = user;
        this.orderEntity = orderEntity;
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
