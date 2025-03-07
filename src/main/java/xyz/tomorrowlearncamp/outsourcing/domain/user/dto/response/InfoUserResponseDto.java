package xyz.tomorrowlearncamp.outsourcing.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InfoUserResponseDto {

    private Long userId;

    private String email;

    private String nickname;

    @Builder
    public InfoUserResponseDto(Long userId, String email, String nickname) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
    }

}
