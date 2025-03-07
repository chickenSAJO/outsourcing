package xyz.tomorrowlearncamp.outsourcing.domain.comment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.tomorrowlearncamp.outsourcing.domain.review.entity.ReviewEntity;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;
import xyz.tomorrowlearncamp.outsourcing.global.entity.BaseEntity;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "comment_entity")
public class CommentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private ReviewEntity review;

    private String contents;

    public CommentEntity(UserEntity user, ReviewEntity review, String contents) {
        this.user = user;
        this.review = review;
        this.contents = contents;
    }
}
