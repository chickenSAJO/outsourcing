package xyz.tomorrowlearncamp.outsourcing.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoreSaveRquestDto {

    private String storeTitle;
    @NotBlank
    @Pattern(regexp = "/^[0-9]{2}:[0-9]{2}:[0-9]{2}$/")
    private String openTime;
    private String closeTime;
    private int minimumOrder;
}
