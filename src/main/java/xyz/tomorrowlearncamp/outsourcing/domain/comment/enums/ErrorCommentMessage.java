package xyz.tomorrowlearncamp.outsourcing.domain.comment.enums;

import lombok.Getter;

@Getter
public enum ErrorCommentMessage {
    NOT_FOUND_COMMENT("댓글을 찾을 수 없습니다."),
    UNAUTHORIZED("권한이 없습니다."),
    ;

    private final String errorMessage;

    ErrorCommentMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
