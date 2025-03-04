package xyz.tomorrowlearncamp.outsourcing.domain.store.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import xyz.tomorrowlearncamp.outsourcing.domain.store.enums.StoreTimeType;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;

@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class StoreSaveRequestDto {
    @Size(max = 100) //100문자까지
    private String storeTitle;

    @NotBlank
    @Pattern(regexp = StoreTimeType.TIME_FORMAT_PATTERN)//00:00:00 형식
    private String openTime;

    @NotBlank
    @Pattern(regexp = StoreTimeType.TIME_FORMAT_PATTERN) //00:00:00 형식
    private String closeTime;

    @Range(min=0, max=99999) //0원부터 10만원 미만까지 (null허용)
    private int minimumOrder;

    private UserEntity user;
}
