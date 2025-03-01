package xyz.tomorrowlearncamp.outsourcing.domain.user.enums;

import xyz.tomorrowlearncamp.outsourcing.global.exception.InvalidRequestException;

import java.util.Arrays;

public enum Usertype {
    USER, OWNER;

    public static Usertype of(String role) {
        return Arrays.stream(Usertype.values())
                .filter(r -> r.name().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(() -> new InvalidRequestException("유효하지 않은 유저타입입니다."));
    }
}
