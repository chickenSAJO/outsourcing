package xyz.tomorrowlearncamp.outsourcing.domain.store.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import jakarta.validation.constraints.NotBlank;
//import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import xyz.tomorrowlearncamp.outsourcing.domain.user.entity.UserEntity;

import java.time.LocalTime;

@Getter
//@RequiredArgsConstructor
public class StoreSaveRequestDto {
    @NotNull
    @Size(max = 100) //100문자까지
    private String storeTitle;

    @NotBlank
    private LocalTime openTime;//00:00:00 형식

    @NotBlank
    private LocalTime closeTime;//00:00:00 형식

    @Range(min=0, max=99999) //0원부터 10만원 미만까지 (null허용)
    private int minimumOrder;

    private UserEntity user;

    public StoreSaveRequestDto(String storeTitle, LocalTime openTime, LocalTime closeTime, int minimumOrder, UserEntity user) {
        this.storeTitle = storeTitle;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minimumOrder = minimumOrder;
        this.user = user;
    }
}
