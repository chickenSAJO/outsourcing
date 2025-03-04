package xyz.tomorrowlearncamp.outsourcing.domain.enums;

import lombok.Getter;

@Getter
public enum CommentErrorMessage {
    NOT_FOUND_COMMENT("댓글을 찾을 수 없습니다."),
    ;

    private final String errorMessage;

    CommentErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
