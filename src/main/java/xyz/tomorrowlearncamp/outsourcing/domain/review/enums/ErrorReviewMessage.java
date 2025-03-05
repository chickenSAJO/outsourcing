package xyz.tomorrowlearncamp.outsourcing.domain.review.enums;

import lombok.Getter;

@Getter
public enum ErrorReviewMessage {
    NOT_FOUND_REVIEW("리뷰를 찾을 수 없습니다."),
    ;

    private final String errorMessage;

    ErrorReviewMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
