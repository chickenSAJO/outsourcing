package xyz.tomorrowlearncamp.outsourcing.auth.dto;

import lombok.Getter;
import xyz.tomorrowlearncamp.outsourcing.domain.user.enums.Usertype;

@Getter
public class AuthUser {

    private final Long id;
    private final String email;
    private final Usertype usertype;

    public AuthUser(Long id, String email, Usertype usertype) {
        this.id = id;
        this.email = email;
        this.usertype = usertype;
    }
}
